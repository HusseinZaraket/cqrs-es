package com.areeba.sample.cqrs.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class MoneyAmountDTO {
    private BigDecimal amount;
}
