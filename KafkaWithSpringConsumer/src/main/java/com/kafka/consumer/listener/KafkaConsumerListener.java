package com.kafka.consumer.listener;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class KafkaConsumerListener {

    private Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerListener.class);

    /**
     * Si hubiera mas de un Listener topic se puede agrupar
     *
     * @param msn
     */
    @KafkaListener(topics = {"DesdeSpring-Topic"}, groupId = "uno")
    public void listener(String msn){
        LOGGER.info("MENSAJE RECIBIDO: " + msn);
    }
}
