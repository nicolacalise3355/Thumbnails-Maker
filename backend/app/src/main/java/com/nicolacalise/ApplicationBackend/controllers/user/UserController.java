package com.nicolacalise.ApplicationBackend.controllers.user;

import com.nicolacalise.ApplicationBackend.exceptions.entities.UserException;
import com.nicolacalise.ApplicationBackend.models.dtos.request.UserRequestDTO;
import com.nicolacalise.ApplicationBackend.models.dtos.response.UserResponseDTO;
import com.nicolacalise.ApplicationBackend.models.http.HttpResponseInvalid;
import com.nicolacalise.ApplicationBackend.models.http.HttpResponseValid;
import com.nicolacalise.ApplicationBackend.services.entityServices.UserService;
import com.nicolacalise.ApplicationBackend.staticvalues.Codes;
import com.nicolacalise.ApplicationBackend.staticvalues.GeneralMessages;
import com.nicolacalise.ApplicationBackend.staticvalues.entitiesMessages.UserMessages;
import com.nicolacalise.ApplicationBackend.validators.UserValidator;
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
            UserResponseDTO newUser = this.userService.getUserById(id);
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
    public ResponseEntity<?> createUser(@RequestBody UserRequestDTO user) {
        if(!this.userValidator.isUserValid(user)) return ResponseEntity.badRequest().body(new HttpResponseInvalid(Codes.ERROR_CODE, UserMessages.USER_NOT_VALID));
        return ResponseEntity.ok(new HttpResponseValid(Codes.CREATION_CODE, this.userService.addUser(user)));
    }


}
