package org.example.user.adapters.sql;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "users")
@Getter
public class UserPersistModel {

    @Id
    private String id;

    private String uuid;

    private String username;
}
