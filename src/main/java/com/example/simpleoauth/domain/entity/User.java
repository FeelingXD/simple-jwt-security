package com.example.simpleoauth.domain.entity;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
public class User extends BaseEntity {
    private String email;
    private String name;
    private String password;
}
