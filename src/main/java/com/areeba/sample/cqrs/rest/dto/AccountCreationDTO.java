package com.areeba.sample.cqrs.rest.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class AccountCreationDTO {
    private final BigDecimal initialBalance;
    private final String owner;
}
