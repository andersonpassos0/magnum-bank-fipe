package br.com.magnumbank.api1.application;

import br.com.magnumbank.api1.adapters.in.dto.MarcaResponse;
import br.com.magnumbank.api1.domain.Marca;
import br.com.magnumbank.api1.mapper.MarcaMapper;
import br.com.magnumbank.api1.ports.out.MarcaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarcaServiceImpl implements MarcaService{

    private final MarcaRepository marcaRepository;
    private final MarcaMapper marcaMapper;

    @Override
    public Page<MarcaResponse> getMarcas(Pageable pageable) {
        Page<Marca> marcasList = marcaRepository.findAll(pageable);
        return marcasList.map(marcaMapper::toResponse);
    }
}
