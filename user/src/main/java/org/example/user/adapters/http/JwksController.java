package org.example.user.adapters.http;

import lombok.RequiredArgsConstructor;
import org.example.user.adapters.jwt.JwtTokenGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(".well-known/jwks.json")
public class JwksController {

    private final JwtTokenGenerator jwtTokenGenerator;

    @GetMapping
    public String getJwks() {
        final String e = jwtTokenGenerator.e();
        final String n = jwtTokenGenerator.n();
        return String.format("""
                {
                  "keys": [
                    {
                      "kty": "RSA",
                      "e": "%s",
                      "n": "%s",
                      "use": "sig",
                      "kid": "test-key",
                      "alg": "RS256"
                    }
                  ]
                }""", e, n);
    }
}
