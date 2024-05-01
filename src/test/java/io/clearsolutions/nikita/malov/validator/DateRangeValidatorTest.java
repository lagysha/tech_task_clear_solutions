package io.clearsolutions.nikita.malov.validator;

import io.clearsolutions.nikita.malov.dto.DateRangeDto;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class DateRangeValidatorTest {

    private final DateRangeValidator validator = new DateRangeValidator();
    private final ConstraintValidatorContext context = null;

    @Test
    public void testIsValidNullDateRangeReturnsTrue() {
        DateRangeDto dateRange = null;

        assertTrue(validator.isValid(dateRange, context));
    }

    @Test
    public void testIsValidValidDateRangeReturnsTrue() {
        DateRangeDto dateRange = new DateRangeDto();
        dateRange.setFrom(LocalDate.of(2022, 1, 1));
        dateRange.setTo(LocalDate.of(2022, 12, 31));

        assertTrue(validator.isValid(dateRange, context));
    }

    @Test
    public void testIsValidInvalidDateRangeReturnsFalse() {
        DateRangeDto dateRange = new DateRangeDto();
        dateRange.setFrom(LocalDate.of(2022, 12, 31));
        dateRange.setTo(LocalDate.of(2022, 1, 1));

        assertFalse(validator.isValid(dateRange, context));
    }
}
