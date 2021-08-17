package com.offer.test.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class ResidentValidator implements ConstraintValidator<ResidentConstraint, String> {
    private String localCountry;

    @Override
    public void initialize(ResidentConstraint residentConstraint) {
        this.localCountry = residentConstraint.localCountry();
    }

    @Override
    public boolean isValid(String country, ConstraintValidatorContext cxt) {
        if(Objects.isNull(country)) return true; // to ignore this validation if there is no specified country
        return country.equalsIgnoreCase(this.localCountry);
    }
}
