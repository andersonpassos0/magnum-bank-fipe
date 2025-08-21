package br.com.magnumbank.api1.infra.config;

import br.com.magnumbank.api1.domain.Role;
import br.com.magnumbank.api1.ports.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import br.com.magnumbank.api1.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Profile("!prod")
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;


    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByEmail("admin@magnum.com")){
            var usuario = User.builder()
                    .email("admin@magnum.com")
                    .password(encoder.encode("admin123"))
                    .roles(Set.of(Role.ROLE_ADMIN, Role.ROLE_USER))
                    .build();
            userRepository.save(usuario);
        }
    }
}
