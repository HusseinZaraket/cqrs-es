package com.areeba.sample.cqrs.projection;

import com.areeba.sample.cqrs.entity.BankAccount;
import com.areeba.sample.cqrs.event.AccountCreatedEvent;
import com.areeba.sample.cqrs.event.MoneyCreditedEvent;
import com.areeba.sample.cqrs.event.MoneyDebitedEvent;
import com.areeba.sample.cqrs.exception.AccountNotFoundException;
import com.areeba.sample.cqrs.query.FindBankAccountQuery;
import com.areeba.sample.cqrs.repository.BankAccountRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class BankAccountProjection {

    private final BankAccountRepository repository;

    @EventHandler
    public void on(AccountCreatedEvent event) {
        log.debug("Handling a Bank Account creation command {}", event.getId());
        BankAccount bankAccount = new BankAccount(
                event.getId(),
                event.getOwner(),
                event.getInitialBalance()
        );
        this.repository.save(bankAccount);
    }

    @EventHandler
    public void on(MoneyCreditedEvent event) throws AccountNotFoundException {
        log.debug("Handling a Bank Account Credit command {}", event.getId());
        Optional<BankAccount> optionalBankAccount = this.repository.findById(event.getId());
        if (optionalBankAccount.isPresent()) {
            BankAccount bankAccount = optionalBankAccount.get();
            bankAccount.setBalance(bankAccount.getBalance().add(event.getCreditAmount()));
            this.repository.save(bankAccount);
        } else {
            throw new AccountNotFoundException(event.getId());
        }
    }

    @EventHandler
    public void on(MoneyDebitedEvent event) throws AccountNotFoundException {
        log.debug("Handling a Bank Account Debit command {}", event.getId());
        Optional<BankAccount> optionalBankAccount = this.repository.findById(event.getId());
        if (optionalBankAccount.isPresent()) {
            BankAccount bankAccount = optionalBankAccount.get();
            bankAccount.setBalance(bankAccount.getBalance().subtract(event.getDebitAmount()));
            this.repository.save(bankAccount);
        } else {
            throw new AccountNotFoundException(event.getId());
        }
    }

    @QueryHandler
    public BankAccount handle(FindBankAccountQuery query) {
        log.debug("Handling FindBankAccountQuery query: {}", query);
        return this.repository.findById(query.getAccountId()).orElse(null);
    }
}
