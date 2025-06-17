package bbq.order.config;

@Component
@Configuration
public class KafkaConfiguration {

    @Bean
    public NewTopic orderTopic() {
        return TopicBuilder.name("orders")
                .partitions(1)
                .replicas(1)
                .build();
    }
    
}
