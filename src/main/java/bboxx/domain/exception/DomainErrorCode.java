package bboxx.domain.exception;

import lombok.Getter;

@Getter
public enum DomainErrorCode {
    INTERNAL_SERVER_ERROR(500, "BD001", "internal server error"),
    UNAUTHORIZED_ERROR(401, "BD002", "unauthorized error"),
    SOCIAL_USER_FETCH_ERROR(500, "BD003", "social user fetch error");

    private final int status;
    private final String code;
    private final String message;

    DomainErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
