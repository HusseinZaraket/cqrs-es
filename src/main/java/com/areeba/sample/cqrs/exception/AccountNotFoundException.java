package com.areeba.sample.cqrs.exception;

import java.util.UUID;

public class AccountNotFoundException extends Throwable {

	private static final long serialVersionUID = 1L;

	public AccountNotFoundException(UUID id) {
        super("Cannot found account number [" + id + "]");
    }
}
