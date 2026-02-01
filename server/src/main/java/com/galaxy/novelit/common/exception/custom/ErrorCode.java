package com.galaxy.novelit.common.exception.custom;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 400 BAD_REQUEST
    EDIT_REFUSED(HttpStatus.BAD_REQUEST, "현재 편집이 불가능합니다!"),
    ILLEGAL_UUID(HttpStatus.BAD_REQUEST, "UUID 오류입니다!"),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다!"),
    NON_UNIQUE(HttpStatus.BAD_REQUEST, "NonUniqueException 발생!"),
    WRONG_DIRECTORY_TYPE(HttpStatus.BAD_REQUEST, "디렉토리 타입이 잘못되었습니다!"),
    NOT_COMMENT_WRITER(HttpStatus.BAD_REQUEST, "댓글 작성자가 아닙니다!"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD_REQUEST"),

    // 401 UNAUTHORIZED
    ACCESS_REFUSED(HttpStatus.UNAUTHORIZED, "권한이 없습니다!"),
    NOT_LOGGED_IN(HttpStatus.UNAUTHORIZED, "로그인 하지 않은 사용자입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED"),


    // 404 NOT_FOUND
    NO_SUCH_DIRECTORY(HttpStatus.NOT_FOUND, "존재하지 않는 디렉토리 혹은 파일입니다! 확인 후 다시 시도해주세요."),
    NO_SUCH_ELEMENT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 자료입니다!"),
    NO_SUCH_WORKSPACE(HttpStatus.NOT_FOUND, "없는 작품입니다!"),
    NO_SUCH_PLOT(HttpStatus.NOT_FOUND, "존재하지 않는 플롯입니다!"),
    NO_SUCH_COMMENT(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다!"),
    NO_SUCH_COMMENT_UUID(HttpStatus.NOT_FOUND, "존재하지 않는 코멘트 UUID입니다!"),
    NO_SUCH_COMMNET_INFO(HttpStatus.NOT_FOUND, "해당 댓글정보가 없습니다!"),
    DELETED_ELEMENT(HttpStatus.NOT_FOUND, "삭제된 자료입니다!"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND");

    private final HttpStatus status;
    private final String message;

    ErrorCode(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }
}
