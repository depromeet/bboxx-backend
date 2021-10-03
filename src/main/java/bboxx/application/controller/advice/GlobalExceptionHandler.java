package bboxx.application.controller.advice;

import bboxx.application.controller.dto.response.ApiResponse;
import bboxx.domain.exception.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({DomainException.class})
    public ResponseEntity<ApiResponse<Object>> domainExceptionHandler(DomainException exception) {
        return ResponseEntity
                .status(exception.getErrorCode().getStatus())
                .body(ApiResponse.failure(exception.getErrorCode().getCode(), exception.getErrorCode().getMessage()));
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Object> defaultExceptionHandler(Exception exception) {
        return ApiResponse.failure("BX001", "internal server error");
    }
}