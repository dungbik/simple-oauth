package com.example.simpleoauth.domain.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.simpleoauth.domain.auth.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
