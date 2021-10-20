package com.example.mobilele.init;

import com.example.mobilele.models.entity.User;
import com.example.mobilele.models.entity.UserRole;
import com.example.mobilele.models.enums.Role;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.repository.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;


    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void run(String... args) {
        initializeRoles();
        initializeUsers();

    }

    private void initializeRoles() {
        UserRole userRole = new UserRole();
        userRole.setRole(Role.ADMIN);
        userRole.setId(1L);
        userRoleRepository.save(userRole);
        userRole = new UserRole();
        userRole.setRole(Role.USER);
        userRole.setId(2L);
        userRoleRepository.save(userRole);

    }

    private void initializeUsers() {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setActive(true);
            admin.setUsername("admin");
            admin.setFirstName("Admin");
            admin.setLastName("Adminov");
            admin.setPassword(passwordEncoder.encode("Admin123"));
            admin.setRole(userRoleRepository.getById(1L));
            admin.setModified(LocalDateTime.now());
            admin.setCreated(LocalDateTime.now());
            admin.setImageUrl("https://www.bikecenter.bg/media/catalog/product/cache/1/image/540x/85e4522595efc69f496374d01ef2bf13/_/v/_vp_872.jpg");
            userRepository.save(admin);

        }
    }
}
