package com.example.auth.controller;

import com.example.auth.payload.SignUpRequest;
import com.example.auth.testcontainer.MbtiTestContainers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MbtiTestContainers
@AutoConfigureMockMvc
@SpringBootTest
public class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void signUpTestWithWrongRequest() throws Exception {

        // given
        SignUpRequest signUpRequest = new SignUpRequest();

        // when
        mockMvc.perform(post("/auth/signup", signUpRequest)).andExpect(status().isBadRequest());

        // then
    }
}
