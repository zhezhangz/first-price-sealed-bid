package org.example.user.domain;

import java.util.Optional;

public interface UserRepository {

    Optional<String> getUuidByUserName(String username);
}
