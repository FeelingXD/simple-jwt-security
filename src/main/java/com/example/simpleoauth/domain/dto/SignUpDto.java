package com.example.simpleoauth.domain.dto;

import com.example.simpleoauth.domain.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpDto {
    private String email;
    private String password;
    private String name;

    public static User toEntity(SignUpDto dto) {
        return User.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .password(dto.getPassword())
                .build();
    }
}
