package com.offer.test.service;

import com.offer.test.jpa.UserRepository;
import com.offer.test.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;


/**
 * This class serves as the service layer of the User class. It contains the methods called in the associated REST
 * controller, and uses the associated JPA repository to access the persistence layer.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Gets all the users present in the User table in the data source, and returns an Iterable of them.
     * @return an Iterable of all users.
     * @see Iterable
     */
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Searches the user corresponding to the given id in the data source and returns it in an Optional.
     * @param id primary key of the desired user.
     * @return an Optional containing (or not) the desired user.
     * @see Optional
     */
    public Optional<User> getUserWithId(long id) {
        return userRepository.findById(id);
    }

    /**
     * Checks if the given username is already taken by one or more user in the data source.
     * @param username the username to check.
     * @return <code>true</code> if the username is already taken, <code>false</code> otherwise.
     * @see Optional
     */
    public Boolean isUsernameAlreadyTaken(String username) {
        return !userRepository.findAllByUsername(username).isEmpty();
    }

    /**
     * Return an Optional that can contain the reasons why the user could not be created. <br>
     * First, the method checks if data was sent, else returns a message. <br>
     * Then, it tries to create a LocalDate with the three corresponding given parameters. If an exception is thrown,
     * the method returns a message. <br>
     * The method next verifies if the given username is already taken by another user in the data source, and return
     * a message if that is the case. <br>
     * Once passed all these steps, a user bean is build and is submitted to validation. Each constraint is checked,
     * and if there is some violation, a digest of the validators' messages is returned. <br>
     * Finally, the user bean is saved into the data source, and an empty Optional is returned, meaning that everything
     * went well.
     *
     * @param username the username of the user to be created.
     * @param dayOfBirth the day of the date of birth of the user to be created.
     * @param monthOfBirth the month of the date of birth of the user to be created.
     * @param yearOfBirth the year of birth of the user to be created.
     * @param country the country of residence of the user to be created.
     * @param gender the gender of the user to be created.
     * @param phoneNumber the phone number of the user to be created.
     * @return an Optional that can contain the reasons why the user could not be created.
     * @see User
     * @see Optional
     * @see LocalDate
     * @see Validation
     */
    public Optional<String> createUser(
            String username,
            Integer dayOfBirth,
            Integer monthOfBirth,
            Integer yearOfBirth,
            String country,
            String gender,
            String phoneNumber) {

        // Check if data
        if(Objects.isNull(username) &&
                Objects.isNull(dayOfBirth) &&
                Objects.isNull(monthOfBirth) &&
                Objects.isNull(yearOfBirth) &&
                Objects.isNull(country) &&
                Objects.isNull(gender) &&
                Objects.isNull(phoneNumber)) {
            return Optional.of("Unable to create the user, no data was given.");
        }


        // Date of birth's creation
        LocalDate dateOfBirth;
        try { // try to create LocalDate with given parameters
            dateOfBirth = LocalDate.of(yearOfBirth, monthOfBirth, dayOfBirth);
        }
        catch(Exception e) { // if incorrect date format
            return Optional.of("The given date of birth is not correct.");
        }


        // Check if the given username is available
        if(this.isUsernameAlreadyTaken(username)) {
            return Optional.of("The given username is already taken.");
        }


        // Building the user bean with the given parameters
        User user = User
                .builder()
                .username(username)
                .dateOfBirth(dateOfBirth)
                .country(country)
                .gender(gender)
                .phoneNumber(phoneNumber)
                .build();


        // Check the constraints
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        // If constraint violations
        if(!constraintViolations.isEmpty()) {
            // Construct the error message
            String errorMessage = "The user cannot be registered for the following reasons:";
            for(ConstraintViolation<User> violation : constraintViolations) {
                errorMessage = errorMessage.concat("\n\t - ").concat(violation.getMessage());
            }

            return Optional.of(errorMessage);
        }

        // Else save user into the data source
        else {
            userRepository.save(user);
            return Optional.empty();
        }
    }
}
