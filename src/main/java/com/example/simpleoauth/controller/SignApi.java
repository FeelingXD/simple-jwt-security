package com.example.simpleoauth.controller;

import com.example.simpleoauth.domain.dto.ReIssueDto;
import com.example.simpleoauth.domain.dto.SignInResultDto;
import com.example.simpleoauth.domain.form.SignInForm;
import com.example.simpleoauth.domain.form.SignUpForm;
import com.github.feelingxd.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;

public interface SignApi {
    @Operation(summary = "회원가입", description = "폼을 입력받아 회원가입을 합니다.")
    public ApiResponse<Void> signUp(SignUpForm form);

    @Operation(summary = "회원 로그인")
    public ApiResponse<SignInResultDto> login(SignInForm form);

    @Operation(summary = "회원 로그아웃", description = "로그아웃")
    public ApiResponse<Void> logout(HttpServletRequest request);

    @Operation(summary = "로그인 여부를 체크합니다. ", description = "로그인을 체크하는 endpoint입니다. ")

    public ApiResponse<Void> loginCheck();

    @Operation(summary = "reissue", description = "ATK 만료시 RTK를 헤더에서 추출하여 ATK를 갱신 합니다.")
    public ApiResponse<ReIssueDto> reissue(HttpServletRequest req);
}
