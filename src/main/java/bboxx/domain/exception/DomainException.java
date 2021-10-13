package bboxx.domain.exception;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {

    private final DomainErrorCode errorCode;

    public DomainException() {
        this.errorCode = DomainErrorCode.INTERNAL_SERVER_ERROR;
    }

    public DomainException(DomainErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DomainException)) {
            return false;
        }
        return this.errorCode.getCode().equals(((DomainException) obj).errorCode.getCode());
    }
}


