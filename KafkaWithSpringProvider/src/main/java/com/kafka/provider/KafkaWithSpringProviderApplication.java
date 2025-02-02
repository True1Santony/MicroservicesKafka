package com.kafka.provider;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class KafkaWithSpringProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaWithSpringProviderApplication.class, args);
	}


	@Bean
	CommandLineRunner init(KafkaTemplate<String, String> kafkaTemplate){
		return args -> {
			kafkaTemplate.send("DesdeSpring-Topic", "Desde spring con amor");
		};
	}


}
