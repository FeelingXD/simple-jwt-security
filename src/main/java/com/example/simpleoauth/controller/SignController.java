package com.example.simpleoauth.controller;

import com.example.simpleoauth.domain.dto.SignInResultDto;
import com.example.simpleoauth.domain.form.SignInForm;
import com.example.simpleoauth.domain.form.SignUpForm;
import com.example.simpleoauth.service.SignService;
import com.github.feelingxd.ApiResponse;
import com.github.feelingxd.example.ExampleResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class SignController {
    private final SignService signService;

    @PostMapping //회원가입
    public ApiResponse<Void> signUp(@RequestBody SignUpForm form) {
        signService.SignUp(SignUpForm.toDto(form));
        return ApiResponse.<Void>builder().code(ExampleResponseCode.RESPONSE_CREATED).build();
    }

    @PostMapping("/login") //로그인
    public ApiResponse<SignInResultDto> login(@RequestBody SignInForm form) {
        var data = signService.SignIn((SignInForm.toDto(form)));
        return ApiResponse.<SignInResultDto>builder().code(ExampleResponseCode.RESPONSE_SUCCESS).data(data).build();
    }

    @GetMapping("/login-check")
    public ApiResponse<Void> loginCheck() {
        return ApiResponse.<Void>builder().code(ExampleResponseCode.RESPONSE_SUCCESS).build();
    }
}
