package br.com.magnumbank.api2.adapters.in.messaging;

import br.com.magnumbank.api2.adapters.in.dto.MarcaMessage;
import br.com.magnumbank.api2.application.ProcessarMarcaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MarcasListener {

    private final ProcessarMarcaService processarMarcaService;

    @KafkaListener(
            topics = "${app.topics.marcas}",
            containerFactory = "marcaKafkaListenerContainerFactory")
    public void consumer(MarcaMessage message){
        log.info("Marca {} - {} recebida do kafka", message.getCodigo(), message.getNome());
        processarMarcaService.processar(message);
    }

}
