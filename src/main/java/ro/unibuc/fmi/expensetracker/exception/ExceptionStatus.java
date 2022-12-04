package ro.unibuc.fmi.expensetracker.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.http.HttpStatus;
import ro.unibuc.fmi.expensetracker.model.Expense;

import java.util.Arrays;

public enum ExceptionStatus {

    USER_NOT_FOUND("User with id '%s' not found!", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS("User with email '%s' already exists!", HttpStatus.BAD_REQUEST),
    TRIP_NOT_FOUND("Trip with id '%s' not found!", HttpStatus.NOT_FOUND),
    EXPENSE_NOT_FOUND("Expense with id '%s' not found!", HttpStatus.NOT_FOUND),
    EXPENSE_ALREADY_EXISTS("Expense with id '%s' already exists!", HttpStatus.BAD_REQUEST),
    INVALID_EXPENSE_TYPE("Invalid expense type '%s', expense type must be one of: " + Arrays.asList(Expense.ExpenseType.values()), HttpStatus.BAD_REQUEST),
    INVALID_PERSONAL_EXPENSE("Invalid amount of users supplied. For personal expenses you must supply a single user. User ids supplied: '%s'", HttpStatus.BAD_REQUEST),
    INVALID_GROUP_EXPENSE("Invalid amount of users supplied. For group expenses you must supply more than a single user. User ids supplied: '%s'", HttpStatus.BAD_REQUEST),
    EXPENSE_USER_NOT_FOUND("User with id '%s' is not participating in the group expense with id '%s'.", HttpStatus.NOT_FOUND),
    INVALID_EXPENSE_TYPE_FOR_PAYMENT_CALCULATION("Expense of type '%s' is invalid for user payment calculation. Expense type must be " + Expense.ExpenseType.GROUP + ".", HttpStatus.BAD_REQUEST);

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