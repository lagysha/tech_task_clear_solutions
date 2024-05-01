package io.clearsolutions.nikita.malov.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DateRangeDto {
    @NotNull
    private LocalDate from;
    @NotNull
    private LocalDate to;
}
