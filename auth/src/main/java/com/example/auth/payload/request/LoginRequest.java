package com.example.auth.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LoginRequest {

    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 20, message = "이름을 2~20자 사이로 입력해주세요")
    private String username;

    @NotBlank(message = "비밀번호는 대소문자 구분, 숫자, 특수문자가 포함되어야 하며 6~20자 사이로 입력해주세요.")
    @Pattern(regexp = "(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*\\W).{6,20}$")
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}