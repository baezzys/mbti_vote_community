package com.example.auth.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "user_roles")
@IdClass(UserRolesId.class)
public class UserRoles {

    @Id
    private int userId;
    @Id
    private int roleId;

    @ManyToOne
    private User user;

    @ManyToOne
    private Role role;

    public UserRoles(User user, Role role) {
        this.user = user;
        this.role = role;
        this.userId = user.getId();
        this.roleId = role.getId();
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
