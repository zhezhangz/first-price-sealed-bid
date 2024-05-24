package org.example.user.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public String login(String username) {
        if (!userRepository.existsUsername(username)) {
            throw new InvalidUser();
        }
        return "succeed";
    }
}
