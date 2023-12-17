package donggukthon.volunmate.dto.exception;

import donggukthon.volunmate.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ExceptionDto {
    private final Integer code;
    private final String message;

    public ExceptionDto(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public ExceptionDto(Exception e) {
        this.code = ErrorCode.INTERNAL_SERVER_ERROR.getCode();
        this.message = e.getMessage();
    }

    public static ExceptionDto of(ErrorCode errorCode) {
        return new ExceptionDto(errorCode);
    }
}
