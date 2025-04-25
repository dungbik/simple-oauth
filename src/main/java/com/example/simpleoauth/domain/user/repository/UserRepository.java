package com.example.simpleoauth.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.simpleoauth.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
