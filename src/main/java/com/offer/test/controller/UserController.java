package com.offer.test.controller;

import com.offer.test.model.User;
import com.offer.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * This class serves as a REST controller for the User class. It contains the routes and theirs respective methods to
 * <b>show all users</b>, <b>show the details of a particular user</b>, and finally <b>add a new user</b>.
 * It communicates with the associated service to get the required data.
 * @see UserService
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Displays the full list of the registered users.
     * @return an HTTP 200 response with the users list in its body.
     * @see ResponseEntity
     */
    @GetMapping(path = "/api")
    public ResponseEntity<?> index() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    /**
     * Displays the information of the desired user.
     * @param id the primary key of the desired user.
     * @return an HTTP 200 response with the user in its body if it is found, else an HTTP 404 response with an error
     * message in its body.
     * @see ResponseEntity
     */
    @GetMapping(path = "/api/{id}")
    public ResponseEntity<?> get(@PathVariable("id") long id) {
        Optional<User> optionalUser = userService.getUserWithId(id);
        return optionalUser.isPresent() ?
                ResponseEntity.ok().body(optionalUser.get()) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("The requested user does not exist.");
    }

    /**
     * Tries to create a new user from the given parameters. Calls the <code>createUser</code> of the corresponding
     * service, and return :
     * <li>an <b>HTTP 200</b> response with a <b>success message</b> in its body if the service's method do not returned anything</li>
     * <li>an <b>HTTP 412</b> response with an <b>error message</b> in its body if the service's method returned an error message</li>
     * <li>an <b>HTTP 500</b> response with an <b>error message</b> in its body if an exception is thrown by the service's method</li>
     *
     * @param username the username of the user to be created.
     * @param dayOfBirth the day of the date of birth of the user to be created.
     * @param monthOfBirth the month of the date of birth of the user to be created.
     * @param yearOfBirth the year of birth of the user to be created.
     * @param country the country of residence of the user to be created.
     * @param gender the gender of the user to be created.
     * @param phoneNumber the phone number of the user to be created.
     * @return a custom HTTP status and message in its body, according to the result of the service's method.
     * @see ResponseEntity
     * @see UserService
     */
    @PostMapping(path = "/api")
    public ResponseEntity<?> create(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Integer dayOfBirth,
            @RequestParam(required = false) Integer monthOfBirth,
            @RequestParam(required = false) Integer yearOfBirth,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String phoneNumber) {

        // Try to create the user
        try {
            Optional<String> optErrorMessage = userService.createUser(
                    username, dayOfBirth, monthOfBirth, yearOfBirth, country, gender, phoneNumber
            );

            return optErrorMessage.isEmpty() ?
                    ResponseEntity.ok().body("User registered successfully !") : // if no error returned
                    ResponseEntity.unprocessableEntity().body(optErrorMessage.get()); // if some errors returned
        }

        // If service's method throws an exception
        catch(Exception e) {
            e.printStackTrace(); // log stacktrace into the server's console
            return ResponseEntity.internalServerError().body("Error creating User object from query data.");
        }
    }
}