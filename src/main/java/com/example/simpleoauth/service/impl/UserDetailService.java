package com.example.simpleoauth.service.impl;

import com.example.simpleoauth.domain.UserAuth;
import com.example.simpleoauth.domain.entity.Authority;
import com.example.simpleoauth.repository.AuthorityRepository;
import com.example.simpleoauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var current_user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("유저 없음"));
        var authority = getAuthorities(current_user.getEmail());

        return UserAuth.byUserAndAuthorities(current_user, authority);
    }

    public Collection<GrantedAuthority> getAuthorities(String email) {
        List<Authority> authorityList = authorityRepository.findAuthoritiesByEmail(email);
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Authority authority : authorityList) {
            authorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }
        return authorities;
    }

}
