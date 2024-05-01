package io.clearsolutions.nikita.malov.validator;

import io.clearsolutions.nikita.malov.annotation.ValidDateRange;
import io.clearsolutions.nikita.malov.dto.DateRangeDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, DateRangeDto> {
    /**
     * Validates if the provided date range is valid, i.e., if the start date is before the end date.
     *
     * @param dateRange The date range to be validated.
     * @param context   The validation context.
     * @return {@code true} if the date range is valid, {@code false} otherwise. Returns {@code true} if the provided date range is {@code null}.
     */
    @Override
    public boolean isValid(DateRangeDto dateRange, ConstraintValidatorContext context) {
        if (dateRange == null) {
            return true;
        }
        return dateRange.getFrom().isBefore(dateRange.getTo());
    }
}
