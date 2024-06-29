package com.calise.tb_backend.validators;

import com.calise.tb_backend.models.dtos.request.UserRequestDto;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public boolean isUserValid(UserRequestDto userRequestDTO) {
        return isUserNotNull(userRequestDTO) &&
                isPasswordValid(userRequestDTO);
    }

    private boolean isUserNotNull(UserRequestDto userRequestDTO) {
        return userRequestDTO.getEmail() != null &&
                userRequestDTO.getPassword() != null;
    }

    private boolean isPasswordValid(UserRequestDto userRequestDTO) {
        String userPass = userRequestDTO.getPassword().toLowerCase();

        return userRequestDTO.getPassword().length() > 8 &&
                !userPass.equals(userRequestDTO.getPassword()) &&
                userRequestDTO.getPassword().matches(".*\\d.*");
    }
}
