package org.ict.boot_react.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(
            ResponseStatusException ex){
        // ResponseStatusException 예외를 처리하는 메소드
        // 이 예외는 HTTP 상태 코드와 관련된 예외를 나타냄
        // ex.getReason() : 예외 발생 시 제공되는 상세 메세지
        // ex.getStatusCode() : HTTP 상태 코드
        return new ResponseEntity<>(ex.getReason(), ex.getStatusCode());
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDataAccessException(
            ResponseStatusException ex){
        // 데이버베이스 접근 중 발생하는 모든 예외를 처리하는 메소드
        // 주로 CRUD 작업 실패시 발생하는 예외
        // INTERNAL_SERVER_ERROR 로 설정해서 내부 서버 오류임을 알림
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("데이터 베이스 관련 오류 발생");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(
            ResponseStatusException ex){
        // 기타 모든 예외를 처리하는 메소드
        // 이 메소드는 처리 되지 않는 모든 예외 캐치
        // HTTPStatus.INTERNAL_SERVER_ERROR 로 설정해서 내부 서버 오류임을 알림
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(
            MethodArgumentNotValidException ex){
        // 메소드의 전달값이 유효하지 않을 때 발생하는 예외를 처리하는 메소드
        // 주로 @Valid 어노테이션이 사영된 매개변수 검증 과정에서 문제가 발생했을 때 이 예외 발생
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String filedName = ((FieldError) error).getField(); // 오루가 발생한 필드 이름
            String message = error.getDefaultMessage(); // 오류 메세지
            errors.put(filedName, message); // 필드명과 오류 메세지를 맵에 추가
        });

        // 유효성 검사 실패에 대한 상세 정보를 담은 Map을 클라이언트에게 반환
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
