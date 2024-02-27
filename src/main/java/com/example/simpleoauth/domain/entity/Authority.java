package com.example.simpleoauth.domain.entity;

import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public class Authority extends BaseEntity {
    private String email;

    private String authority;
}
