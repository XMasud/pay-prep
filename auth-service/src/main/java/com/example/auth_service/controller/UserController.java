package com.example.auth_service.controller;

import com.example.auth_service.dto.CreateUserRequestDTO;
import com.example.auth_service.dto.CreateUserResponseDTO;
import com.example.auth_service.dto.LoginRequestDTO;
import com.example.auth_service.model.User;
import com.example.auth_service.repository.UserRepository;
import com.example.auth_service.service.UserService;
import com.example.auth_service.util.JWTUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserRepository userRepository) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<CreateUserResponseDTO> create(@RequestBody CreateUserRequestDTO userDTO){
        CreateUserResponseDTO newUser = userService.create(userDTO);
        return ResponseEntity.ok().body(newUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreateUserResponseDTO> getUserById(@PathVariable UUID id){
        CreateUserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok().body(user);
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

        User user = userRepository.findUserByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(user);

        return ResponseEntity.ok(Map.of("token", token));
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello, authenticated user!");
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        System.out.println("Hey ----");
        return ResponseEntity.ok().build();
    }
}
