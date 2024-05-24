package org.example.user.adapters.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.example.user.domain.TokenGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;

@Repository
public class JwtTokenGenerator implements TokenGenerator {

    private final Algorithm algorithm;

    public JwtTokenGenerator(@Value("${fpsb.user-token.private-key}") String secret) throws NoSuchAlgorithmException {
        // Todo: Random key pair just for demo.
        //       In production, key pair should be created from env secret
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        var keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        this.algorithm = Algorithm.RSA256(publicKey, privateKey);
    }

    @Override
    public String generateToken(String uuid) {
        try {
            return JWT.create()
                    .withIssuer("fpsb")
                    .withExpiresAt(Instant.MAX)
                    .withSubject(uuid)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Failed to create token");
        }
    }
}
