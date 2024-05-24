package org.example.user.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final TokenGenerator tokenGenerator;

    public String login(String username) {
        return userRepository.getUuidByUserName(username)
                .map(tokenGenerator::generateToken)
                .orElseThrow(InvalidUser::new);
    }
}
