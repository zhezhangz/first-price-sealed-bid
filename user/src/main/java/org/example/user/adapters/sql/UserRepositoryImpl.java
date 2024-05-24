package org.example.user.adapters.sql;

import lombok.RequiredArgsConstructor;
import org.example.user.domain.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public boolean existsUsername(String username) {
        return jpaUserRepository.existsByUsername(username);
    }
}
