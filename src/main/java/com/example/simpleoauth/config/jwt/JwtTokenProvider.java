package com.example.simpleoauth.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenProvider {
    @Value("${jwt.auth.atk}")
    private String ACCESS_TOKEN_HEADER;

    @Value("${jwt.auth.rtk}")
    private String REFRESH_TOKEN_HEADER;

    private final SecretKey secretKey;
    private final UserDetailsService userDetailsService;
    private final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 60; //60m
    private final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24;//24h


    public Authentication getAuthentication(String token) { // token 으로부터 권한을 추출하는 메서드
        UserDetails userDetails = userDetailsService.loadUserByUsername(extractUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 생성자
    public JwtTokenProvider(@Value("${jwt.secretKey}") String seed, UserDetailsService userDetailsService) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(seed));
        this.userDetailsService = userDetailsService;
    }


    public String generateToken(String email) {
        var user_auth = userDetailsService.loadUserByUsername(email);
        return generateToken(new HashMap<>(), user_auth);
    }

    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts
                .builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(secretKey)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getClaims(token);
        return claimResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private boolean isTokenExpired(String token) {
        return extractExpired(token).before(new Date());
    }

    private Date extractExpired(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Claims getClaims(String token) {
        var info = Jwts.parser().
                verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return info;
    }


    public String resolveATK(HttpServletRequest request) {
        return request.getHeader(ACCESS_TOKEN_HEADER);
    }

    public boolean validateToken(String token) {
        return isTokenValid(token, userDetailsService.loadUserByUsername(extractUsername(token)));
    }
}
