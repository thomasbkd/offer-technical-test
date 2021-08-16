package com.offer.test.model;

import com.offer.test.validator.AdultConstraint;
import com.offer.test.validator.ResidentConstraint;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue
    private long id;

    @NotEmpty(message = "His username cannot be empty")
    @Length(min = 5, max = 20, message = "The length of his username must be between 5 and 20 characters")
    private String username;

    @NotNull(message = "His date of birth cannot be empty")
    @AdultConstraint(message = "He must have the required age to be registered")
    private LocalDate dateOfBirth;

    @NotEmpty(message = "His country of residence cannot be empty")
    @ResidentConstraint(message = "He must live in this country to be registered")
    private String country;

    @Nullable
    private String gender;

    @Nullable
    private String phoneNumber;
}
