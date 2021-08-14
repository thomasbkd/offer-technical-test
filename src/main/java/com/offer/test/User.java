package com.offer.test;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import javax.persistence.*;

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue
    private long id;

    private String username;
    private LocalDate dateOfBirth;
    private String country;


    public User() {}

    public User(String username, LocalDate dateOfBirth, String country) {
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public boolean isResident() {
        return this.country.toLowerCase().equals("france");
    }

    public boolean isAdult() {
        int age = Period.between(this.getDateOfBirth(), LocalDate.now()).getYears();
        return age >= 18;
    }
}
