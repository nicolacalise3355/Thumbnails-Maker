package com.calise.tb_backend.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class StatusController {

    public StatusController(){}

    @GetMapping
    public ResponseEntity<?> getStatus(){
        return ResponseEntity.ok().body(true);
    }
}
