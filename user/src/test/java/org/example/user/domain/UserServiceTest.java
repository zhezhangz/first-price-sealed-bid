package org.example.user.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenGenerator tokenGenerator;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, tokenGenerator);
    }

    @Test
    void should_throw_invalid_user_when_user_doesnt_exist() {
        doReturn(Optional.empty()).when(userRepository).getUuidByUserName(anyString());

        final Exception exception = catchException(() -> userService.login("test-user"));

        assertThat(exception).isInstanceOf(InvalidUser.class);
        verify(tokenGenerator, times(0)).generateToken(anyString());
    }

    @Test
    void should_return_succeed_when_user_exists() {
        doReturn(Optional.of("uuid")).when(userRepository).getUuidByUserName(anyString());
        doReturn("mock-token").when(tokenGenerator).generateToken("uuid");

        final String result = userService.login("test-user");

        assertThat(result).isEqualTo("mock-token");
    }
}
