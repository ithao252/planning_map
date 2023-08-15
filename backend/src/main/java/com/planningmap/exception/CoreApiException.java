package com.planningmap.exception;


import com.planningmap.support.error.ErrorType;
import lombok.Getter;

@Getter
public class CoreApiException extends RuntimeException {
    private final ErrorType errorType;

    public CoreApiException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }
}
