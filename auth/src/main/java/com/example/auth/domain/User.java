package com.example.auth.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private String password;

    public Mbti getMbti() {
        return mbti;
    }

    private Mbti mbti;

    @OneToMany(mappedBy = "user")
    private Set<UserRole> roles = new HashSet<>();

    public User() {
    }

    public User(String username, String password, Mbti mbti) {
        this.username = username;
        this.password = password;
        this.mbti = mbti;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void addRole(Role role) {
        UserRole userRole = new UserRole(this, role);
        this.roles.add(userRole);
    }
}
