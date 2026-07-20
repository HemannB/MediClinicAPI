package com.example.mediclinicapi.repository;

import com.example.mediclinicapi.domain.User;
import com.example.mediclinicapi.domain.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findByRole(Role role);
}
