package com.microservices.orderservice.service;

import com.microservices.orderservice.dto.InventoryResponse;
import com.microservices.orderservice.dto.OrderLineItemsDto;
import com.microservices.orderservice.dto.OrderPlaceEvent;
import com.microservices.orderservice.dto.OrderRequest;
import com.microservices.orderservice.model.Order;
import com.microservices.orderservice.model.OrderLineItems;
import com.microservices.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final WebClient.Builder webClientBuilder;
    private final OrderRepository orderRepository;

    private  final KafkaTemplate<String, OrderPlaceEvent> kafkaTemplate;

    private final Tracer tracer;        // create own SpanID for sleuth
    public String placeOrder(OrderRequest orderRequest) throws IllegalAccessException {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().
                    stream().map(this::mapToDTO).toList();

            // set orderLineItems
            order.setOrderLineItemsList(orderLineItems);

            List<String> skuCodes = order.getOrderLineItemsList().stream()
                            .map(OrderLineItems::getSkuCode).toList();
                                                       // getter method -> getSkuCode

        Span inventoryServiceLookUp= tracer.nextSpan().name("InventoryServiceForSkuCodeLookUp");
        try(Tracer.SpanInScope spanInScope = tracer.withSpan(inventoryServiceLookUp.start())){
            // Call INVENTORY service
            // http://localhost:8083/api/inventory?skuCode=iphone-13&skuCode=iphone-13-red
            InventoryResponse[] resArray = webClientBuilder.build().get().
                    uri("http://inventory-service/api/inventory",
                            uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                    .retrieve().bodyToMono(InventoryResponse[].class).block();      // to sync operation -> block

            Boolean allProductsInStock = Arrays.stream(resArray).allMatch(InventoryResponse::isInStock);
            if(allProductsInStock){
                orderRepository.save(order);
                //Notify kafka
                kafkaTemplate.send("notificationTopic", new OrderPlaceEvent(order.getOrderNumber()));
                return "Order placed successfully";
            }else{
                throw new IllegalAccessException("product is not on the stock, please try again later");
            }
        }
        finally {
            inventoryServiceLookUp.end();
        }

    }

    private OrderLineItems mapToDTO(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setId(orderLineItemsDto.getId());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
