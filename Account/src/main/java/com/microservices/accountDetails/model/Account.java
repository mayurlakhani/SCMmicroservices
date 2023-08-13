package com.microservices.accountDetails.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "account")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_gen")
    @SequenceGenerator(name = "account_gen", sequenceName = "account_seq")
    @Column(name = "id", nullable = false)
    private Long id;
    private String type;
    private String IBAN;
    private String BIC;
    private String AccountType;
    private String accountHolderName;
    private Long userId;


}
