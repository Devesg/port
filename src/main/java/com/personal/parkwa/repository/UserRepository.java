package com.personal.parkwa.repository;

import java.util.Optional;

import com.personal.parkwa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    User findByName(String name);
}
