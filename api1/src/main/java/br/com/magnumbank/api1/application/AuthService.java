package br.com.magnumbank.api1.application;

import br.com.magnumbank.api1.adapters.in.dto.LoginRequest;
import br.com.magnumbank.api1.adapters.in.dto.TokenResponse;

public interface AuthService {
    TokenResponse login(LoginRequest request);
}
