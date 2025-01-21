# Kafka Spring Microservices Project

This project demonstrates how to implement a simple Kafka-based microservices architecture using Spring Boot. It consists of two modules: a **Provider** and a **Consumer**, both communicating via Kafka topics.

## Project Structure

- **KafkaWithSpring** (Parent Project):
  - Manages shared dependencies and configurations.
- **KafkaWithSpringProvider**:
  - Produces messages and sends them to Kafka topics.
- **KafkaWithSpringConsumer**:
  - Listens to Kafka topics and processes received messages.

## Prerequisites

1. **Kafka Server:** Install and configure Apache Kafka (version 2.x or later).
2. **Java:** Version 17 or later.
3. **Maven:** Version 3.6 or later.
4. **Spring Boot:** 3.4.1 (managed through the parent POM).

## Setup

### Configuration

1. Update the `application.properties` file for both modules with the correct Kafka server address:
   ```properties
   spring.kafka.bootstrapServer=localhost:9092
   ```

### Build the Project

Run the following command in the project’s root directory:
```bash
mvn clean install
```

## Modules

### KafkaWithSpringProvider

The **Provider** module sends messages to the Kafka topic "DesdeSpring-Topic" upon startup.

#### Main Features

- Defines a topic with the following configurations:
  - **Retention Period:** 1 day (24 hours).
  - **Maximum Message Size:** 10 MB.
  - **Cleanup Policy:** Delete.

- Sends a test message using `KafkaTemplate`:
  ```java
  kafkaTemplate.send("DesdeSpring-Topic", "Desde spring con amor");
  ```

#### Kafka Topic Configuration

Defined in `KafkaTopicConfig`:
```java
@Bean
public NewTopic generateTopic() {
    Map<String, String> configurations = new HashMap<>();
    configurations.put(TopicConfig.RETENTION_MS_CONFIG, "86400000");
    configurations.put(TopicConfig.MAX_MESSAGE_BYTES_CONFIG, "10485760");
    return TopicBuilder.name("DesdeSpring-Topic")
                      .partitions(2)
                      .replicas(2)
                      .configs(configurations)
                      .build();
}
```

### KafkaWithSpringConsumer

The **Consumer** module listens for messages on "DesdeSpring-Topic" and logs the received messages.

#### Main Features

- Configured to consume messages via `KafkaListener`:
  ```java
  @KafkaListener(topics = {"DesdeSpring-Topic"}, groupId = "uno")
  public void listener(String message) {
      LOGGER.info("Message Received: " + message);
  }
  ```

- Uses a `KafkaListenerContainerFactory` to manage the Kafka consumer:
  ```java
  @Bean
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> consumer() {
      ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
      factory.setConsumerFactory(consumerFactory());
      return factory;
  }
  ```

## Running the Project

1. Start the Kafka server locally.
2. Run the **Provider** module:
   ```bash
   mvn spring-boot:run -pl KafkaWithSpringProvider
   ```
   This sends a message to the topic "DesdeSpring-Topic".
3. Run the **Consumer** module:
   ```bash
   mvn spring-boot:run -pl KafkaWithSpringConsumer
   ```
   This listens for messages on the topic and logs them to the console.

## Testing

Run the tests using the following command:
```bash
mvn test
```

The project includes integration tests for Kafka producer and consumer functionalities.

## Key Dependencies

### Shared Dependencies

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka-test</artifactId>
    <scope>test</scope>
</dependency>
```

### Module-Specific Dependencies

- **Provider Module:**
  - Spring Kafka for message production.
- **Consumer Module:**
  - Spring Kafka for message consumption.

## Notes

- Ensure Kafka is running on the specified bootstrap server address before running the application.
- Messages are processed synchronously; for high throughput, consider adjusting Kafka listener concurrency settings.

---

Enjoy building scalable and event-driven microservices with Kafka and Spring Boot!

