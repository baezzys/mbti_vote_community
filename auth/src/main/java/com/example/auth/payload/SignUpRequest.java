package com.example.auth.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.example.auth.domain.ERole;
import com.example.auth.domain.Mbti;
import com.example.auth.payload.validation.ValidERole;
import com.example.auth.payload.validation.ValidMbti;

public class SignUpRequest {

    private String username;
    private ERole role;
    private Mbti mbti;
    private String password;

    public SignUpRequest() {
    }

    public SignUpRequest(String username, ERole role, Mbti mbti, String password) {
        this.username = username;
        this.role = role;
        this.mbti = mbti;
        this.password = password;
    }

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
