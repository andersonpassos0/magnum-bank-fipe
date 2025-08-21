package br.com.magnumbank.api1.application;

import br.com.magnumbank.api1.adapters.in.dto.MarcaResponse;
import br.com.magnumbank.api1.adapters.in.dto.VeiculoResponse;
import br.com.magnumbank.api1.domain.Marca;
import br.com.magnumbank.api1.domain.Veiculo;
import br.com.magnumbank.api1.mapper.MarcaMapper;
import br.com.magnumbank.api1.mapper.VeiculoMapper;
import br.com.magnumbank.api1.ports.out.MarcaRepository;
import br.com.magnumbank.api1.ports.out.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarcaServiceImpl implements MarcaService{

    private final MarcaRepository marcaRepository;
    private final VeiculoRepository veiculoRepository;
    private final MarcaMapper marcaMapper;
    private final VeiculoMapper veiculoMapper;

    @Override
    public Page<MarcaResponse> getMarcas(Pageable pageable) {
        Page<Marca> marcasList = marcaRepository.findAll(pageable);
        return marcasList.map(marcaMapper::toResponse);
    }

    @Override
    public Page<VeiculoResponse> listarVeiculosPorMarca(String codigo, Pageable pageable) {
        Marca marca = marcaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Marca de código " + codigo + " não encontrada!"));

        Page<Veiculo> veiculosList = veiculoRepository.findByMarca(marca, pageable);

        return veiculosList.map(veiculoMapper::toResponse);
    }
}
