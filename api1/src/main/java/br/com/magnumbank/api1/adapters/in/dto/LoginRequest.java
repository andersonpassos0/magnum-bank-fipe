package br.com.magnumbank.api1.adapters.in.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@Email String email,
                           @NotBlank String password) {
}
