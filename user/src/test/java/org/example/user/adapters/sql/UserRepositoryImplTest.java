package org.example.user.adapters.sql;

import org.example.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql(scripts = {"/database_init/init_user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UserRepositoryImplTest {

    private UserRepository userRepository;

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryImpl(jpaUserRepository);
    }

    @Test
    void should_exist_with_valid_username() {
        final boolean existed = userRepository.existsUsername("test-user");

        assertThat(existed).isTrue();
    }

    @Test
    void should_not_exist_with_invalid_username() {
        final boolean existed = userRepository.existsUsername("invalid-username");

        assertThat(existed).isFalse();
    }
}
