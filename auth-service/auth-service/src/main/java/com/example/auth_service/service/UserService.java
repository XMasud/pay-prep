package com.example.auth_service.service;

import com.example.auth_service.dto.CreateUserRequestDTO;
import com.example.auth_service.dto.CreateUserResponseDTO;
import com.example.auth_service.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CreateUserResponseDTO create(CreateUserRequestDTO user){

    }
}
