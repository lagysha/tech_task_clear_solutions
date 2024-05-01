package io.clearsolutions.nikita.malov.validator;

import io.clearsolutions.nikita.malov.annotation.Adult;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AdultValidator implements ConstraintValidator<Adult, LocalDate> {
    /**
     * The minimum age allowed for a user.
     */
    @Value("${user-min-age}")
    private int minAge;

    /**
     * Validates if the given date represents an age above the minimum age.
     *
     * @param value   The date of birth to be validated.
     * @param context The validation context.
     * @return {@code true} if the age is above or equal to the minimum age, {@code false} otherwise.
     */
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return Period.between(value, LocalDate.now()).getYears() >= minAge;
    }
}