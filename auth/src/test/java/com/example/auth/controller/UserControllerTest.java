package com.example.auth.controller;

import com.example.auth.domain.ERole;
import com.example.auth.domain.Mbti;
import com.example.auth.domain.User;
import com.example.auth.payload.SignUpRequest;
import com.example.auth.payload.request.LoginRequest;
import com.example.auth.repository.UserRepository;
import com.example.auth.service.UserDetailsServiceImpl;
import com.example.auth.testcontainer.MbtiTestContainers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.converters.Auto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MbtiTestContainers
@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;

    @Autowired
    WebApplicationContext context;

    @Autowired
    PasswordEncoder passwordEncoder;

    protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            StandardCharsets.UTF_8);

    @BeforeEach
    public void init() {
        User user = new User("loginTest", passwordEncoder.encode("123ABCdef!@#"), Mbti.ESFJ);
        userRepository.save(user);
    }

    @Test
    @DisplayName("signUp test: Success")
    void signUpTest() throws Exception {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("testUser", ERole.ROLE_USER, Mbti.ESFJ, "23AB#@def");

        // when
        when(userRepository.existsByUsername(anyString())).thenReturn(false);

        // then
        mockMvc.perform(post("/auth/signup"))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));
    }

    @Test
    @DisplayName("signUp test: Fail(duplicated username)")
    void signUpDuplicatedName() throws Exception {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("loginTest", ERole.ROLE_USER, Mbti.INTP, "23AB#@def");

        // when & then
        mockMvc.perform(post("/auth/signup"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error: Username is already taken!"));

    }

    @Test
    @DisplayName("loginTest: success")
    void loginTestSuccess() throws Exception {

        // given
        String userName = "loginTest";
        String password = "123ABCdef!@#";
        LoginRequest loginRequest = new LoginRequest(userName, password);

        // when
        mockMvc.perform(post("/auth/signin"))
                .andExpect(status().isOk())
                .andExpect(content().string());
    }

    @Test
    @DisplayName("loginTest: fail")
    void loginTestFail() throws Exception{

        // given
        // Case1: both different
        String diffUsername = "testFail";
        String diffPassword = "dkenAB123@@";
        LoginRequest bothDiff = new LoginRequest(diffUsername, diffPassword);

        // Case2: password different
        String userName = "loginTest";
        LoginRequest passwordDiff = new LoginRequest(userName, diffPassword);

        // Case3: username different
        String password = "123ABCdef!@#";
        LoginRequest usernameDiff = new LoginRequest(diffUsername, password);

        mockMvc.perform(post("/auth/signin"))
                .andExpect(status().isBadRequest());
    }


}