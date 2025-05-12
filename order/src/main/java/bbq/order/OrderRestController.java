package bbq.order;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import bbq.order.model.Order;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "Order", description = "Order Resource")
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderRabbitMQPublisher publisher;

    private final OrderRepository orderRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public Order post(@RequestBody Order order) {
        // 1. Save Order
        var savedOrder = orderRepository.save(order);

        // 2. Publish order
        publisher.publish(savedOrder);

        /* Check and fix if order crash still results in order in delivery.orders */
        if (true)
            throw new RuntimeException("Order Crash from OrderRestController");

        // 3. Return order
        return savedOrder;
    }

}
