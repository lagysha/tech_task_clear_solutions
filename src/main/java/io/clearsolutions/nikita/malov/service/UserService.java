package io.clearsolutions.nikita.malov.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import static io.clearsolutions.nikita.malov.constant.ExceptionMessage.USER_PATCH_EXCEPTION;
import io.clearsolutions.nikita.malov.dto.UserRequestDto;
import io.clearsolutions.nikita.malov.dto.UserResponseDto;
import io.clearsolutions.nikita.malov.dto.UserVO;
import io.clearsolutions.nikita.malov.exception.PatchException;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ObjectMapper objectMapper;

    /**
     * Partially updates a user's fields specified by the JSON Patch.
     *
     * @param userPatchRequestDto The JSON Patch containing the updates to be applied.
     * @param userId              The ID of the user to be patched.
     * @return The patched user response DTO.
     * @throws PatchException If there is an issue while applying the patch.
     */
    public UserResponseDto patchUser(JsonPatch userPatchRequestDto, Long userId) {
        //TODO: Get user by userId
        UserVO userVO =
            new UserVO(1L, "test@test.com", "FirstName1",
                "LastName1", LocalDate.of(2003, 3, 3), "testAddress",
                "1236667890");

        UserVO user = applyPatchToUser(userPatchRequestDto, userVO);
        //TODO: Save updated user
        return null;
    }

    /**
     * Retrieves all users within the specified date range.
     *
     * @param from The start date of the date range.
     * @param to   The end date of the date range.
     * @return A list of user response DTOs within the specified date range.
     */
    public List<UserResponseDto> getAllUsersInDateRange(LocalDate from, LocalDate to) {
        //TODO: Find all users in date range
        return null;
    }

    /**
     * Deletes a user from the database by their ID.
     *
     * @param id The ID of the user to be deleted.
     */
    public void deleteById(Long id) {
        //TODO: Delete user by id
    }

    /**
     * Updates a user's information.
     *
     * @param id             The ID of the user to be updated.
     * @param userRequestDto The updated user data.
     * @return The updated user response DTO.
     */
    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        //TODO: Save updated user
        return null;
    }

    /**
     * Creates a new user and saves them to the database.
     *
     * @param userRequestDto The data of the user to be created.
     * @return The created user response DTO.
     */
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        //TODO: Create and save user
        return null;
    }

    private UserVO applyPatchToUser(JsonPatch patch, UserVO targetUser) {
        try {
            return objectMapper.treeToValue(patch.apply(objectMapper.convertValue(targetUser, JsonNode.class)),
                UserVO.class);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new PatchException(USER_PATCH_EXCEPTION + e);
        }
    }
}
