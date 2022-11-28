package ro.unibuc.fmi.expensetracker.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.http.HttpStatus;

public enum ExceptionStatus {

    USER_NOT_FOUND("User with id '%s' not found!", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS("User with email '%s' already exists!", HttpStatus.BAD_REQUEST);

    private final String value;
    private final HttpStatus httpStatus;

    ExceptionStatus(String value, HttpStatus httpStatus) {
        this.value = value;
        this.httpStatus = httpStatus;
    }

    @JsonCreator
    public static ExceptionStatus fromValue(String text) {
        for (ExceptionStatus b : ExceptionStatus.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

}