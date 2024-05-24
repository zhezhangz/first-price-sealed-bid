package org.example.user.adapters.sql;

import lombok.RequiredArgsConstructor;
import org.example.user.domain.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public Optional<String> getUuidByUserName(String username) {
        return jpaUserRepository.findByUsername(username)
                .map(UserPersistModel::getUuid);
    }
}
