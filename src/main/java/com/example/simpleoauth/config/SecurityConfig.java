package com.example.simpleoauth.config;

import com.example.simpleoauth.config.filter.JwtAuthFilter;
import com.example.simpleoauth.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider tokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain defaultFilter(HttpSecurity http) throws Exception {
        http.anonymous(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(request -> {
                    request.requestMatchers("/users/login").permitAll(); //users 의 login 만 허용
                    request.requestMatchers("/users").permitAll();
                }
        );
        http.authorizeHttpRequests(request -> {
            request.requestMatchers("/users/login-check").authenticated();
            request.requestMatchers("/users/logout").authenticated();
        });

        // jwt 필터 추가
        http.addFilterBefore(new JwtAuthFilter(tokenProvider, redisTemplate), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
