package com.offer.test.controller;

import com.offer.test.model.User;
import com.offer.test.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserControllerTest {
    @Autowired
    UserController userController;

    @MockBean
    UserService userService;


    @Test
    void shouldReturnHTTP200_Index_OneUser() {
        when(userService.getAllUsers()).thenReturn(
                List.of(new User(1, "user1", LocalDate.of(1999, 8, 29), "France", null, null))
        );

        ResponseEntity<List<User>> response = userController.index();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void shouldReturnHTTP200_Index_ThreeUsers() {
        when(userService.getAllUsers()).thenReturn(
                List.of(
                        new User(1, "user1", LocalDate.of(1999, 8, 29), "France", null, null),
                        new User(2, "user2", LocalDate.of(2000, 5, 22), "France", null, null),
                        new User(3, "user3", LocalDate.of(2001, 2, 4), "France", null, null)
                )
        );

        ResponseEntity<List<User>> response = userController.index();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().size());
    }

    @Test
    void shouldReturnHTTP200_Index_NoUser() {
        when(userService.getAllUsers()).thenReturn(List.of());

        ResponseEntity<List<User>> response = userController.index();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    void shouldReturnHTTP404_Get_NoUser() {
        when(userService.getUserWithId(1)).thenReturn(Optional.empty());

        ResponseEntity<?> response = userController.get(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(String.class, response.getBody().getClass());
    }

    @Test
    void shouldReturnHTTP200Error_Get_OneUser() {
        when(userService.getUserWithId(1)).thenReturn(
                Optional.of(new User(1, "user1", LocalDate.of(1999, 8, 29), "France", null, null))
        );

        ResponseEntity<?> response = userController.get(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(User.class, response.getBody().getClass());
    }

    @Test
    void shouldReturnHTTP200_Create_UserOk() {
        when(userService.createUser("user1", 29, 8, 1999, "France", null, null)).thenReturn(Optional.empty());

        ResponseEntity<?> response = userController.create("user1", 29, 8, 1999, "France", null, null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldReturnHTTP422_Create_UserNotOk() {
        when(userService.createUser("user1", 29, 8, 1999, "UK", null, null)).thenReturn(
                Optional.of("The user cannot be registered.")
        );

        ResponseEntity<?> response = userController.create("user1", 29, 8, 1999, "UK", null, null);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    }

    @Test
    void shouldReturnHTTP500_Create_Throws() {
        when(userService.createUser("user1", 29, 8, 1999, "France", null, null)).thenThrow(new RuntimeException());

        ResponseEntity<?> response = userController.create("user1", 29, 8, 1999, "France", null, null);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}