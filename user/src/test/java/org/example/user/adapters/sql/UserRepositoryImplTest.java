package org.example.user.adapters.sql;

import org.example.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

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
        final Optional<String> uuid = userRepository.getUuidByUserName("test-user");

        assertThat(uuid).hasValue("00000000-0000-0000-0000-000000001001");
    }

    @Test
    void should_not_exist_with_invalid_username() {
        final Optional<String> uuid = userRepository.getUuidByUserName("invalid-username");

        assertThat(uuid).isEmpty();
    }
}
