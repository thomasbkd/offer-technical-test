package com.offer.test;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.Period;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;


@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue
    private long id;

    @NonNull
    private String username;

    @NonNull
    private LocalDate dateOfBirth;

    @NonNull
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

    public boolean isResident() {
        return this.country.toLowerCase().equals("france");
    }

    public boolean isAdult() {
        int age = Period.between(this.dateOfBirth, LocalDate.now()).getYears();
        return age >= 18;
    }
}
