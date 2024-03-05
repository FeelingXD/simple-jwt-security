package com.example.simpleoauth.service.impl;

import com.example.simpleoauth.config.jwt.JwtTokenProvider;
import com.example.simpleoauth.domain.dto.SignInDto;
import com.example.simpleoauth.domain.dto.SignInResultDto;
import com.example.simpleoauth.domain.dto.SignUpDto;
import com.example.simpleoauth.domain.entity.User;
import com.example.simpleoauth.repository.AuthorityRepository;
import com.example.simpleoauth.repository.UserRepository;
import com.example.simpleoauth.service.SignService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class SignServiceImpl implements SignService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    private final RedisTemplate<String,String> redisTemplate;

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
    public void logout(HttpServletRequest request) {
        var ATK=request.getHeader("ATK");
        var expiration = tokenProvider.extractExpired(ATK);
        redisTemplate.opsForValue().set(ATK,"logout");

    }



    @Override
    public SignInResultDto SignIn(SignInDto dto) { //Todo 로그인 구현
        var user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("이메일이 일치하지 않음"));
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) { // 비밀번호가 일치하지 않다면
            throw new RuntimeException("비밀번호 불일치");
        }
        var atk = generateATK(dto.getEmail());

        return SignInResultDto.builder()
                .ATK(atk)
                .RTK(null)
                .build();
    }

    private String generateATK(String email) {
        return tokenProvider.generateToken(email);
    }

    public boolean checkExistEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
