package com.nicolacalise.ApplicationBackend.validators;

import com.nicolacalise.ApplicationBackend.models.dtos.request.UserRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public boolean isUserValid(UserRequestDTO userRequestDTO) {
        return isUserNotNull(userRequestDTO) &&
                isPasswordValid(userRequestDTO);
    }

    private boolean isUserNotNull(UserRequestDTO userRequestDTO) {
        return userRequestDTO.getEmail() != null &&
                userRequestDTO.getPassword() != null;
    }

    private boolean isPasswordValid(UserRequestDTO userRequestDTO) {
        String userPass = userRequestDTO.getPassword().toLowerCase();

        return userRequestDTO.getPassword().length() > 8 &&
                !userPass.equals(userRequestDTO.getPassword()) &&
                userRequestDTO.getPassword().matches(".*\\d.*");
    }
}
