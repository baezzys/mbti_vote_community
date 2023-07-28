package com.example.auth.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_roles")
public class UserRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Role role;

    public UserRoles(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public UserRoles() {

    }

    public User getUser() {
        return user;
    }

    public Role getRole() {
        return role;
    }
}
