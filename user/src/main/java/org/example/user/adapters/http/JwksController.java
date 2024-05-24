package org.example.user.adapters.http;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(".well-known/jwks.json")
public class JwksController {

    @GetMapping
    public String getJwks() {
        return """
                {
                  "keys": [
                    {
                      "kty": "RSA",
                      "e": "AQAB",
                      "use": "sig",
                      "kid": "test-key",
                      "alg": "RS256",
                      "n": "test-key"
                    }
                  ]
                }""";
    }
}
