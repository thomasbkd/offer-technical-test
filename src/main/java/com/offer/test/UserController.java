package com.offer.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;


@RestController
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(path = "/api")
    public Iterable<User> index() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/api/{id}")
    public User get(@PathVariable("id") long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @PostMapping(path = "/api")
    public ResponseEntity<String> create(String username, int dayOfBirth, int monthOfBirth, int yearOfBirth, String country, String gender, String phoneNumber) {
        User user;
        LocalDate dateOfBirth;

        try {
            dateOfBirth = LocalDate.of(yearOfBirth, monthOfBirth, dayOfBirth);
        }
        catch(Exception e) {
            return new ResponseEntity<String>(
                    "The given date of birth is not correct.",
                    HttpStatus.UNPROCESSABLE_ENTITY
            );
        }

        try {
            user = new User(
                    username,
                    dateOfBirth,
                    country,
                    gender,
                    phoneNumber
            );

            if(!user.isAdult()) {
                return new ResponseEntity<String>(
                        "The user cannot be registered because he does not have the required age.",
                        HttpStatus.UNPROCESSABLE_ENTITY
                );
            }

            if(!user.isResident()) {
                return new ResponseEntity<String>(
                        "The user cannot be registered because he does not live in this country.",
                        HttpStatus.UNPROCESSABLE_ENTITY
                );
            }

            userRepository.save(user);

            return new ResponseEntity<String>(
                    "User registered successfully !",
                    HttpStatus.OK
            );
        }

        catch(Exception e) {
            return new ResponseEntity<String>(
                    "Error creating User object from query data.",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}