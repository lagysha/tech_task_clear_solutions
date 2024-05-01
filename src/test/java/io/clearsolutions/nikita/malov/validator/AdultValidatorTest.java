package io.clearsolutions.nikita.malov.validator;

import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdultValidatorTest {

    private final AdultValidator validator = new AdultValidator();
    private final ConstraintValidatorContext context = null;
    @BeforeEach
    void initialise() {
        ReflectionTestUtils.setField(validator,"minAge",18);
    }

    @Test
    public void testIsValidNullDateRangeReturnsTrue() {
        assertFalse(validator.isValid(null, context));
    }

    @Test
    public void testIsValidValidDateRangeReturnsTrue() {
        LocalDate localDate = LocalDate.of(1900,1,1);
        assertTrue(validator.isValid(localDate, context));
    }

    @Test
    public void testIsValidInvalidDateRangeReturnsFalse() {
        LocalDate localDate = LocalDate.now();
        assertFalse(validator.isValid(localDate, context));
    }
}
