package com.example.simpleoauth.domain.form;

import com.example.simpleoauth.domain.dto.SignInDto;
import lombok.Data;

@Data
public class SignInForm {
    private String email;
    private String password;

    public static SignInDto toDto(SignInForm form) {
        return SignInDto.builder()
                .email(form.getEmail())
                .password(form.getPassword())
                .build();

    }
}
