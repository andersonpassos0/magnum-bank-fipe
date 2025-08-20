package br.com.magnumbank.api1.adapters.out.kafka;

import br.com.magnumbank.api1.adapters.out.dto.MarcaMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MarcaProducer {

    private final KafkaTemplate<String, MarcaMessage> kafkaTemplate;

    @Value("${app.topics.marcas}")
    private String topic;

    public void send(MarcaMessage mensagem){
        kafkaTemplate.send(topic, mensagem.getCodigo(), mensagem)
                .whenComplete((result, ex) -> {
                    if (ex != null){
                        log.error("Falha ao enviar para kafka: {}", mensagem, ex);
                    } else {
                        log.info("Marca enviada para kafka: key={}, topic={}, offset={}",
                                mensagem.getCodigo(), topic, result.getRecordMetadata().offset());
                    }
                });
    }
}
