package br.com.magnumbank.api2.application;

import br.com.magnumbank.api2.adapters.in.dto.MarcaMessage;
import br.com.magnumbank.api2.adapters.out.FipeClient;
import br.com.magnumbank.api2.adapters.out.dto.ModeloDTO;
import br.com.magnumbank.api2.adapters.out.dto.ModelosResponse;
import br.com.magnumbank.api2.domain.Marca;
import br.com.magnumbank.api2.domain.Veiculo;
import br.com.magnumbank.api2.ports.out.MarcaRepository;
import br.com.magnumbank.api2.ports.out.VeiculoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessarMarcaService {

    private final MarcaRepository marcaRepository;
    private final VeiculoRepository veiculoRepository;
    private final FipeClient fipeClient;

    @Transactional
    public void processar(MarcaMessage message){
        Marca marca = marcaRepository.findByCodigo(message.getCodigo())
                .map(existing -> {
                    existing.setNome(message.getNome());
                    return existing;
                })
                .orElseGet(() -> Marca.builder()
                .codigo(message.getCodigo())
                .nome(message.getNome())
                .build());

        marca = marcaRepository.save(marca);

        ModelosResponse response = fipeClient.listarModelosPorMarca(message.getCodigo());
        if(response == null || response.getModelos() == null){
            log.warn("A marca {} não possui modelos cadastrados", message.getNome());
            return;
        }

        for (ModeloDTO modelo : response.getModelos()){
            Veiculo veiculo = Veiculo.builder()
                    .codigo(modelo.getCodigo())
                    .modelo(modelo.getNome())
                    .marca(marca)
                    .build();
            veiculoRepository.save(veiculo);
        }

        log.info("Processado {} modelos da marca {}.", response.getModelos().size(), message.getNome());
    }
}
