package io.github.gabrielsizilio.signdocs.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import io.github.gabrielsizilio.signdocs.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        try {

            return JWT.create()
                    .withIssuer("signdocs-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateExpirationTime())
                    .sign(algorithm);
        }
        catch (JWTCreationException exception) {
            throw new RuntimeException("Could not generate token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("signdocs-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Could not validate token", exception);
        }
    }

    private Instant generateExpirationTime() {
        return LocalDateTime.now().plusMinutes(1).toInstant(ZoneOffset.of("-03:00"));
    }
}
