package com.example.auth.controller;

import com.example.auth.domain.ERole;
import com.example.auth.domain.Mbti;
import com.example.auth.payload.SignUpRequest;
import com.example.auth.repository.UserRepository;
import com.example.auth.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    MockMvc mvc;
    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("signup test")
    void signUp_Test() throws Exception {
        // given
        SignUpRequest signUpRequest = new SignUpRequest();
        String nickName = "testuser";
        String password = "testpassword";
        signUpRequest.setUsername(nickName);
        signUpRequest.setPassword(password);
        signUpRequest.setMbti(Mbti.ESFJ);
        signUpRequest.setRole(ERole.ROLE_USER);

        // when
        when(userRepository.existsByUsername(any())).thenReturn(false);

        // then
        mvc.perform(post("/signup"))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));

    }

}