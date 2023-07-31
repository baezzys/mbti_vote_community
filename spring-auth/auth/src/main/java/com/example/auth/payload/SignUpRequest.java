package com.example.auth.payload;

import java.util.Set;

import com.example.auth.domain.ERole;
import com.example.auth.domain.Mbti;
import com.example.auth.payload.validation.ValidERole;
import com.example.auth.payload.validation.ValidMbti;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {

    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 20, message = "이름을 2~20자 사이로 입력해주세요")
    private String username;

    @NotBlank(message = "Role을 선택해주세요.")
    @ValidERole
    private ERole role;

    @NotBlank(message = "MBTI 타입을 선택해 주세요.")
    @ValidMbti
    private Mbti mbti;

    @NotBlank(message = "비밀번호는 대소문자 구분, 숫자, 특수문자가 포함되어야 하며 6~20자 사이로 입력해주세요.")
    @Pattern(regexp = "(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*\\W).{6,20}$")
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
