package com.codewithdurgesh.blog2.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithdurgesh.blog2.entities.User;


public interface UserRepo extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);
}
