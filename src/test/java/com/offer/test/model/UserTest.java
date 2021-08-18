package com.offer.test.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    User user;

    @BeforeEach
    void setUp() {
        user = new User(1, "user1", LocalDate.of(1999, 8, 29), "France", "M", "06.30.39.51.06");
    }


    @Test
    void getId() {
        assertEquals(1, user.getId());
    }

    @Test
    void getUsername() {
        assertEquals("user1", user.getUsername());
    }

    @Test
    void getDateOfBirth() {
        assertEquals(LocalDate.of(1999, 8, 29), user.getDateOfBirth());
    }

    @Test
    void getCountry() {
        assertEquals("France", user.getCountry());
    }

    @Test
    void getGender() {
        assertEquals("M", user.getGender());
    }

    @Test
    void getPhoneNumber() {
        assertEquals("06.30.39.51.06", user.getPhoneNumber());
    }

    @Test
    void setId() {
        long newValue = 2;
        user.setId(newValue);
        assertEquals(newValue, user.getId());
    }

    @Test
    void setUsername() {
        String newValue = "user2";
        user.setUsername(newValue);
        assertEquals(newValue, user.getUsername());
    }

    @Test
    void setDateOfBirth() {
        LocalDate newValue = LocalDate.of(2000, 5, 22);
        user.setDateOfBirth(newValue);
        assertEquals(newValue, user.getDateOfBirth());
    }

    @Test
    void setCountry() {
        String newValue = "Italy";
        user.setCountry(newValue);
        assertEquals(newValue, user.getCountry());
    }

    @Test
    void setGender() {
        String newValue = "F";
        user.setGender(newValue);
        assertEquals(newValue, user.getGender());
    }

    @Test
    void setPhoneNumber() {
        String newValue = "06.12.34.56.78";
        user.setPhoneNumber(newValue);
        assertEquals(newValue, user.getPhoneNumber());
    }

    @Test
    void builder() {
        User userBuilt = User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .dateOfBirth(user.getDateOfBirth())
                .country(user.getCountry())
                .gender(user.getGender())
                .phoneNumber(user.getPhoneNumber())
                .build();

        assertEquals(user.getId(), userBuilt.getId());
        assertEquals(user.getUsername(), userBuilt.getUsername());
        assertEquals(user.getDateOfBirth(), userBuilt.getDateOfBirth());
        assertEquals(user.getCountry(), userBuilt.getCountry());
        assertEquals(user.getGender(), userBuilt.getGender());
        assertEquals(user.getPhoneNumber(), userBuilt.getPhoneNumber());
    }
}