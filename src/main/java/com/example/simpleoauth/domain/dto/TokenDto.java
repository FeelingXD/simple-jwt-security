package com.example.simpleoauth.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDto {
    private String ATK; // access token
    private String RTK; // refresh token
}
