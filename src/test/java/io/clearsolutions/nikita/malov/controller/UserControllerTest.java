package io.clearsolutions.nikita.malov.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.fge.jsonpatch.JsonPatch;
import io.clearsolutions.nikita.malov.dto.UserRequestDto;
import io.clearsolutions.nikita.malov.dto.UserResponseDto;
import io.clearsolutions.nikita.malov.exception.CustomExceptionHandler;
import io.clearsolutions.nikita.malov.service.UserService;
import java.time.LocalDate;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    private static final String userLink = "/api/v1/user";
    private MockMvc mockMvc;
    private final UserRequestDto userRequestDto = new UserRequestDto(
        "example@example.com",
        "John",
        "Doe",
        LocalDate.of(1990, 5, 15),
        "123 Main St, City, Country",
        "1234567890"
    );
    private final UserResponseDto userResponseDto = new UserResponseDto(1L, "example@example.com",
        "John",
        "Doe",
        LocalDate.of(1990, 5, 15),
        "123 Main St, City, Country",
        "1234567890");
    private final String request = """
        {
                "email":"example@example.com",
                "firstName": "John",
                "lastName": "Doe",
                "birthDate": "1990-05-15",
                "address": "123 Main St, City, Country",
                "phoneNumber": "1234567890"
        }
        """;
    private final String patchRequest = """
        [
        {
            "op": "replace", 
            "path": "/firstName",
            "value": "TestFirstName"
        },
        {
            "op": "replace", 
            "path": "/lastName",
            "value": "TestLastName"
        }
        ]
        """;
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private ErrorAttributes errorAttributes = new DefaultErrorAttributes();

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders
            .standaloneSetup(userController)
            .setControllerAdvice(new CustomExceptionHandler(errorAttributes))
            .build();
    }

    @Test
    void getAllUsersInDateRangeTest() throws Exception {
        var reponseList = List.of(userResponseDto);

        LocalDate from = LocalDate.of(2000, 12, 12);
        LocalDate to = LocalDate.of(2001, 12, 12);

        when(userService.getAllUsersInDateRange(from, to)).thenReturn(reponseList);

        mockMvc.perform(get(userLink)
                .param("from", from.toString())
                .param("to", to.toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].email").value(userResponseDto.getEmail()));

        verify(userService).getAllUsersInDateRange(from, to);
    }

    @Test
    void deleteUserTest() throws Exception {

        mockMvc.perform(delete(userLink + "/{id}", 1L))
            .andExpect(status().isNoContent());

        verify(userService).deleteById(1L);
    }

    @Test
    void updateUserTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        when(userService.updateUser(1L, userRequestDto)).thenReturn(userResponseDto);

        var result = mockMvc.perform(put(userLink + "/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse()
            .getContentAsString();

        UserResponseDto response = objectMapper.readValue(result, UserResponseDto.class);
        assertEquals(response, userResponseDto);

        verify(userService).updateUser(1L, userRequestDto);
    }

    @Test
    void createUserTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        when(userService.createUser(userRequestDto)).thenReturn(userResponseDto);

        String result = mockMvc.perform(post(userLink)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse()
            .getContentAsString();
        UserResponseDto response = objectMapper.readValue(result, UserResponseDto.class);
        assertEquals(response, userResponseDto);

        verify(userService).createUser(userRequestDto);
    }

    @Test
    void  patchUserTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        JsonPatch patch = objectMapper.readValue(patchRequest.getBytes(), JsonPatch.class);
        ArgumentMatcher<JsonPatch> argumentMatcher = argument -> argument.toString().equals(patch.toString());

        when(userService.patchUser(argThat(argumentMatcher), eq(1L))).thenReturn(userResponseDto);

        String result = mockMvc.perform(patch(userLink + "/{id}", 1L)
                .contentType("application/json-patch+json")
                .content(patchRequest))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse()
            .getContentAsString();
        UserResponseDto response = objectMapper.readValue(result, UserResponseDto.class);
        assertEquals(response, userResponseDto);

        verify(userService).patchUser(argThat(argumentMatcher), eq(1L));
    }
}
