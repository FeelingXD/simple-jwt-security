package com.example.simpleoauth.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("Simple-Jwt-Security")
                .description("SpringSecurity 와 jjwt(jwt)를 사용하는 예제 입니다.")
                .version("1.0.0");
    }

    private Components components() {
        return new Components()
                .addSecuritySchemes("Authorization(ATK)", ATKSecurityScheme());
    }

    private SecurityScheme ATKSecurityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .name("ATK")
                .in(SecurityScheme.In.HEADER)
                .scheme("JWT")
                .bearerFormat("ATK");
    }

}
