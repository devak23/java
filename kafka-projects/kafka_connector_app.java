// ============================================================================
// STEP 1: pom.xml - Add these dependencies
// ============================================================================
/*
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.0</version>
    </parent>
    
    <groupId>com.example</groupId>
    <artifactId>kafka-connector</artifactId>
    <version>1.0.0</version>
    
    <properties>
        <java.version>17</java.version>
    </properties>
    
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka</artifactId>
        </dependency>
        
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
        </dependency>
        
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
    </dependencies>
</project>
*/

// ============================================================================
// STEP 2: application.yml - Kafka Configuration (DYNAMIC TOPIC REGISTRATION)
// ============================================================================
/*
spring:
  application:
    name: kafka-connector-service
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: kafka-connector-group
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      enable-auto-commit: false
      properties:
        spring.json.trusted.packages: "*"
    listener:
      ack-mode: manual
      concurrency: 3

# DYNAMIC TOPIC-TO-PROCESSOR MAPPING
# To add a new topic, just add it here - NO CODE CHANGES NEEDED!
kafka:
  topic-mappings:
    - topic: user-events-topic
      event-type: UserEvent
      processor: userEventProcessor
    - topic: order-events-topic
      event-type: OrderEvent
      processor: orderEventProcessor
    - topic: notification-events-topic
      event-type: NotificationEvent
      processor: notificationEventProcessor
    - topic: payment-events-topic
      event-type: PaymentEvent
      processor: paymentEventProcessor
    # ADD NEW TOPICS HERE WITHOUT CODE CHANGES:
    # - topic: inventory-events-topic
    #   event-type: InventoryEvent
    #   processor: inventoryEventProcessor

logging:
  level:
    org.springframework.kafka: INFO
    com.example.kafka: DEBUG
*/

// ============================================================================
// STEP 3: Main Application Class
// ============================================================================
package com.example.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class KafkaConnectorApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaConnectorApplication.class, args);
    }
}

// ============================================================================
// STEP 4: Generic Event Processor Interface
// ============================================================================
package com.example.kafka.service;

public interface EventProcessor<T> {
    void process(T event);
    Class<T> getEventType();
}

// ============================================================================
// STEP 5: Topic Configuration Model
// ============================================================================
package com.example.kafka.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "kafka")
@Data
public class KafkaTopicMappingConfig {
    private List<TopicMapping> topicMappings;
    
    @Data
    public static class TopicMapping {
        private String topic;
        private String eventType;
        private String processor;
    }
}

// ============================================================================
// STEP 6: Event Type Registry (Maps event types to classes)
// ============================================================================
package com.example.kafka.registry;

import com.example.kafka.model.*;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class EventTypeRegistry {
    
    private final Map<String, Class<?>> eventTypeMap = new HashMap<>();
    
    public EventTypeRegistry() {
        // Register all event types here
        registerEventType("UserEvent", UserEvent.class);
        registerEventType("OrderEvent", OrderEvent.class);
        registerEventType("NotificationEvent", NotificationEvent.class);
        registerEventType("PaymentEvent", PaymentEvent.class);
        // Add new event types here when needed
    }
    
    public void registerEventType(String typeName, Class<?> clazz) {
        eventTypeMap.put(typeName, clazz);
    }
    
    public Class<?> getEventClass(String typeName) {
        Class<?> clazz = eventTypeMap.get(typeName);
        if (clazz == null) {
            throw new IllegalArgumentException("Unknown event type: " + typeName);
        }
        return clazz;
    }
}

// ============================================================================
// STEP 7: Generic Kafka Multi-Topic Connector (NO CODE CHANGES NEEDED!)
// ============================================================================
package com.example.kafka.connector;

