package io.clearsolutions.nikita.malov.dto;

import io.clearsolutions.nikita.malov.annotation.Adult;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserRequestDto {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 1, max = 50)
    private String firstName;
    @NotBlank
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
