package com.microservices.notification.config;

import com.microservices.notification.model.OrderPlaceEvent;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@Slf4j
public class KafkaTopicConfig {
    @Value("${spring.kafka.topic.name}")
    private String topicName;
    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(topicName)
                .partitions(10)
                .replicas(1)
                .build();
    }

    @KafkaListener(id = "notificationTopicId", topics = "notificationTopic")
    public void listen(OrderPlaceEvent orderPlaceEvent) {
        //send email here
        log.info("Received notification for Order {}", orderPlaceEvent);

    }
}