import com.example.kafka.config.KafkaTopicMappingConfig;
import com.example.kafka.registry.EventTypeRegistry;
import com.example.kafka.service.EventProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class GenericKafkaConnector {
    
    private final ObjectMapper objectMapper;
    private final ApplicationContext applicationContext;
    private final KafkaTopicMappingConfig mappingConfig;
    private final EventTypeRegistry eventTypeRegistry;
    
    // Cache: topic -> {eventClass, processor}
    private final Map<String, TopicHandler> topicHandlers = new HashMap<>();
    
    @Autowired
    public GenericKafkaConnector(ObjectMapper objectMapper,
                                 ApplicationContext applicationContext,
                                 KafkaTopicMappingConfig mappingConfig,
                                 EventTypeRegistry eventTypeRegistry) {
        this.objectMapper = objectMapper;
        this.applicationContext = applicationContext;
        this.mappingConfig = mappingConfig;
        this.eventTypeRegistry = eventTypeRegistry;
    }
    
    @PostConstruct
    public void initialize() {
        log.info("Initializing Generic Kafka Connector with topic mappings...");
        
        for (KafkaTopicMappingConfig.TopicMapping mapping : mappingConfig.getTopicMappings()) {
            try {
                // Get event class from registry
                Class<?> eventClass = eventTypeRegistry.getEventClass(mapping.getEventType());
                
                // Get processor bean
                EventProcessor<?> processor = 
                    (EventProcessor<?>) applicationContext.getBean(mapping.getProcessor());
                
                // Cache the handler
                topicHandlers.put(mapping.getTopic(), 
                    new TopicHandler(eventClass, processor));
                
                log.info("Registered handler - Topic: {}, EventType: {}, Processor: {}", 
                         mapping.getTopic(), mapping.getEventType(), mapping.getProcessor());
                
            } catch (Exception e) {
                log.error("Failed to register handler for topic {}: {}", 
                         mapping.getTopic(), e.getMessage(), e);
            }
        }
        
        log.info("Generic Kafka Connector initialized with {} topic handlers", 
                 topicHandlers.size());
    }
    
    /**
     * SINGLE GENERIC LISTENER FOR ALL TOPICS!
     * Listens to all configured topics dynamically
     */
    @KafkaListener(
        topics = "#{@kafkaTopicMappingConfig.topicMappings.![topic]}",
        groupId = "${spring.kafka.consumer.group-id}",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeEvent(ConsumerRecord<String, String> record, 
                            Acknowledgment acknowledgment) {
        String topic = record.topic();
        
        try {
            log.info("Received Event - Topic: {}, Partition: {}, Offset: {}, Key: {}", 
                     topic, record.partition(), record.offset(), record.key());
            
            // Get the appropriate handler for this topic
            TopicHandler handler = topicHandlers.get(topic);
            
            if (handler == null) {
                log.error("No handler configured for topic: {}", topic);
                acknowledgment.acknowledge();
                return;
            }
            
            // Deserialize JSON to the specific event type
            Object event = objectMapper.readValue(record.value(), handler.getEventClass());
            log.debug("Parsed Event: {}", event);
            
            // Process the event with the appropriate processor
            processEvent(handler.getProcessor(), event);
            
            // Acknowledge successful processing
            acknowledgment.acknowledge();
            log.info("Event processed successfully from topic: {}", topic);
            
        } catch (Exception e) {
            log.error("Error processing event from topic {}: {}", 
                     topic, e.getMessage(), e);
            // Implement retry logic or dead letter queue here
            acknowledgment.acknowledge(); // Still acknowledge to avoid reprocessing
        }
    }
    
    @SuppressWarnings("unchecked")
    private <T> void processEvent(EventProcessor<T> processor, Object event) {
        processor.process((T) event);
    }
    
    // Inner class to hold handler information
    private static class TopicHandler {
        private final Class<?> eventClass;
        private final EventProcessor<?> processor;
        
        public TopicHandler(Class<?> eventClass, EventProcessor<?> processor) {
            this.eventClass = eventClass;
            this.processor = processor;
        }
        
        public Class<?> getEventClass() {
            return eventClass;
        }
        
        public EventProcessor<?> getProcessor() {
            return processor;
        }
    }
}

// ============================================================================
// STEP 8: Message Models (DTOs)
// ============================================================================
package com.example.kafka.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEvent {
    @NotNull
    @JsonProperty("user_id")
    private String userId;
    
    @JsonProperty("event_type")
    private String eventType;
    
    @JsonProperty("user_name")
    private String userName;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class OrderEvent {
    @NotNull
    @JsonProperty("order_id")
    private String orderId;
    
    @JsonProperty("customer_id")
    private String customerId;
    
    @JsonProperty("total_amount")
    private Double totalAmount;
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("items_count")
    private Integer itemsCount;
    
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class NotificationEvent {
    @NotNull
    @JsonProperty("notification_id")
    private String notificationId;
    
    @JsonProperty("recipient")
    private String recipient;
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("channel")
    private String channel;
    
    @JsonProperty("priority")
    private String priority;
    
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class PaymentEvent {
    @NotNull
    @JsonProperty("payment_id")
    private String paymentId;
    
    @JsonProperty("order_id")
    private String orderId;
    
    @JsonProperty("amount")
    private Double amount;
    
    @JsonProperty("currency")
    private String currency;
    
    @JsonProperty("payment_method")
    private String paymentMethod;
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
}

// ============================================================================
// STEP 9: Event Processors (Implement EventProcessor Interface)
// ============================================================================
package com.example.kafka.service;

import com.example.kafka.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("userEventProcessor")
@Slf4j
public class UserEventProcessor implements EventProcessor<UserEvent> {
    
    @Override
    public void process(UserEvent event) {
        log.info("Processing User Event: Type={}, UserId={}, UserName={}", 
                 event.getEventType(), event.getUserId(), event.getUserName());
        
        switch (event.getEventType()) {
            case "CREATED":
                handleUserCreated(event);
                break;
            case "UPDATED":
                handleUserUpdated(event);
                break;
            case "DELETED":
                handleUserDeleted(event);
                break;
            default:
                log.warn("Unknown user event type: {}", event.getEventType());
        }
    }
    
    @Override
    public Class<UserEvent> getEventType() {
        return UserEvent.class;
    }
    
    private void handleUserCreated(UserEvent event) {
        log.info("User created: {}", event.getUserId());
    }
    
    private void handleUserUpdated(UserEvent event) {
        log.info("User updated: {}", event.getUserId());
    }
    
    private void handleUserDeleted(UserEvent event) {
        log.info("User deleted: {}", event.getUserId());
    }
}

@Service("orderEventProcessor")
@Slf4j
class OrderEventProcessor implements EventProcessor<OrderEvent> {
    
    @Override
    public void process(OrderEvent event) {
        log.info("Processing Order Event: OrderId={}, Status={}, Amount={}", 
                 event.getOrderId(), event.getStatus(), event.getTotalAmount());
        
        switch (event.getStatus()) {
            case "PENDING":
                log.info("Order pending: {}", event.getOrderId());
                break;
            case "CONFIRMED":
                log.info("Order confirmed: {}", event.getOrderId());
                break;
            case "SHIPPED":
                log.info("Order shipped: {}", event.getOrderId());
                break;
            case "DELIVERED":
                log.info("Order delivered: {}", event.getOrderId());
                break;
            default:
                log.warn("Unknown order status: {}", event.getStatus());
        }
    }
    
    @Override
    public Class<OrderEvent> getEventType() {
        return OrderEvent.class;
    }
}

@Service("notificationEventProcessor")
@Slf4j
class NotificationEventProcessor implements EventProcessor<NotificationEvent> {
    
    @Override
    public void process(NotificationEvent event) {
        log.info("Processing Notification Event: Channel={}, Priority={}, Recipient={}", 
                 event.getChannel(), event.getPriority(), event.getRecipient());
        
        switch (event.getChannel()) {
            case "EMAIL":
                log.info("Sending email to: {}", event.getRecipient());
                break;
            case "SMS":
                log.info("Sending SMS to: {}", event.getRecipient());
                break;
            case "PUSH":
                log.info("Sending push notification to: {}", event.getRecipient());
                break;
            default:
                log.warn("Unknown notification channel: {}", event.getChannel());
        }
    }
    
    @Override
    public Class<NotificationEvent> getEventType() {
        return NotificationEvent.class;
    }
}

@Service("paymentEventProcessor")
@Slf4j
class PaymentEventProcessor implements EventProcessor<PaymentEvent> {
    
    @Override
    public void process(PaymentEvent event) {
        log.info("Processing Payment Event: PaymentId={}, Status={}, Amount={} {}", 
                 event.getPaymentId(), event.getStatus(), event.getAmount(), event.getCurrency());
        
        switch (event.getStatus()) {
            case "PENDING":
                log.info("Payment pending: {}", event.getPaymentId());
                break;
            case "SUCCESS":
                log.info("Payment successful: {}", event.getPaymentId());
                break;
            case "FAILED":
                log.info("Payment failed: {}", event.getPaymentId());
                break;
            default:
                log.warn("Unknown payment status: {}", event.getStatus());
        }
    }
    
    @Override
    public Class<PaymentEvent> getEventType() {
        return PaymentEvent.class;
    }
}

// ============================================================================
// STEP 10: Kafka Configuration
// ============================================================================
package com.example.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
    
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;
    
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);
        
        return new DefaultKafkaConsumerFactory<>(config);
    }
    
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = 
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }
}

