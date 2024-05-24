package org.example.user.adapters.jwt;

import org.example.user.domain.TokenGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class JwtTokenGeneratorTest {

    private TokenGenerator tokenGenerator;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        tokenGenerator = new JwtTokenGenerator("secret");
    }

    @Test
    void should_generate_token_with_uuid() {
        String uuid = "00000000-0000-0000-0000-000000000001";

        String token = tokenGenerator.generateToken(uuid);

        final String JwtTokenPattern = "[a-zA-Z0-9-_=]+\\.[a-zA-Z0-9-_=]+\\.[a-zA-Z0-9-_=]+";
        assertThat(token).matches(Pattern.compile(JwtTokenPattern));
        final String algorithmSection = new String(Base64.getDecoder().decode(token.split("\\.")[0]));
        assertThat(algorithmSection).isEqualTo("{\"alg\":\"RS256\",\"typ\":\"JWT\"}");
        final String payloadSection = new String(Base64.getDecoder().decode(token.split("\\.")[1]));
        assertThat(payloadSection).isEqualTo("{\"iss\":\"fpsb\",\"exp\":31556889864403199,\"sub\":\"00000000-0000-0000-0000-000000000001\"}");
    }
}
