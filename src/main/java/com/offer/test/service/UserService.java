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


    public Boolean isUsernameAlreadyChosen(String username) {
        return !userRepository.findAllByUsername(username).isEmpty();
    }

    public Optional<String> createUser(
            String username,
            Integer dayOfBirth,
            Integer monthOfBirth,
            Integer yearOfBirth,
            String country,
            String gender,
            String phoneNumber) {

        if(Objects.isNull(username) &&
                Objects.isNull(dayOfBirth) &&
                Objects.isNull(monthOfBirth) &&
                Objects.isNull(yearOfBirth) &&
                Objects.isNull(country) &&
                Objects.isNull(gender) &&
                Objects.isNull(phoneNumber)) {
            return Optional.of("Unable to create the user, no data was given.");
        }

        LocalDate dateOfBirth;

        try {
            dateOfBirth = LocalDate.of(yearOfBirth, monthOfBirth, dayOfBirth);
        }
        catch(Exception e) {
            return Optional.of("The given date of birth is not correct.");
        }

        if(this.isUsernameAlreadyChosen(username)) {
            return Optional.of("The chosen username already exists.");
        }

        User user = User
                .builder()
                .username(username)
                .dateOfBirth(dateOfBirth)
                .country(country)
                .gender(gender)
                .phoneNumber(phoneNumber)
                .build();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        if(constraintViolations.size() == 0) {
            userRepository.save(user);
            return Optional.empty();
        }

        else {
            String errorMessage = "The user cannot be registered for the following reasons:";
            for(ConstraintViolation<User> violation : constraintViolations) {
                errorMessage = errorMessage.concat("\n\t - ").concat(violation.getMessage());
            }

            return Optional.of(errorMessage);
        }
    }

}
