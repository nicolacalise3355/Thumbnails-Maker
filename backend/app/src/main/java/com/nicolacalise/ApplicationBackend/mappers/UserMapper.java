package com.nicolacalise.ApplicationBackend.mappers;

import com.nicolacalise.ApplicationBackend.models.dtos.request.UserRequestDTO;
import com.nicolacalise.ApplicationBackend.models.dtos.response.UserResponseDTO;
import com.nicolacalise.ApplicationBackend.models.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getPassword());
    }

    public User toEntity(UserRequestDTO userDTO) {
        return new User(
                userDTO.getEmail(),
                userDTO.getPassword());
    }
}
