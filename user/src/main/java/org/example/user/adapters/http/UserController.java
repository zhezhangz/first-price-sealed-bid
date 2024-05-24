package org.example.user.adapters.http;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.user.domain.InvalidUser;
import org.example.user.domain.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
@Slf4j
public class UserController {

    private final UserService userService;

    @PutMapping("login")
    public String login(@RequestBody LoginRequest loginRequest) {
        final String username = loginRequest.username();
        log.info("About to login user {}", username);
        // Todo: For demo, `password` will be ignored here
        try {
            return userService.login(username);
        } catch (InvalidUser e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
