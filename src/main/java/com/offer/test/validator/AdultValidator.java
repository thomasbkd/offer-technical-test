package com.offer.test.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class AdultValidator implements ConstraintValidator<AdultConstraint, LocalDate> {
    private int requiredAge;

    @Override
    public void initialize(AdultConstraint adultConstraint) {
        this.requiredAge = adultConstraint.requiredAge();
    }

    @Override
    public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext cxt) {
        if(Objects.isNull(dateOfBirth)) return true;
        int age = Period.between(dateOfBirth, LocalDate.now()).getYears();
        return age >= this.requiredAge;
    }
}
