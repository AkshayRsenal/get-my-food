package bbq.delivery.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class RabbitMqConfiguration {

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        var messageConverter =  new Jackson2JsonMessageConverter();
        messageConverter.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.INFERRED);
        return messageConverter;
    }

    @Bean
    public Declarables rabbitDeclarables() {
        // Topic
        var deliveryUpdatesExchange = new TopicExchange("delivery.updates");
        var ordersDeliveredQueue = QueueBuilder.durable("orders.delivered").build();
        var ordersInProgressQueue = QueueBuilder.durable("orders.inprogress").build();

        var ordersDeliveredBinding = BindingBuilder.bind(ordersDeliveredQueue)
                .to(deliveryUpdatesExchange)
                .with("delivered");
        var ordersInProgressBinding = BindingBuilder.bind(ordersInProgressQueue)
                .to(deliveryUpdatesExchange)
                .with("inprogress");

        return new Declarables(
                deliveryUpdatesExchange,
                ordersDeliveredQueue,
                ordersInProgressQueue,
                ordersDeliveredBinding,
                ordersInProgressBinding
        );
    }
}