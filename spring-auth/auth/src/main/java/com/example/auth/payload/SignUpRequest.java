package com.example.auth.payload;

import java.util.Set;

import com.example.auth.domain.ERole;
import com.example.auth.domain.Mbti;

public class SignUpRequest {

    private String username;

    private ERole role;

    private Mbti mbti;

    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ERole getRole() {
        return role;
    }

    public Mbti getMbti() {
        return mbti;
    }

}
