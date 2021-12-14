package com.areeba.sample.cqrs.service;

import com.areeba.sample.cqrs.command.CreateAccountCommand;
import com.areeba.sample.cqrs.command.CreditMoneyCommand;
import com.areeba.sample.cqrs.command.DebitMoneyCommand;
import com.areeba.sample.cqrs.entity.BankAccount;
import com.areeba.sample.cqrs.rest.dto.AccountCreationDTO;
import com.areeba.sample.cqrs.rest.dto.MoneyAmountDTO;

import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import static com.areeba.sample.cqrs.service.ServiceUtils.formatUuid;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class AccountCommandService {
    private final CommandGateway commandGateway;

    public CompletableFuture<BankAccount> createAccount(AccountCreationDTO creationDTO) {
        return this.commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID(),
                creationDTO.getInitialBalance(),
                creationDTO.getOwner()
        ));
    }

    public CompletableFuture<String> creditMoneyToAccount(String accountId,
                                                          MoneyAmountDTO moneyCreditDTO) {
        return this.commandGateway.send(new CreditMoneyCommand(
                formatUuid(accountId),
                moneyCreditDTO.getAmount()
        ));
    }

    public CompletableFuture<String> debitMoneyFromAccount(String accountId,
                                                           MoneyAmountDTO moneyDebitDTO) {
        return this.commandGateway.send(new DebitMoneyCommand(
                formatUuid(accountId),
                moneyDebitDTO.getAmount()
        ));
    }
}
