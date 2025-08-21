package br.com.magnumbank.api2.adapters.in;

import br.com.magnumbank.api2.adapters.in.dto.MarcaMessage;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import static org.springframework.kafka.support.serializer.JsonDeserializer.TRUSTED_PACKAGES;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.kafka.support.serializer.JsonDeserializer.VALUE_DEFAULT_TYPE;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrap;
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, MarcaMessage> marcaConsumerFactory(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, org.springframework.kafka.support.serializer.JsonDeserializer.class);
        props.put(TRUSTED_PACKAGES, "br.com.magnumbank.api2.*");
        props.put(VALUE_DEFAULT_TYPE, MarcaMessage.class.getName());

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean(name = "marcaKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, MarcaMessage> marcaKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MarcaMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(marcaConsumerFactory());
        return factory;
    }

}
