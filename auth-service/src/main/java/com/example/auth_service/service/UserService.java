package com.example.auth_service.service;

import com.example.auth_service.dto.CreateUserRequestDTO;
import com.example.auth_service.dto.CreateUserResponseDTO;
import com.example.auth_service.exception.ExistingEmailException;
import com.example.auth_service.exception.UserNotFoundException;
import com.example.auth_service.mapper.UserMapper;
import com.example.auth_service.model.User;
import com.example.auth_service.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CreateUserResponseDTO create(CreateUserRequestDTO request) {

        Optional<User> existingUser = userRepository.findUserByEmail(request.getEmail());

        if (existingUser.isPresent())
            throw new ExistingEmailException("Email already exists!");

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User saveUser = userRepository.save(user);

        return new CreateUserResponseDTO(
                saveUser.getId(),
                saveUser.getName(),
                saveUser.getEmail()
        );
    }

    public CreateUserResponseDTO getUserById(UUID id) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        return UserMapper.toDto(existingUser);
    }

}
