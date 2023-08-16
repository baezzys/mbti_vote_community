package com.example.auth.service;

import com.example.auth.domain.Mbti;
import com.example.auth.domain.User;
import com.example.auth.repository.UserRepository;
import com.example.auth.testcontainer.MbtiTestContainers;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@MbtiTestContainers
@Transactional
@SpringBootTest
class UserDetailsServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void loadUserByUsernameSuccess() {
        // given
        String username = "testUser";
        User user = new User(username, "password", Mbti.ESFJ);
        userRepository.save(user);

        // when
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // then
        assertNotNull(userDetails);
        Assertions.assertThat(username).isEqualTo(userDetails.getUsername());
    }

    @Test
    void loadUserByUsernameFail() {
        // given
        String username = "testUser";
        String failName = "testUserFail";
        User user = new User(username, "123Abc@1", Mbti.ESFJ);
        userRepository.save(user);

        // when
        UserDetails userDetails = userDetailsService.loadUserByUsername(failName);

        // then
        assertNull(userDetails);
    }

    @Test
    void loadUserByUsernameNotMatch() {
        // given
        String username1 = "testUser1";
        String username2 = "testUser2";
        User user1 = new User(username1, "123Abc@1", Mbti.ESFJ);
        User user2 = new User(username2, "456aBc@2", Mbti.INTP);
        userRepository.save(user1);
        userRepository.save(user2);

        // when
        UserDetails userDetails1 = userDetailsService.loadUserByUsername(username1);
        UserDetails userDetails2 = userDetailsService.loadUserByUsername(username2);

        // then
        Assertions.assertThat(userDetails1).isNotEqualTo(userDetails2);
    }
}