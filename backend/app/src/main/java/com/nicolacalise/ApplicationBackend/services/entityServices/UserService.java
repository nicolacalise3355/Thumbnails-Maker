package com.nicolacalise.ApplicationBackend.services.entityServices;

import com.nicolacalise.ApplicationBackend.dao.UserDAO;
import com.nicolacalise.ApplicationBackend.exceptions.entities.UserException;
import com.nicolacalise.ApplicationBackend.mappers.UserMapper;
import com.nicolacalise.ApplicationBackend.models.dtos.request.UserRequestDTO;
import com.nicolacalise.ApplicationBackend.models.dtos.response.UserResponseDTO;
import com.nicolacalise.ApplicationBackend.models.entities.User;
import com.nicolacalise.ApplicationBackend.staticvalues.Codes;
import com.nicolacalise.ApplicationBackend.staticvalues.entitiesMessages.UserMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserDAO userRepository;
    private UserMapper userMapper;

    @Autowired
    public UserService(UserDAO userRepository, UserMapper userMapper) {
        this.userRepository =  userRepository;
        this.userMapper = userMapper;
    }

    public UserResponseDTO getUserById(long id) throws UserException {
        return this.userMapper.toDTO(this.userRepository.findById(id).orElseThrow(() -> new UserException(UserMessages.USER_NOT_FOUND, Codes.ERROR_CODE)));
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public UserResponseDTO addUser(UserRequestDTO user) {
        return this.userMapper.toDTO(this.userRepository.save(this.userMapper.toEntity(user)));
    }

}
