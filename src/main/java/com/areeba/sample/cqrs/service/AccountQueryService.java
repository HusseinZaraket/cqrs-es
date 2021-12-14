package com.areeba.sample.cqrs.service;

import com.areeba.sample.cqrs.entity.BankAccount;
import com.areeba.sample.cqrs.query.FindBankAccountQuery;

import lombok.AllArgsConstructor;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.areeba.sample.cqrs.service.ServiceUtils.formatUuid;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountQueryService {
	
	@Autowired
    private QueryGateway queryGateway;
    
	@Autowired
	private EventStore eventStore;

    public CompletableFuture<BankAccount> findById(String accountId) {
        return this.queryGateway.query(
                new FindBankAccountQuery(formatUuid(accountId)),
                ResponseTypes.instanceOf(BankAccount.class)
        );
    }

    public List<Object> listEventsForAccount(String accountId) {
        return this.eventStore
                .readEvents(formatUuid(accountId).toString())
                .asStream()
                .map(Message::getPayload)
                .collect(Collectors.toList());
    }
}
