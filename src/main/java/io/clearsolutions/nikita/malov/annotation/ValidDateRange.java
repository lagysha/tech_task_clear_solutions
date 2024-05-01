package io.clearsolutions.nikita.malov.annotation;

import io.clearsolutions.nikita.malov.validator.DateRangeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = DateRangeValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateRange {
    /**
     * Returns the message to be used when the validation of the date range fails.
     *
     * @return The validation failure message.
     */
    String message() default "Invalid date range";

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
