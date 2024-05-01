package io.clearsolutions.nikita.malov.annotation;

import io.clearsolutions.nikita.malov.validator.AdultValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AdultValidator.class)
public @interface Adult {
    /**
     * Returns the message to be used when the validation fails.
     *
     * @return The validation failure message.
     */
    String message() default "User must be an adult";

    /**
     * Returns the array of validation groups this constraint belongs to.
     *
     * @return An array of validation groups.
     */
    Class<?>[] groups() default {};

    /**
     * Returns the array of payload classes associated with the constraint.
     *
     * @return An array of payload classes.
     */
    Class<? extends Payload>[] payload() default {};
}