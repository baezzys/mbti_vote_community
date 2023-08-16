package com.example.auth.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.example.auth.domain.Mbti;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.domain.ERole;
import com.example.auth.domain.Role;
import com.example.auth.domain.User;
import com.example.auth.payload.SignUpRequest;
import com.example.auth.payload.request.LoginRequest;
import com.example.auth.payload.response.JwtResponse;
import com.example.auth.payload.response.MessageResponse;
import com.example.auth.repository.UserRepository;
import com.example.auth.security.jwt.JwtUtils;
import com.example.auth.service.UserDetailsImpl;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.encoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        if (!isUsernameValid(loginRequest.getUsername()) || !isPasswordValid(loginRequest.getPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Invalid request"));
        }

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String jwt = jwtUtils.generateJwtToken(authentication);

        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        final List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {

        if(!isValid(signUpRequest)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Invalid request"));
        }

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        final User user = new User(signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getMbti());

        final ERole erole = signUpRequest.getRole();
        final Role role = new Role(erole);
        user.addRole(role);

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

    public boolean isValid(SignUpRequest signUpRequest) {
        if(!isPasswordValid(signUpRequest.getPassword()) ||
                !isUsernameValid(signUpRequest.getUsername()) ||
                !isMbtiValid(signUpRequest.getMbti()) ||
                !isERoleValid(signUpRequest.getRole())) {
            return false;
        }

        return true;
    }

    public boolean isPasswordValid(String password) {
        String regex = "(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*\\W).{6,20}$";
        if (password == null) {
            return false;
        }

        return password.matches(regex);
    }

    public boolean isUsernameValid(String username) {
        String regex = ".*[^a-zA-Z0-9].*";

        if (username == null && username.matches(regex)) {
            return false;
        }
        if(username.length() < 2 || username.length() > 20) {
            return false;
        }
        return true;
    }

    public boolean isMbtiValid(Mbti mbti) {
        if (mbti == null) {
            return false;
        }

        for (Mbti name : Mbti.values()) {
            if (mbti.name().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean isERoleValid(ERole role) {
        return role != null && (role == ERole.ROLE_USER || role == ERole.ROLE_ADMIN);
    }
}
