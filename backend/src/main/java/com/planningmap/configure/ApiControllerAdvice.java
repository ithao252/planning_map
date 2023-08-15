package com.planningmap.configure;

import com.planningmap.exception.CoreApiException;
import com.planningmap.support.error.ErrorType;
import com.planningmap.support.error.ExitCode;
import com.planningmap.support.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(CoreApiException.class)
    public ResponseEntity<ApiResponse<?>> handleCoreApiException(CoreApiException e) {
        ErrorType errorType = e.getErrorType();
        return new ResponseEntity<>(
                ApiResponse.error(errorType),
                errorType.getStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAccessDeniedException(AccessDeniedException e) {
        ErrorType errorType = new ErrorType(HttpStatus.FORBIDDEN, ExitCode.E403, e.getMessage());
        return new ResponseEntity<>(
                ApiResponse.error(errorType),
                errorType.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        ErrorType defaultError = new ErrorType();
        return new ResponseEntity<>(
                ApiResponse.error(defaultError),
                defaultError.getStatus());
    }

}
