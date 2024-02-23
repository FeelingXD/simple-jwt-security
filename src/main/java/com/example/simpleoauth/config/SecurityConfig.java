package com.example.simpleoauth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
    @Bean
    public SecurityFilterChain defaultFilter(HttpSecurity http) throws Exception{
        http.csrf((csrf)->csrf.ignoringRequestMatchers("/**"));
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.headers(headersConfigurer-> headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        // 허용
        http.authorizeHttpRequests(request->
                request.requestMatchers("/h2-console/**").permitAll()
                );
        return http.build();
    }
}