// ============================================================================
// STEP 11: ObjectMapper Bean Configuration
// ============================================================================
package com.example.kafka.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
    
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}

// ============================================================================
// HOW TO ADD A NEW TOPIC (NO CODE CHANGES IN CONNECTOR!)
// ============================================================================
/*

STEP 1: Create the Event Model
-------------------------------
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryEvent {
    @JsonProperty("product_id")
    private String productId;
    
    @JsonProperty("quantity")
    private Integer quantity;
    
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
}

STEP 2: Register Event Type in EventTypeRegistry
-------------------------------------------------
public EventTypeRegistry() {
    registerEventType("UserEvent", UserEvent.class);
    registerEventType("OrderEvent", OrderEvent.class);
    registerEventType("NotificationEvent", NotificationEvent.class);
    registerEventType("PaymentEvent", PaymentEvent.class);
    registerEventType("InventoryEvent", InventoryEvent.class); // ADD THIS LINE
}

STEP 3: Create the Processor
-----------------------------
@Service("inventoryEventProcessor")
@Slf4j
public class InventoryEventProcessor implements EventProcessor<InventoryEvent> {
    
    @Override
    public void process(InventoryEvent event) {
        log.info("Processing Inventory Event: ProductId={}, Quantity={}", 
                 event.getProductId(), event.getQuantity());
        // Your business logic here
    }
    
    @Override
    public Class<InventoryEvent> getEventType() {
        return InventoryEvent.class;
    }
}

STEP 4: Add Configuration in application.yml
---------------------------------------------
kafka:
  topic-mappings:
    - topic: user-events-topic
      event-type: UserEvent
      processor: userEventProcessor
    - topic: order-events-topic
      event-type: OrderEvent
      processor: orderEventProcessor
    - topic: notification-events-topic
      event-type: NotificationEvent
      processor: notificationEventProcessor
    - topic: payment-events-topic
      event-type: PaymentEvent
      processor: paymentEventProcessor
    - topic: inventory-events-topic    # ADD THIS
      event-type: InventoryEvent        # ADD THIS
      processor: inventoryEventProcessor # ADD THIS

THAT'S IT! The GenericKafkaConnector will automatically handle the new topic.
NO CHANGES needed in the connector class!

*/