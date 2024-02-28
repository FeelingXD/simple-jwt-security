package com.example.simpleoauth.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInResultDto {
    private String ATK;
    private String RTK;

    public SignInResultDto toResultDtofromTokenDto(TokenDto dto) {
        return SignInResultDto.builder()
                .ATK(dto.getATK())
                .RTK(dto.getRTK())
                .build();
    }
}
