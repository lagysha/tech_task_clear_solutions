package io.clearsolutions.nikita.malov.controller;

import com.github.fge.jsonpatch.JsonPatch;
import io.clearsolutions.nikita.malov.annotation.ValidDateRange;
import io.clearsolutions.nikita.malov.dto.DateRangeDto;
import io.clearsolutions.nikita.malov.dto.UserRequestDto;
import io.clearsolutions.nikita.malov.dto.UserResponseDto;
import io.clearsolutions.nikita.malov.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    /**
     * Creates a new user with the provided user data.
     *
     * @param userRequestDto The user data for the new user.
     * @return ResponseEntity containing the created user's data and HTTP status CREATED.
     */
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(userService.createUser(userRequestDto));
    }

    /**
     * Updates some fields of an existing user identified by the given ID using JSON Patch.
     *
     * @param patch The JSON Patch object containing the fields to be updated.
     * @param id    The ID of the user to be updated.
     * @return ResponseEntity containing the updated user's data and HTTP status OK.
     */
    @PatchMapping(value = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<UserResponseDto> patchUser(
        @RequestBody JsonPatch patch,
        @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(userService.patchUser(patch, id));
    }

    /**
     * Updates all fields of an existing user identified by the given ID.
     *
     * @param userRequestDto The new user data containing all fields.
     * @param id             The ID of the user to be updated.
     * @return ResponseEntity containing the updated user's data and HTTP status OK.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@Valid @RequestBody UserRequestDto userRequestDto,
                                                      @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(userService.updateUser(id,userRequestDto));
    }

    /**
     * Deletes the user with the given ID.
     *
     * @param id The ID of the user to be deleted.
     * @return ResponseEntity with HTTP status NO_CONTENT if successful, otherwise appropriate error status.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .build();
    }

    /**
     * Retrieves all users within the specified date range.
     *
     * @param dateRangeDto The date range specifying the period to retrieve users for.
     * @return ResponseEntity containing a list of users within the specified date range and HTTP status OK.
     */
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsersInDateRange(@Valid @ValidDateRange DateRangeDto dateRangeDto) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(userService.getAllUsersInDateRange(dateRangeDto.getFrom(), dateRangeDto.getTo()));
    }
}
