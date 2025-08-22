package br.com.magnumbank.api1.application;

import br.com.magnumbank.api1.adapters.in.dto.LoginRequest;
import br.com.magnumbank.api1.adapters.in.dto.TokenResponse;
import br.com.magnumbank.api1.domain.Role;
import br.com.magnumbank.api1.domain.User;
import br.com.magnumbank.api1.infra.security.JwtTokenService;
import br.com.magnumbank.api1.ports.out.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Mock
    AuthenticationManager authManager;
    @Mock
    JwtTokenService jwtTokenService;

    @InjectMocks
    AuthServiceImpl authService;

    @BeforeEach
    void setup() {
    }

    @Test
    void shouldReturnBearerToken(){
        var request = new LoginRequest("user@magnumbank.com", "s3nh4");
        var user = User.builder().id(1L)
                                 .email("user@magnumbank.com")
                                 .password("$pass$hash$fake")
                                 .roles(Set.of(Role.ROLE_USER))
                                 .build();

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(user));
        when(jwtTokenService.generate(user.getEmail(), user.getRoles())).thenReturn("jwt123");

        TokenResponse resp = authService.login(request);

        assertThat(resp.token()).isEqualTo("jwt123");
        assertThat(resp.tokenType()).isEqualTo("Bearer");

        verify(authManager).authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        verify(userRepository).findByEmail(request.email());
        verify(jwtTokenService).generate("user@magnumbank.com", Set.of(Role.ROLE_USER));
    }

    @Test
    void shouldReturnExceptionWhenUserNotFound(){
        var request = new LoginRequest("nul@magnumbank.com", "123");

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Credenciais inválidas");
    }
}
