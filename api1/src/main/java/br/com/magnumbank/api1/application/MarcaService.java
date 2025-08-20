package br.com.magnumbank.api1.application;

import br.com.magnumbank.api1.adapters.out.FipeClient;
import br.com.magnumbank.api1.adapters.out.dto.MarcaMessage;
import br.com.magnumbank.api1.adapters.out.kafka.MarcaProducer;
import br.com.magnumbank.api1.domain.port.MarcaServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarcaService implements MarcaServicePort {

    private final FipeClient fipeClient;
    private final MarcaProducer marcaProducer;

    @Override
    public List<String> buscarMarcas() {
        List<Map<String, Object>> marcas = fipeClient.buscarMarcas();

        marcas.forEach(m -> {
            String codigo = String.valueOf(m.get("codigo"));
            String nome = String.valueOf(m.get("nome"));
            marcaProducer.send(new MarcaMessage(codigo, nome));
        });

        return marcas.stream()
                .map(m -> (String) m.get("nome"))
                .collect(Collectors.toList());
    }
}
