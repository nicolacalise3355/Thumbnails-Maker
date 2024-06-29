package com.calise.tb_backend.services.entities;

import com.calise.tb_backend.dao.UserDAO;
import com.calise.tb_backend.exceptions.entities.UserException;
import com.calise.tb_backend.mapper.UserMapper;
import com.calise.tb_backend.models.dtos.request.UserRequestDto;
import com.calise.tb_backend.models.dtos.response.UserResponseDto;
import com.calise.tb_backend.models.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.calise.tb_backend.staticdata.messages.entities.UserMessages;
import com.calise.tb_backend.staticdata.Codes;

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

    public UserResponseDto getUserById(long id) throws UserException {
        return this.userMapper.toDto(this.userRepository.findById(id).orElseThrow(() -> new UserException(UserMessages.USER_NOT_FOUND, Codes.ERROR_CODE)));
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public UserResponseDto addUser(UserRequestDto user) {
        return this.userMapper.toDto(this.userRepository.save(this.userMapper.toEntity(user)));
    }

}
