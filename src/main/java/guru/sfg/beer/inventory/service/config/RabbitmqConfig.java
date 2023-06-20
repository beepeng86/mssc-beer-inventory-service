package guru.sfg.beer.inventory.service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jt on 2019-07-20.
 */
@Configuration
public class RabbitmqConfig {

    public static final String NEW_INVENTORY_QUEUE = "new-inventory";
    public static final String ALLOCATE_ORDER_QUEUE = "allocate-order";
    public static final String ALLOCATE_ORDER_RESPONSE_QUEUE = "allocate-order-response";
    public static final String DEALLOCATE_ORDER_QUEUE = "deallocate-order";

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    Queue newInventoryQueue() {
        return QueueBuilder.durable(NEW_INVENTORY_QUEUE)
                        .build();
    }

    @Bean
    Queue allocateQueue() {
        return QueueBuilder.durable(ALLOCATE_ORDER_QUEUE)
                .build();
    }

    @Bean
    Queue allocateResponseQueue() {
        return QueueBuilder.durable(ALLOCATE_ORDER_RESPONSE_QUEUE)
                .build();
    }

    @Bean
    Queue deallocateQueue() {
        return QueueBuilder.durable(DEALLOCATE_ORDER_QUEUE)
                .build();
    }

    @Bean
    TopicExchange exchange() {
        return ExchangeBuilder.topicExchange("spring-boot-exchange").build();
    }

    @Bean
    Binding binding(Queue newInventoryQueue, TopicExchange exchange) {
        return BindingBuilder.bind(newInventoryQueue).to(exchange)
                .with(NEW_INVENTORY_QUEUE);
    }

    @Bean
    Binding allocateBinding(Queue allocateQueue, TopicExchange exchange) {
        return BindingBuilder.bind(allocateQueue).to(exchange)
                .with(ALLOCATE_ORDER_QUEUE);
    }

    @Bean
    Binding allocateResponseBinding(Queue allocateResponseQueue, TopicExchange exchange) {
        return BindingBuilder.bind(allocateResponseQueue).to(exchange)
                .with(ALLOCATE_ORDER_RESPONSE_QUEUE);
    }

    @Bean
    Binding deallocateBinding(Queue deallocateQueue, TopicExchange exchange) {
        return BindingBuilder.bind(deallocateQueue).to(exchange)
                .with(DEALLOCATE_ORDER_QUEUE);
    }

//    @Bean
//    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(NEW_INVENTORY_QUEUE, ALLOCATE_ORDER_QUEUE, ALLOCATE_ORDER_RESPONSE_QUEUE, DEALLOCATE_ORDER_QUEUE);
////        container.setMessageListener(listenerAdapter);
//        return container;
//    }

//    @Bean
//    MessageListenerAdapter listenerAdapter(AllocationListener receiver, org.springframework.amqp.support.converter.MessageConverter converter) {
//        MessageListenerAdapter a = new MessageListenerAdapter(receiver, "receiveMessage");
//        a.setMessageConverter(converter);
//        return a;
//    }
}
