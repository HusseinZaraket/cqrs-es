package com.areeba.sample.cqrs.exception;

import java.math.BigDecimal;
import java.util.UUID;

public class InsufficientBalanceException extends Throwable {

	private static final long serialVersionUID = 1L;

	public InsufficientBalanceException(UUID accountId, BigDecimal debitAmount) {
        super("Insufficient Balance: Cannot debit " + debitAmount +
                " from account number [" + accountId.toString() + "]");
    }
}
