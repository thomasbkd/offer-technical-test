package com.offer.test.controller;

import com.offer.test.model.User;
import com.offer.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Year;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/api")
    public ResponseEntity<?> index() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping(path = "/api/{id}")
    public ResponseEntity<?> get(@PathVariable("id") long id) {
        Optional<User> optionalUser = userService.getUserWithId(id);
        return optionalUser.isPresent() ?
                ResponseEntity.ok().body(optionalUser.get()) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("The requested user does not exist.");
    }

    @PostMapping(path = "/api")
    public ResponseEntity<String> create(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Integer dayOfBirth,
            @RequestParam(required = false) Integer monthOfBirth,
            @RequestParam(required = false) Integer yearOfBirth,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String phoneNumber) {


        try {
            Optional<String> optErrorMessage = userService.createUser(
                    username, dayOfBirth, monthOfBirth, yearOfBirth, country, gender, phoneNumber
            );

            return optErrorMessage.isEmpty() ?
                    ResponseEntity.ok().body("User registered successfully !") :
                    ResponseEntity.unprocessableEntity().body(optErrorMessage.get());
        }

        catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error creating User object from query data.");
        }
    }
}