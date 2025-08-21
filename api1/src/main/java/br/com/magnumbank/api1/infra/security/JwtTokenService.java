package br.com.magnumbank.api1.infra.security;

import br.com.magnumbank.api1.domain.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.Set;

@Component
public class JwtTokenService {

    private final byte[] key;
    private final Duration expiration;

    public JwtTokenService(
            @Value("${app.security.jwt.secret}") String secret,
            @Value("${app.security.jwt.expiration}") Duration expiration){

        this.key = secret.getBytes(StandardCharsets.UTF_8);
        this.expiration = expiration;
    }

    public String generate(String subject, Set<Role> roles){
        var now = new Date();
        var exp = new Date(now.getTime() + expiration.toMillis());

        return Jwts.builder()
                .setSubject(subject)
                .claim("roles", roles)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(Keys.hmacShaKeyFor(key), SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> parse (String token){
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(key))
                .build()
                .parseClaimsJws(token);
    }
}
