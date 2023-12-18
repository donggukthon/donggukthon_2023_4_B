package donggukthon.volunmate.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /**
     * 400 Bad Request
     */
    BAD_REQUEST_ERROR(400, HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INVALID_ARGUMENT(400, HttpStatus.BAD_REQUEST, "요청에 유효하지 않은 인자입니다."),

    /**
     * 401 Unauthorized
     */
    UNAUTHORIZED_ERROR(401, HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),

    /**
     * 403 Forbidden
     */
    FORBIDDEN_ERROR(403, HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    MISSING_REQUEST_PARAMETER(403, HttpStatus.FORBIDDEN, "필수 요청 파라미터가 누락되었습니다."),
    ACCESS_DENIED(403, HttpStatus.FORBIDDEN, "접근이 거부되었습니다."),

    /**
     * 404 Not Found
     */
    NOT_FOUND_ERROR(404, HttpStatus.NOT_FOUND, "요청하신 리소스를 찾을 수 없습니다."),

    /**
     * 405 Method Not Allowed
     */
    METHOD_NOT_ALLOWED_ERROR(405, HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메소드입니다."),

    /**
     * 406 Not Acceptable
     */
    NOT_ACCEPTABLE_ERROR(406, HttpStatus.NOT_ACCEPTABLE, "요청한 리소스의 미디어 타입을 지원하지 않습니다."),

    /**
     * 500 Internal Server Error
     */
    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "서버에 오류가 발생하였습니다.");


    private final Integer code;
    private final HttpStatus status;
    private final String message;
}
