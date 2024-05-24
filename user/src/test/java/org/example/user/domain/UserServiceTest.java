package org.example.user.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    void should_throw_invalid_user_when_user_doesnt_exist() {
        doReturn(false).when(userRepository).existsUsername(anyString());

        final Exception exception = catchException(() -> userService.login("test-user"));

        assertThat(exception).isInstanceOf(InvalidUser.class);
    }

    @Test
    void should_return_succeed_when_user_exists() {
        doReturn(true).when(userRepository).existsUsername(anyString());

        final String result = userService.login("test-user");

        assertThat(result).isEqualTo("succeed");
    }
}
