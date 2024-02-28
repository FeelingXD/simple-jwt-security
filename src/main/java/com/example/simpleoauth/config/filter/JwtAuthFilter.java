package com.example.simpleoauth.config.filter;

import com.example.simpleoauth.config.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var token = jwtTokenProvider.resolveATK(request); // request 에서 ATK 가져오기

        if (token != null && jwtTokenProvider.validateToken(token)) {// 토큰이 비어있지안히고 검증되는 토큰일 경우 로그인 정보생성
            var authentication = jwtTokenProvider.getAuthentication(token); // token 으로 부터 권한 가져오기
            SecurityContextHolder.getContext().setAuthentication(authentication); // 해당 스레드의 security에 권한 지정
        }
        filterChain.doFilter(request, response);
    }

}
