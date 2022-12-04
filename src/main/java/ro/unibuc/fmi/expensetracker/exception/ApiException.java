package ro.unibuc.fmi.expensetracker.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private final ExceptionStatus exceptionStatus;
    private final String[] errorParameters;
    private final String errorMessage;

    public ApiException(ExceptionStatus exceptionStatus, String... errorParameters) {
        this.exceptionStatus = exceptionStatus;
        this.errorParameters = errorParameters;
        this.errorMessage = String.format(exceptionStatus.toString(), errorParameters);
    }

}