package com.offer.test.controller;

import com.offer.test.model.User;
import com.offer.test.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.*;
import java.time.LocalDate;
import java.util.Set;


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

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

            if(constraintViolations.size() == 0) {
                userRepository.save(user);
                return new ResponseEntity<String>(
                        "User registered successfully !",
                        HttpStatus.OK
                );
            }

            else {
                String errorMessage = new String("The user cannot be registered for the following reasons: \n");
                for(ConstraintViolation<User> violation : constraintViolations) {
                    errorMessage = errorMessage.concat("\t - ").concat(violation.getMessage().concat("\n"));
                }

                return new ResponseEntity<String>(
                        errorMessage,
                        HttpStatus.UNPROCESSABLE_ENTITY
                );
            }

        }

        catch(Exception e) {
            return new ResponseEntity<String>(
                    "Error creating User object from query data.",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}