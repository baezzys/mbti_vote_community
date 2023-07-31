package com.example.auth.repository;

import com.example.auth.domain.Mbti;
import com.example.auth.domain.User;
import com.example.auth.service.UserDetailsServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    private UserRepository userRepository;
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    public void clear() {
        userRepository.deleteAll();
    }

    @Test
    void findByUserNameSuccess() {
        // given
        String username = "testUser";
        User user = new User(username, "password", Mbti.ESFJ);
        userRepository.save(user);

        // when
        Optional<User> findUser = userRepository.findByUsername("testUser");

        // then
        Assertions.assertThat(user).isEqualTo(findUser);
    }

    @Test
    void findByUserNameFail() {
        // given
        String username = "testUser";
        User user = new User(username, "password", Mbti.ESFJ);
        userRepository.save(user);

        // when
        Optional<User> findUser = userRepository.findByUsername("testUserFail");

        // then
        Assertions.assertThat(user).isNotEqualTo(findUser);
    }

}