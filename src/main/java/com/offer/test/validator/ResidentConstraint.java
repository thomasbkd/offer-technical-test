package com.offer.test.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ FIELD }) // applies to a field
@Retention(RUNTIME)
@Constraint(validatedBy = ResidentValidator.class)
public @interface ResidentConstraint {
    String localCountry() default "France"; // country of which the user must be resident
    String message() default "The user must be adult";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
