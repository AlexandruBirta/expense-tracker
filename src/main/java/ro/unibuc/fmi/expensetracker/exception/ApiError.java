package ro.unibuc.fmi.expensetracker.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
@Getter
public class ApiError {

    private final String errorMessage;
    private final HttpStatus httpStatus;
    private final LocalDateTime timestamp;

}