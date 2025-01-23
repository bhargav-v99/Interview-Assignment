package my.com.website.exception;

import my.com.website.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Define a global exception handler
@ControllerAdvice
public class GlobalExceptionalHandler extends ResponseEntityExceptionHandler {

    // Handle MethodArgumentNotValidException (validation errors)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        // Create a map to store validation errors
        Map<String, String> validationErrors = new HashMap<>();
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

        // Iterate through validation errors and add to the map
        validationErrorList.forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String validationMsg = error.getDefaultMessage();
            validationErrors.put(fieldName, validationMsg);
        });

        // Return a 400 Bad Request response with the validation errors
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    // Handle WebsiteAlreadyExistsException
    @ExceptionHandler(WebsiteAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistsException(WebsiteAlreadyExistsException exception,
                                                                                 WebRequest webRequest){
        // Create an ErrorResponseDto object with error details
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.CONFLICT,
                exception.getMessage(),
                LocalDateTime.now()
        );
        // Return a 409 Conflict response with the ErrorResponseDto object
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
    }

    // Handle RecordNotFoundException
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleRecordNotFoundException(RecordNotFoundException exception,
                                                                                 WebRequest webRequest){
        // Create an ErrorResponseDto object with error details
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now()
        );
        // Return a 404 Not Found response with the ErrorResponseDto object
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    // Handle UserNotFoundException
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(UserNotFoundException exception,
                                                                          WebRequest webRequest){
        // Create an ErrorResponseDto object with error details
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now()
        );
        // Return a 404 Not Found response with the ErrorResponseDto object
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    // Handle general Exception (catch-all)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(Exception exception,
                                                                        WebRequest webRequest){
        // Create an ErrorResponseDto object with error details
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                LocalDateTime.now()
        );

        // Return a 500 Internal Server Error response with the ErrorResponseDto object
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
