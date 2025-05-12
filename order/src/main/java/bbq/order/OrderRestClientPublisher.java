package bbq.order;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.JsonNode;

import bbq.order.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderRestClientPublisher {
    
    @Value("${delivery-service.url:http://localhost:8050}")
    private String deliveryServiceUrl;
    
    private final RestClient restClient;

    void publish(Order order) {
        var responseDelivery = restClient.post()
                .uri(deliveryServiceUrl + "/api/delivery")
                .body(order)
                .retrieve()
                .toEntity(JsonNode.class);
        log.info("Published to delivery with response: {}", responseDelivery);


    }

}
