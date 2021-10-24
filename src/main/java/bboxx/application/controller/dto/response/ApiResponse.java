package bboxx.application.controller.dto.response;

import bboxx.domain.exception.DomainErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ApiResponse<T> {

    private static final String SUCCESS_CODE = "0";
    private static final String SUCCESS_MESSAGE = "success";

    private String code;

    private String message;

    private T data;

    public static ApiResponse<EmptyJsonResponse> success() {
        return new ApiResponse<>(SUCCESS_CODE, SUCCESS_MESSAGE, new EmptyJsonResponse());
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

    public static ApiResponse<EmptyJsonResponse> failure(String code, String message) {
        return new ApiResponse<>(code, message, new EmptyJsonResponse());
    }
    public static ApiResponse<EmptyJsonResponse> failure(DomainErrorCode errorCode) {
        return new ApiResponse<>(errorCode.getCode(), errorCode.getMessage(), new EmptyJsonResponse());
    }


}
