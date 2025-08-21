package br.com.magnumbank.api1.application;

import br.com.magnumbank.api1.adapters.in.dto.UpdateVeiculoRequest;
import br.com.magnumbank.api1.adapters.in.dto.VeiculoResponse;
import br.com.magnumbank.api1.domain.Veiculo;
import br.com.magnumbank.api1.mapper.VeiculoMapper;
import br.com.magnumbank.api1.ports.out.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VeiculoServiceImpl implements VeiculoService{

    private final VeiculoRepository veiculoRepository;
    private final VeiculoMapper veiculoMapper;

    @Override
    public VeiculoResponse updateVeiculo(Long id, UpdateVeiculoRequest updateVeiculoRequest) {
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Veículo de id " + id + " não encontrado!"));

        veiculoMapper.updateEntityFromRequest(veiculo, updateVeiculoRequest);

        return veiculoMapper.toResponse(veiculoRepository.save(veiculo));
    }
}
