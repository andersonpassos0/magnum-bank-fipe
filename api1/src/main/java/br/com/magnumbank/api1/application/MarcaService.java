package br.com.magnumbank.api1.application;

import br.com.magnumbank.api1.adapters.in.dto.MarcaResponse;
import br.com.magnumbank.api1.adapters.in.dto.VeiculoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MarcaService {
    Page<MarcaResponse> getMarcas(Pageable pageable);
    Page<VeiculoResponse> listarVeiculosPorMarca(String codigo, Pageable pageable);
}
