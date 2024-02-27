package com.example.simpleoauth.repository;

import com.example.simpleoauth.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> { // 유저 레포
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
}
