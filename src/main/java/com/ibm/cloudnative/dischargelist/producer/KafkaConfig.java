package com.ibm.cloudnative.dischargelist.producer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains all the configuration information for Kafka producer factory to be
 * able to create a Kafka template for publishing messages
 */
@Configuration
@Getter
@Slf4j
public class KafkaConfig {
    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${kafka.username:}")
    private String username;
    @Value("${kafka.password:}")
    private String password;
    @Value("${kafka.login-module:org.apache.kafka.common.security.plain.PlainLoginModule}")
    private String loginModule;
    @Value("${kafka.sasl-mechanism:PLAIN}")
    private String saslMechanism;
    @Value("${kafka.truststore-location:}")
    private String truststoreLocation;
    @Value("${kafka.clientId}")
    private String kafkaClientId;
    @Value("${kafka.truststore-password:}")
    private String truststorePassword;
    @Value("${kafka.producer.acks-config:all}")
    private String producerAcksConfig;
    @Value("${kafka.producer.linger:1}")
    private int producerLinger;
    @Value("${kafka.producer.timeout:30000}")
    private int producerRequestTimeout;
    @Value("${kafka.producer.batch-size:16384}")
    private int producerBatchSize;
    @Value("${kafka.producer.send-buffer:131072}")
    private int producerSendBuffer;
    @Value("${kafka.security-protocol:SASL_SSL}")
    private String securityProtocol;

    public static void addSaslProperties(Map<String, Object> properties, String saslMechanism, String securityProtocol, String loginModule, String username, String password) {
        if (!StringUtils.isEmpty(username)) {
            properties.put("security.protocol", securityProtocol);
            properties.put("sasl.mechanism", saslMechanism);
            String saslJassConfig = String.format("%s required username=\"%s\" password=\"%s\" ;", loginModule, username, password);
            properties.put("sasl.jaas.config", saslJassConfig);
        }
    }

    private static void addTruststoreProperties(Map<String, Object> properties, String location, String password) {
        if (!StringUtils.isEmpty(location)) {
            properties.put("ssl.truststore.location", location);
            properties.put("ssl.truststore.password", password);
        }
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.LINGER_MS_CONFIG, producerLinger);
        properties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, producerRequestTimeout);
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, producerBatchSize);
        properties.put(ProducerConfig.SEND_BUFFER_CONFIG, producerBatchSize);
        properties.put(ProducerConfig.ACKS_CONFIG, producerAcksConfig);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaClientId);

        addSaslProperties(properties, saslMechanism, securityProtocol, loginModule, username, password);
        addTruststoreProperties(properties, truststoreLocation, truststorePassword);

        return new DefaultKafkaProducerFactory<>(properties);
    }

    @Bean
    public KafkaTemplate kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {

        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaClientId);

        addSaslProperties(properties, saslMechanism, securityProtocol, loginModule, username, password);
        addTruststoreProperties(properties, truststoreLocation, truststorePassword);

        return new KafkaAdmin(properties);
    }

}
