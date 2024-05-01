package io.clearsolutions.nikita.malov.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.validation.metadata.ConstraintDescriptor;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

@ExtendWith(MockitoExtension.class)
public class CustomExceptionHandlerTest {
    @Mock
    WebRequest webRequest;
    @Mock
    ErrorAttributes errorAttributes;
    Map<String, Object> objectMap;
    @Mock
    private BindingResult bindingResult;
    @InjectMocks
    CustomExceptionHandler customExceptionHandler;
    @BeforeEach
    void init() {
        objectMap = new HashMap<>();
        objectMap.put("path", "/ownSecurity/restorePassword");
        objectMap.put("message", "test");
        objectMap.put("timestamp", "2021-02-06T17:27:50.569+0000");
        objectMap.put("trace", "Internal Server Error");
    }

    @Test
    void handleNotFoundExceptionTest() {
        NotFoundException notFoundException = new NotFoundException("test");
        ExceptionResponse exceptionResponse = new ExceptionResponse(objectMap);
        when(errorAttributes.getErrorAttributes(eq(webRequest),
            any(ErrorAttributeOptions.class))).thenReturn(objectMap);
        assertEquals(ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse)
            ,customExceptionHandler.handleNotFoundException(notFoundException, webRequest));
    }

    @Test
    void handleMethodArgumentNotValidTest() {
        List<ObjectError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError("objectName", "fieldName1", "Error message 1"));
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(
            null, bindingResult);
        HashMap<String, String> map = new HashMap<>();
        map.put("fieldName1","Error message 1");

        when(bindingResult.getAllErrors()).thenReturn(fieldErrors);

        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map),
            customExceptionHandler.handleMethodArgumentNotValid(exception,null,null, webRequest));
    }

    @Test
    void handleCreationExceptionTest() {
        CreationException creationException = new CreationException("test");
        ExceptionResponse exceptionResponse = new ExceptionResponse(objectMap);
        when(errorAttributes.getErrorAttributes(eq(webRequest),
            any(ErrorAttributeOptions.class))).thenReturn(objectMap);
        assertEquals(customExceptionHandler.handleCreationException(creationException, webRequest),
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse));
    }

    @Test
    void handleConstraintViolationExceptionTest() {
        ConstraintViolation<String> violation = createConstraintViolation(
            "myField cannot be blank",
            "",
            "myField"
        );
        HashSet<ConstraintViolation<?>> constraintViolations = new HashSet<>();
        constraintViolations.add(violation);
        ConstraintViolationException constraintViolationException =
            new ConstraintViolationException(constraintViolations);
        ExceptionResponse exceptionResponse = new ExceptionResponse(objectMap);
        exceptionResponse.setMessage("myField cannot be blank");

        when(errorAttributes.getErrorAttributes(eq(webRequest),
            any(ErrorAttributeOptions.class))).thenReturn(objectMap);

        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse),
            customExceptionHandler.handleConstraintViolationException(constraintViolationException, webRequest));
    }

    @Test
    void handlePatchExceptionTest() {
        PatchException patchException = new PatchException("test");
        ExceptionResponse exceptionResponse = new ExceptionResponse(objectMap);
        when(errorAttributes.getErrorAttributes(eq(webRequest),
            any(ErrorAttributeOptions.class))).thenReturn(objectMap);
        assertEquals(customExceptionHandler.handlePatchException(patchException, webRequest),
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse));
    }

    private static ConstraintViolation<String> createConstraintViolation(
        String message, String invalidValue, String propertyPath) {

        return new ConstraintViolation<>() {
            @Override
            public String getMessage() {
                return message;
            }

            @Override
            public String getMessageTemplate() {
                return null;
            }

            @Override
            public String getRootBean() {
                return null;
            }

            @Override
            public Class<String> getRootBeanClass() {
                return null;
            }

            @Override
            public Object getLeafBean() {
                return null;
            }

            @Override
            public Path getPropertyPath() {
                return null;
            }

            @Override
            public Object getInvalidValue() {
                return invalidValue;
            }

            @Override
            public ConstraintDescriptor<?> getConstraintDescriptor() {
                return null;
            }

            @Override
            public Object[] getExecutableParameters() {
                return new Object[0];
            }

            @Override
            public Object getExecutableReturnValue() {
                return null;
            }

            @Override
            public <U> U unwrap(Class<U> aClass) {
                return null;
            }
        };
    }
}
