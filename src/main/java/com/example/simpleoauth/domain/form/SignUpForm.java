package com.example.simpleoauth.domain.form;


import com.example.simpleoauth.domain.dto.SignUpDto;
import lombok.Data;

@Data
public class SignUpForm {
    private String email;
    private String password;
    private String name;

    public static SignUpDto toDto(SignUpForm form) {
        return SignUpDto.builder()
                .email(form.getEmail())
                .password(form.getPassword())
                .name(form.getName())
                .build();
    }
}
