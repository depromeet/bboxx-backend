package bboxx.application.controller.advice;

import bboxx.application.controller.dto.response.ApiResponse;
import bboxx.domain.exception.DomainException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({DomainException.class})
    public ResponseEntity<ApiResponse<Object>> domainExceptionHandler(DomainException exception) {
        return ResponseEntity.status(exception.getErrorCode().getStatus())
                .body(ApiResponse.failure(exception.getErrorCode().getCode(), exception.getErrorCode().getMessage()));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiResponse<Object>> defaultExceptionHandler(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.failure("BX001", "internal server error"));
    }
}