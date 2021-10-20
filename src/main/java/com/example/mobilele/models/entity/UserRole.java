package com.example.mobilele.models.entity;

import com.example.mobilele.models.enums.Role;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity(name = "user_roles")
public class UserRole extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private Role role;

    public UserRole() {
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
