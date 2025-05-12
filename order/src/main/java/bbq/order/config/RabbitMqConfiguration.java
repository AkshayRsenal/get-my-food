package bbq.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.amqp.support.converter.Jackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

@Component
@Configuration
public class RabbitMqConfiguration {

    @Bean
    public RabbitTransactionManager rabbitTransactionManager(ConnectionFactory connectionFactory) {
        return new RabbitTransactionManager(connectionFactory);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        var messageConverter = new Jackson2JsonMessageConverter();
        messageConverter.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.INFERRED);
        return messageConverter;
    }

    @Bean
    public Declarables rabbitDeclarables() {
        // Publish/Subscribe
        var kitchenOrdersQueue = new Queue("kitchen.orders", false);
        var deliveryOrdersQueue = QueueBuilder.nonDurable("delivery.orders")
                .deadLetterExchange("delivery.orders.dlx")
                .build();
        var ordersExchange = new FanoutExchange("orders");
        var kitchenOrdersBinding = BindingBuilder.bind(kitchenOrdersQueue).to(ordersExchange);
        var deliveryOrdersBinding = BindingBuilder.bind(deliveryOrdersQueue).to(ordersExchange);

        // // DLQ
        var deliveryOrdersDlx = new FanoutExchange("delivery.orders.dlx");
        var deliveryOrdersDlq = QueueBuilder.nonDurable("delivery.orders.dlq").build();
        var deliveryOrdersDlqBinding = BindingBuilder.bind(deliveryOrdersDlq).to(deliveryOrdersDlx);

        return new Declarables(
                // PubSub
                ordersExchange,
                kitchenOrdersQueue,
                deliveryOrdersQueue,
                kitchenOrdersBinding,
                deliveryOrdersBinding,
                // // DLQ
                deliveryOrdersDlq,
                deliveryOrdersDlx,
                deliveryOrdersDlqBinding);
    }
}
