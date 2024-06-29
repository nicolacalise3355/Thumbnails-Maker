package com.calise.tb_backend.mapper;

import com.calise.tb_backend.enums.RoleEnum;
import com.calise.tb_backend.models.dtos.request.UserRequestDto;
import com.calise.tb_backend.models.dtos.response.UserResponseDto;
import com.calise.tb_backend.models.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDto toDto(User user) {
        UserResponseDto dto = new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getUsername()
        );
        return dto;
    }

    public User toEntity(UserRequestDto userRequestDTO) {
        return new User(
                userRequestDTO.getUsername(),
                userRequestDTO.getPassword(),
                userRequestDTO.getEmail(),
                RoleEnum.USER
        );
    }
}
