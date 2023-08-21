package com.example.auth.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "user_roles")
@IdClass(UserRoleId.class)
public class UserRole {

    @ManyToOne
    @Id
    private User user;

    @ManyToOne
    @Id
    private Role role;

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public UserRole() {

    }

    public User getUser() {
        return user;
    }

    public Role getRole() {
        return role;
    }
}
