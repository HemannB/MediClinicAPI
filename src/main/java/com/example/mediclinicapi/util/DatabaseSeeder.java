package com.example.mediclinicapi.util;

import com.example.mediclinicapi.domain.User;
import com.example.mediclinicapi.domain.enums.Role;
import com.example.mediclinicapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private static final String ADMIN_EMAIL = "admin@mediclinic.com";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail(ADMIN_EMAIL).isEmpty()) {
            User admin = new User();
            admin.setEmail(ADMIN_EMAIL);

            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);
            System.out.println("Usuário administrador criado");
        }
    }
}
