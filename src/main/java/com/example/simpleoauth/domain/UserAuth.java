package com.example.simpleoauth.domain;

import com.example.simpleoauth.domain.entity.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Builder
public class UserAuth implements UserDetails {
    private String username;
    private String password;
    private boolean isEnabled = true;
    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserAuth byUserAndAuthorities(User user, Collection<? extends GrantedAuthority> authorities) {
        return UserAuth.builder()
                .authorities(authorities)
                .username(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
