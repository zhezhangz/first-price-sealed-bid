package org.example.user.domain;

public interface UserRepository {

    boolean existsUsername(String username);
}
