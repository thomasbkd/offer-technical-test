package com.offer.test;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class UserController {

    @GetMapping(path = "/api/")
    public List<User> index() {
        User u1 = new User(
            1,
            "thomasbkd",
            LocalDate.of(1999, 8, 29),
            "France");

        User u2 = new User(
            2,
            "touffe22",
            LocalDate.of(2000, 5, 22),
            "France");

        return List.of(u1, u2);
    }

    @GetMapping(path = "/api/{id}")
    public User get(@PathVariable("id") long id) {
        return new User(
            id,
            "thomasbkd",
            LocalDate.of(1999, 8, 29),
            "France"
        );
    }

    @PostMapping(path = "/api/")
    public void create(@RequestBody RequestBody body) {
        // TODO
    }
}