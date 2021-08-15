package com.offer.test.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.Period;

import javax.persistence.*;
import javax.validation.constraints.*;


@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue
    private long id;

    @NotEmpty(message = "His username cannot be empty.")
    @Length(min = 5, max = 20, message = "The length of his username must be between 5 and 20 characters")
    private String username;

    @NotNull(message = "His date of birth cannot be empty")
    private LocalDate dateOfBirth;

    @NotEmpty(message = "His country of residence cannot be empty")
    private String country;

    @Nullable
    private String gender;

    @Nullable
    private String phoneNumber;

    public User() {}

    public User(String username, LocalDate dateOfBirth, String country, @Nullable String gender, @Nullable String phoneNumber) {
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
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

    @Nullable
    public String getGender() {
        return gender;
    }

    public void setGender(@Nullable String gender) {
        this.gender = gender;
    }

    @Nullable
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@Nullable String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @AssertTrue(message = "He must live in this country to be registered")
    public boolean isResident() {
        return this.country.toLowerCase().equals("france");
    }

    @AssertTrue(message = "He must have the required age to be registered")
    public boolean isAdult() {
        int age = Period.between(this.dateOfBirth, LocalDate.now()).getYears();
        return age >= 18;
    }
}
