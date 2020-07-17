package com.ibm.cloudnative.dischargelist.producer;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ibm.cloudnative.dischargelist.models.UpsertDischargeListRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import reactor.core.publisher.Mono;

import org.apache.commons.io.IOUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;

/**
 * This class is for Publishing the message on topic
 */

@Service
@Slf4j
public class EventProducer {

    private static final String SUCCESS = "success";

//    private static final String CLASSPATH_DATA_DISCHARGE_LIST_PRODUCER_REQUEST = "classpath:";

    @Value("${data.path}")
    private String path;


    private static final ObjectWriter EVENT_MESSAGE_WRITER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .writerFor(UpsertDischargeListRequest.class);

    private final KafkaTemplate<String, String> kafkaTemplate;


    public EventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * This method keeps the message on topic
     *
     * @param transactionId is the transaction id and to be used as key
     * @param eventType     is being kept in header
     * @return success if message is successfully kept on topic
     */
    public Mono<String> publishEvent(String topicName, UpsertDischargeListRequest upsertDischargeListRequest, String transactionId, OffsetDateTime businessValidityTime , String eventType, String transactionType) {
        try {
            String message = createPayLoad(upsertDischargeListRequest);
            ProducerRecord<String, String> record = new ProducerRecord<>(topicName,
                    transactionId, message);


            record.headers().add("apmt_eventType", eventType.getBytes())
                    .add("BusinessValidityTime", businessValidityTime.toString().getBytes())
                    .add("TransactionType", transactionType.getBytes());

            return Mono.fromFuture(kafkaTemplate.send(record).completable()).then(Mono.just(SUCCESS));
        } catch (Exception e) {
            log.error("Unable to send the message for event : " + eventType + "on topic " + topicName + " with transaction Id: " + transactionId, e);
            throw new RuntimeException("Unable to send the message for event : " + eventType + "on topic " + topicName + " with transaction Id: " + transactionId + " and the error is " + e.getMessage());
        }
    }

    private String createPayLoad(UpsertDischargeListRequest request) throws IOException {
        String payload = "";
        log.info("----------- Path {}", path);

        try {
//        File file = ResourceUtils.getFile(CLASSPATH_DATA_DISCHARGE_LIST_PRODUCER_REQUEST + "AMPS_discharge_as_XML.xml");
        File file = ResourceUtils.getFile(path + "AMPS_discharge_as_XML_"
                + request.getPortCode().toUpperCase()
                + "_"
                + request.getVesselCode()
                + "_"
                + request.getVoyageCode()
                + ".xml");
            InputStream in = new FileInputStream(file);
            payload = IOUtils.toString(in, StandardCharsets.UTF_8);
            log.info("Dischage List Payload {} ", payload);
        }
        catch (Exception e){
            log.error("Unable to read input XML file", e);
            throw e;
        }

        return payload;
    }

}