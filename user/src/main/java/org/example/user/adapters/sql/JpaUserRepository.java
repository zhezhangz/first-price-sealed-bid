package org.example.user.adapters.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<UserPersistModel, String> {

    Optional<UserPersistModel> findByUsername(String username);
}
