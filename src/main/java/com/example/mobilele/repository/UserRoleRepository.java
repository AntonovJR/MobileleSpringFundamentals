package com.example.mobilele.repository;

import com.example.mobilele.models.entity.UserRole;
import com.example.mobilele.models.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByRole(Role role);
}
