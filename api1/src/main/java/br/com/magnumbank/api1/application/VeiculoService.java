package br.com.magnumbank.api1.application;

import br.com.magnumbank.api1.adapters.in.dto.UpdateVeiculoRequest;
import br.com.magnumbank.api1.adapters.in.dto.VeiculoResponse;
import jakarta.validation.Valid;

public interface VeiculoService {
    VeiculoResponse updateVeiculo(Long id, @Valid UpdateVeiculoRequest updateVeiculoRequest);
}
