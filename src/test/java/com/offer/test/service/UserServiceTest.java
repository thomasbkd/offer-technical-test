package com.offer.test.service;

import com.offer.test.jpa.UserRepository;
import com.offer.test.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {
    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;


    @Test
    void should_GetAllUsers() {
        when(userRepository.findAll()).thenReturn(
                List.of(
                        new User(1, "user1", LocalDate.of(1999, 8, 29), "France", null, null),
                        new User(2, "user2", LocalDate.of(2000, 5, 22), "France", null, null),
                        new User(3, "user3", LocalDate.of(2001, 2, 4), "France", null, null)
                )
        );

        List<User> allUsers = userService.getAllUsers();
        assertEquals(3, allUsers.size());
    }

    @Test
    void should_GetUserWithId() {
        when(userRepository.findById(1L)).thenReturn(
                Optional.of(
                        new User(1, "user1", LocalDate.of(1999, 8, 29), "France", null, null)
                )
        );

        assertTrue(userService.getUserWithId(1).isPresent());
    }

    @Test
    void should_GetUserWithId_ButNoUserFound() {
        when(userRepository.findById(1L)).thenReturn(
                Optional.empty()
        );

        assertFalse(userService.getUserWithId(1).isPresent());
    }

    @Test
    void shouldReturnTrue_UsernameAlreadyTaken_TwoUsersHaveThisUsername() {
        when(userRepository.findAllByUsername("user1"))
                .thenReturn(
                        List.of(
                                new User(1, "user1", LocalDate.of(1999, 8, 29), "France", null, null),
                                new User(2, "user1", LocalDate.of(2000, 5, 22), "France", null, null)
                        )
                );

        assertTrue(userService.isUsernameAlreadyTaken("user1"));
    }

    @Test
    void shouldReturnTrue_UsernameAlreadyTaken_OneUserHasThisUsername() {
        when(userRepository.findAllByUsername("user1")).thenReturn(
                List.of(new User(1, "user1", LocalDate.of(1999, 8, 29), "France", null, null))
        );

        assertTrue(userService.isUsernameAlreadyTaken("user1"));
    }

    @Test
    void shouldReturnFalse_UsernameAlreadyTaken_NoUserHasThisUsername() {
        when(userRepository.findAllByUsername("user1")).thenReturn(List.of());
        assertFalse(userService.isUsernameAlreadyTaken("user1"));
    }

    @Test
    void shouldReturnAnEmptyOptional_CreateUser_RequiredFields() {
        Optional<String> errorMessage = userService.createUser("user1", 29, 8, 1999, "France", null, null);
        assertTrue(errorMessage.isEmpty());
    }

    @Test
    void shouldReturnAnEmptyOptional_CreateUser_AllFields() {
        Optional<String> errorMessage = userService.createUser("user1", 29, 8, 1999, "France", "M", "06.30.39.51.06");
        assertTrue(errorMessage.isEmpty());
    }

    @Test
    void shouldReturnAnError_CreateUser_NoParameters() {
        Optional<String> errorMessage = userService.createUser(null, null, null, null, null, null, null);
        assertTrue(errorMessage.isPresent());
    }

    @Test
    void shouldReturnAnError_CreateUser_IncorrectDateFormat() {
        Optional<String> errorMessage = userService.createUser("user1", 29, 2, 1999, "France", "M", "06.30.39.51.06");
        assertTrue(errorMessage.isPresent());
    }

    @Test
    void shouldReturnAnError_CreateUser_TooYoung() {
        Optional<String> errorMessage = userService.createUser("user1", 29, 12, 2003, "France", "M", "06.30.39.51.06");
        assertTrue(errorMessage.isPresent());
    }

    @Test
    void shouldReturnAnError_CreateUser_NotResident() {
        Optional<String> errorMessage = userService.createUser("user1", 29, 8, 1999, "Germany", "M", "06.30.39.51.06");
        assertTrue(errorMessage.isPresent());
    }

    @Test
    void shouldReturnAnError_CreateUser_TooShortUsername() {
        Optional<String> errorMessage = userService.createUser("user", 29, 8, 1999, "France", "M", "06.30.39.51.06");
        assertTrue(errorMessage.isPresent());
    }

    @Test
    void shouldReturnAnError_CreateUser_EmptyRequiredField() {
        Optional<String> errorMessage = userService.createUser("user1", 29, 8, 1999, null, "M", "06.30.39.51.06");
        assertTrue(errorMessage.isPresent());
    }

    @Test
    void shouldReturnAnError_CreateUser_UsernameAlreadyTaken() {
        when(userRepository.findAllByUsername("user1")).thenReturn(
                List.of(new User(1, "user1", LocalDate.of(1999, 8, 29), "France", null, null))
        );

        Optional<String> errorMessage = userService.createUser("user1", 29, 8, 1999, null, "M", "06.30.39.51.06");
        assertTrue(errorMessage.isPresent());
    }
}