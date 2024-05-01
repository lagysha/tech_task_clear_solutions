package io.clearsolutions.nikita.malov.dto;

import io.clearsolutions.nikita.malov.annotation.Adult;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserPatchRequestDto {
    @Email
    private String email;
    @Size(min = 1, max = 50)
    private String firstName;
    @Size(min = 1, max = 50)
    private String lastName;
    @Past
    @Adult
    private LocalDate birthDate;
    @Size(max = 100)
    private String address;
    @Pattern(regexp = "\\d{10}")
    private String phoneNumber;
}
