package com.example.simpleoauth.service;

import com.example.simpleoauth.domain.dto.SignInDto;
import com.example.simpleoauth.domain.dto.SignInResultDto;
import com.example.simpleoauth.domain.dto.SignUpDto;

public interface SignService {
    public void SignUp(SignUpDto dto);

    public boolean checkExistEmail(String email);

    public SignInResultDto SignIn(SignInDto dto);
}
