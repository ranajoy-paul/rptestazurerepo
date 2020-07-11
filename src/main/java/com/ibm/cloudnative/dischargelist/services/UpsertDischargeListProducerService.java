package com.ibm.cloudnative.dischargelist.services;

import com.ibm.cloudnative.dischargelist.models.UpsertDischargeListRequest;
import com.ibm.cloudnative.dischargelist.models.UpsertDischargeListResponse;
import reactor.core.publisher.Mono;

/**
 * This is an interface for hbp.update.berth.plan.producer service methods
 */
public interface UpsertDischargeListProducerService {

    Mono<UpsertDischargeListResponse> upsertBerthPlan(UpsertDischargeListRequest upsertDischargeListRequest, String transactionType);
}
