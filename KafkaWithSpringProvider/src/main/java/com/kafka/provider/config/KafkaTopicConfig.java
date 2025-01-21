package com.kafka.provider.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {


    /**
     * Si se cae el Topic principal hay un respaldo por replicas.
     * @return
     */
    @Bean
    public NewTopic generateTopic(){

        Map<String,String> configurations = new HashMap<>();

        configurations.put(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_DELETE);//delete (Borra el mensaje), compact (Mantiene el mas actualizado)
        configurations.put(TopicConfig.RETENTION_MS_CONFIG, "86400000");//mantengo el mensaje 1 dias en el server de kafka por defecto -1 no se borra nunca
        configurations.put(TopicConfig.SEGMENT_BYTES_CONFIG, "1073741824");// tamaño maximo de segmento del Topic, 1Gb max por defecto
        configurations.put(TopicConfig.MAX_MESSAGE_BYTES_CONFIG, "10485760");//tamaño maximo de caba mensaje por defecto 1 Mb, puesto 10Mb

        return TopicBuilder.name("DesdeSpring-Topic")
                .partitions(2)
                .replicas(2)
                .configs(configurations)
                .build();
    }


}
