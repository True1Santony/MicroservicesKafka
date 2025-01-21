package com.kafka.provider.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProviderConfig {

    @Value("${spring.kafka.bootstrapServer}")
    private String bootstrapServers;

    public Map<String, Object> producerConfig(){
     Map<String,Object> properties = new HashMap<>();
     properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
     properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);//objeto de serializacion string to byte
     properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
     return properties;
    }

    /**
     * Farica de clientes para envio de mensajes
     * @return
     */
    @Bean
    public ProducerFactory<String, String> producerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    /**
     * Se inyecta por paramentros el ProducerFactory
     * @param producerFactory
     * @return
     */
    @Bean
    public KafkaTemplate<String , String> kafkaTemplate(ProducerFactory<String, String> producerFactory){
        return new KafkaTemplate<>(producerFactory);
    }

}
