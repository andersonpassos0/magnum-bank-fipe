package br.com.magnumbank.api1.application;

import br.com.magnumbank.api1.adapters.in.dto.LoginRequest;
import br.com.magnumbank.api1.adapters.in.dto.TokenResponse;
import br.com.magnumbank.api1.infra.security.JwtTokenService;
import br.com.magnumbank.api1.ports.out.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final AuthenticationManager authManager;
    private final JwtTokenService tokenService;
    private final UserRepository userRepository;

    @Override
    public TokenResponse login(LoginRequest request) {
        var auth = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        authManager.authenticate(auth);

        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new EntityNotFoundException("Credenciais inválidas"));

        String token = tokenService.generate(user.getEmail(), user.getRoles());
        return new TokenResponse(token);
    }
}
