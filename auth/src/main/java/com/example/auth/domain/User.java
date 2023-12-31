package com.example.auth.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    public Mbti getMbti() {
        return mbti;
    }

    private Mbti mbti;

    @OneToMany(mappedBy = "user")
    private Set<UserRoles> roles = new HashSet<>();

    public User() {
    }

    public User(String username, String password, Mbti mbti) {
        this.username = username;
        this.password = password;
        this.mbti = mbti;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<UserRoles> getRoles() {
        return roles;
    }

    public void addRole(Role role) {
        UserRoles userRoles = new UserRoles(this, role);
        this.roles.add(userRoles);
    }
}
