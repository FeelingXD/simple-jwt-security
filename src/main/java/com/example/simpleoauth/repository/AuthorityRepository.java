package com.example.simpleoauth.repository;


import com.example.simpleoauth.domain.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {//todo 유저 권한만 있는 레포

    List<Authority> findAuthoritiesByEmail(String email);
}
