package com.offer.test.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = AdultValidator.class)
public @interface AdultConstraint {
    int requiredAge() default 18;
    String message() default "The user must be adult";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
