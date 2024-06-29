package com.calise.tb_backend.controllers.entities;

import com.calise.tb_backend.exceptions.entities.UserException;
import com.calise.tb_backend.models.dtos.request.UserRequestDto;
import com.calise.tb_backend.models.dtos.response.UserResponseDto;
import com.calise.tb_backend.models.http.HttpResponseInvalid;
import com.calise.tb_backend.models.http.HttpResponseValid;
import com.calise.tb_backend.services.entities.UserService;
import com.calise.tb_backend.staticdata.Codes;
import com.calise.tb_backend.staticdata.messages.GeneralMessages;
import com.calise.tb_backend.staticdata.messages.entities.UserMessages;
import com.calise.tb_backend.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private UserValidator userValidator;
    private UserService userService;

    @Autowired
    public UserController(UserValidator userValidator, UserService userService) {
        this.userValidator = userValidator;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") long id) {
        if(id < 0) return ResponseEntity.badRequest().body(new HttpResponseInvalid(Codes.ERROR_CODE, GeneralMessages.ID_NOT_VALID));
        try{
            UserResponseDto newUser = this.userService.getUserById(id);
            return ResponseEntity.ok(new HttpResponseValid(Codes.OK_CODE, newUser));
        }catch(UserException e){
            return ResponseEntity.badRequest().body(new HttpResponseInvalid(Codes.ERROR_CODE, e.getMessage()));
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(new HttpResponseValid(Codes.OK_CODE, this.userService.getAllUsers()));
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRequestDto user) {
        if(!this.userValidator.isUserValid(user)) return ResponseEntity.badRequest().body(new HttpResponseInvalid(Codes.ERROR_CODE, UserMessages.USER_NOT_VALID));
        return ResponseEntity.ok(new HttpResponseValid(Codes.CREATION_CODE, this.userService.addUser(user)));
    }

}
