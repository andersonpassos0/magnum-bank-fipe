package br.com.magnumbank.api1.adapters.in.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateVeiculoRequest(@NotBlank String modelo,
                                   String observacoes) {
}
