package org.example.user.adapters.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<UserPersistModel, String> {

    boolean existsByUsername(String username);
}
