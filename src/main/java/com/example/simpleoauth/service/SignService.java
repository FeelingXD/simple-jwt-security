package com.example.simpleoauth.service;

import com.example.simpleoauth.domain.dto.ReIssueDto;
import com.example.simpleoauth.domain.dto.SignInDto;
import com.example.simpleoauth.domain.dto.SignInResultDto;
import com.example.simpleoauth.domain.dto.SignUpDto;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletRequest;


public interface SignService {
    public void SignUp(SignUpDto dto);

    public void logout(HttpServletRequest request);
    public ReIssueDto reIssue(HttpServletRequest request);


    public SignInResultDto SignIn(SignInDto dto);
}
