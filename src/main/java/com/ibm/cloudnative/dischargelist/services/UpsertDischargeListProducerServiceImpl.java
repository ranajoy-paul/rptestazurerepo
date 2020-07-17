package com.ibm.cloudnative.dischargelist.services;

import com.ibm.cloudnative.dischargelist.models.UpsertDischargeListRequest;
import com.ibm.cloudnative.dischargelist.models.UpsertDischargeListResponse;
import com.ibm.cloudnative.dischargelist.producer.EventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * This is an interface for  hbp.update.berth.plan.producer methods
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UpsertDischargeListProducerServiceImpl implements UpsertDischargeListProducerService {

    private final EventProducer eventProducer;

    private static final String DISCHARGE_LIST_EVENT = "dischargeListTransactionInitiated";
    @Value("${businessValidityDuration}")
    private int businessValidityDuration;

    @Value("${kafka.command.submission.topic}")
    private String topicName;

    private static final String TOPIC_NAME = "APMM.berthPlanning.dischargeListEventsGroup.confidential.dedicated.topic.v1";


    @Override
    public Mono<UpsertDischargeListResponse> upsertBerthPlan(UpsertDischargeListRequest upsertDischargeListRequest, String transactionType) {
        UUID transactionId = UUID.randomUUID();
        OffsetDateTime businessValidityTime = OffsetDateTime.now().plusMinutes(businessValidityDuration);
        log.info("Transaction id {} and Business Validity Time {} for Received message {} ", transactionId, businessValidityTime, upsertDischargeListRequest);
        String commandTopicName = determineCommandTopic(upsertDischargeListRequest.getPortCode());
        log.info("Publishing Event on Command Topic {} for Transaction Id {}", commandTopicName, transactionId.toString());

        return eventProducer.publishEvent(commandTopicName, upsertDischargeListRequest, transactionId.toString(), businessValidityTime, DISCHARGE_LIST_EVENT, transactionType)
                        .flatMap(sendEvent -> Mono.just(UpsertDischargeListResponse.builder()
                                        .submissionId(transactionId)
                                        .build())
                        );

    }

    private String determineCommandTopic(String maerskTerminalCode) {
        log.info("------------ topic name {}", topicName);
        return TOPIC_NAME;
    }
}
