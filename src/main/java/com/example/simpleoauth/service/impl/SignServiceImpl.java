package com.example.simpleoauth.service.impl;

import com.example.simpleoauth.domain.dto.SignInDto;
import com.example.simpleoauth.domain.dto.SignInResultDto;
import com.example.simpleoauth.domain.dto.SignUpDto;
import com.example.simpleoauth.domain.entity.User;
import com.example.simpleoauth.repository.AuthorityRepository;
import com.example.simpleoauth.repository.UserRepository;
import com.example.simpleoauth.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignServiceImpl implements SignService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void SignUp(SignUpDto dto) {
        if (checkExistEmail(dto.getEmail())) //이메일 중복검사
            throw new RuntimeException("중복된 이메일");
        var newUser = User.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        userRepository.save(newUser); // 저장
    }


    @Override
    public SignInResultDto SignIn(SignInDto dto) { //Todo 로그인 구현
        return null;
    }

    public boolean checkExistEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
