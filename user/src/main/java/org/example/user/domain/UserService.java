package org.example.user.domain;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public String login(String username) {
        return "succeed";
    }
}
