package com.example.auth_service.mapper;

import com.example.auth_service.dto.CreateUserRequestDTO;
import com.example.auth_service.dto.CreateUserResponseDTO;
import com.example.auth_service.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class UserMapper {

    private static PasswordEncoder passwordEncoder;

    public static CreateUserResponseDTO toDto(User user){

        CreateUserResponseDTO userDTO = new CreateUserResponseDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        user.setEmail(user.getEmail());

        return userDTO;
    }

    public static User toModel(CreateUserRequestDTO userDTO){

        User newUser = new User();

        newUser.setName(userDTO.getName());
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        return newUser;
    }
}
