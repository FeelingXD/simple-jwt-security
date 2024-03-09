package com.example.simpleoauth.service.impl;

import com.example.simpleoauth.config.jwt.JwtTokenProvider;
import com.example.simpleoauth.domain.dto.ReIssueDto;
import com.example.simpleoauth.domain.dto.SignInDto;
import com.example.simpleoauth.domain.dto.SignInResultDto;
import com.example.simpleoauth.domain.dto.SignUpDto;
import com.example.simpleoauth.domain.entity.User;
import com.example.simpleoauth.repository.AuthorityRepository;
import com.example.simpleoauth.repository.UserRepository;
import com.example.simpleoauth.service.SignService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class SignServiceImpl implements SignService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final RedisTemplate<String, String> redisTemplate;
    @Value("${jwt.rtk-prefix}")
    String RTK_PREFIX;
    @Value("${jwt.auth.rtk}")
    String RTK_HEADER;
    @Value("${jwt.auth.atk}")
    String ATK_HEADER;

    private final RedisTemplate<String, String> redisTemplate;

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

    public void logout(HttpServletRequest request) { //Todo if you logout you may remove your RTK(refresh token)
        var atk = request.getHeader("ATK");
        redisTemplate.opsForValue().set(atk, "logout"); // set ATK logout
        redisTemplate.delete(RTK_PREFIX + tokenProvider.extractUsername(atk)); // remove RTK
    }


    @Override
    public SignInResultDto SignIn(SignInDto dto) { //Todo 로그인 구현
        var user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("이메일이 일치하지 않음"));
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) { // 비밀번호가 일치하지 않다면
            throw new RuntimeException("비밀번호 불일치");
        }
        var atk = generateATK(user.getEmail());
        var rtk = generateRTK(); // RTK(refresh token) if you need add custom subject in token this time create empty token :)

        redisTemplate.opsForValue().set(RTK_PREFIX + user.getEmail(), rtk, tokenProvider.extractClaim(rtk, claims -> claims.getExpiration().getTime()), TimeUnit.MILLISECONDS);

        redisTemplate.opsForValue().set(rtk, user.getEmail(), tokenProvider.extractClaim(rtk, claims -> claims.getExpiration().getTime()), TimeUnit.MILLISECONDS);
        return SignInResultDto.builder()
                .ATK(atk)
                .RTK(rtk)
                .build();
    }

    private String generateATK(String email) {
        return tokenProvider.generateToken(email);
    }

    private String generateRTK() {
        return tokenProvider.generateRTKToken();
    }

    public boolean checkExistEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public ReIssueDto reIssue(HttpServletRequest request) { // Todo atk 가 만료일때 rtk로 검증하여 새로운 atk를 반환한다. rtk 가 만료일때는 새로운 로그인을 요청한다.
        var atk = request.getHeader(ATK_HEADER);
        var rtk = request.getHeader(RTK_HEADER);
        var user_email = redisTemplate.opsForValue().get(rtk);
        if (!tokenProvider.isRTKValid(rtk)) {
            return ReIssueDto.builder()
                    .ATK(generateATK(user_email))
                    .build();
        } else { // RTK expired
            throw new RuntimeException("RTK 만료 새로 로그인해 주세요");
        }
    }
}
