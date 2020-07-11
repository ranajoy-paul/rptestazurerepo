package com.ibm.cloudnative.dischargelist.handlers;

import com.ibm.cloudnative.dischargelist.models.UpsertDischargeListRequest;
import com.ibm.cloudnative.dischargelist.models.UpsertDischargeListResponse;
import com.ibm.cloudnative.dischargelist.services.UpsertDischargeListProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

import static org.springframework.web.reactive.function.server.ServerResponse.accepted;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpsertDischargeListProducerHandler {

    private static final String DATA_NOT_FOUND_MESSAGE = "The data you seek is not here.";
    private static final String TRANSACTION_TYPE_CREATE = "Create";
    private static final String TRANSACTION_TYPE_UPDATE = "Update";

    private final UpsertDischargeListProducerService service;

//    private final Validator validator;




    /**
     * This method Published Berth Plan Event
     *
     * @param request the http request.
     * @return A ServerResponse containing Hello World message.
     */
    public Mono<ServerResponse> upsertDischargeList(ServerRequest request) {

        // Transaction-Type header must be passed.
        List<String> transactionType = request.headers().header("Transaction-Type");
        log.debug("transactionType {}", transactionType);
        if (transactionType.size() != 1 || !(TRANSACTION_TYPE_CREATE.equals(transactionType.get(0))
                || TRANSACTION_TYPE_UPDATE.equals(transactionType.get(0)))) {
            return Mono.error(new RuntimeException("Invalid request. Transaction-Type header expected with one of the values: Create or Update"));
        }


        Mono<UpsertDischargeListRequest> updateBerthPlansRequestMono = request.bodyToMono(UpsertDischargeListRequest.class);

        return updateBerthPlansRequestMono.flatMap(updateBerthPlansRequest -> {
            var authResponse = validateRequest(updateBerthPlansRequest, transactionType.get(0));
            return accepted()
//                    .contentType(requiredMediaType(request))
                    .body(authResponse.flatMap(auth -> service.upsertBerthPlan(updateBerthPlansRequest, transactionType.get(0)))
                            .switchIfEmpty(Mono.error(new RuntimeException(DATA_NOT_FOUND_MESSAGE))), UpsertDischargeListResponse.class);
        });

    }

    /**
     * This method validates the UpdateBerthPlansRequest for invalid/null values and authToken with validator service
     *
     * @param upsertDischargeListRequest request to validate.
     * @return authToken after validation.
     */
    private Mono<String> validateRequest(UpsertDischargeListRequest upsertDischargeListRequest,  String transactionType) {
//        Set<ConstraintViolation<Object>> validationErrors = validator.validate(upsertDischargeListRequest);
//        if (!validationErrors.isEmpty()) {
//            throw new ConstraintViolationException(validationErrors);
//        }

        return Mono.just(" ");

    }

}
