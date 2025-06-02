package bbq.delivery;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import bbq.delivery.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderListener {
    private final DeliveryRepository deliveryRepository;

    @RabbitListener(queues = "delivery.orders")
    public void onOrder(Order order) {
        log.info("Received order: {}", order);

        /*
         * Check if crash on Order results in message/delivery in a rabittmq deadletter
         * queue (delivery.orders.dlq)
         */
        // if (true) {
        //     // throw new RuntimeException("Crashedddddd");
        // }
        deliveryRepository.addNewOrder(order);
    }
}
