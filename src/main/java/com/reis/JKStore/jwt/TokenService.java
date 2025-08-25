package com.reis.JKStore.jwt;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.reis.JKStore.domain.Usuario;
import org.springframework.beans.factory.annotation.Value;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import com.auth0.jwt.JWT;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(Usuario usuario){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error enquanto gerava o token", exception);
        }
    }

    //valida o token e rotorna o subject(Login) nele contido
    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            return "";
        }
    }

    //Define o tempo de expiração do Token:
    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }
}