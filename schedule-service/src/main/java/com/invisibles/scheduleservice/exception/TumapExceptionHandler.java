package com.invisibles.scheduleservice.exception;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class TumapExceptionHandler {

    private static final String BAD_REQUEST = "400";
    private static final String NOT_FOUND = "404";
    private static final String UNAUTHORIZED = "401";

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse("Некорректные параметры запроса", BAD_REQUEST, e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalStateException(IllegalArgumentException e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse("a method has been invoked at an illegal or inappropriate time", BAD_REQUEST, e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler({ AuthenticationException.class })
    @ResponseBody
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(Exception ex) {

        ApiErrorResponse errorResponse = new ApiErrorResponse("Authentication failed", UNAUTHORIZED, ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiErrorResponse> handleNoSuchElementException(NoSuchElementException e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse("Ссылка не найдена", NOT_FOUND, e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ApiErrorResponse> handleDateTimeParseException(DateTimeParseException e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse("Неверный формат времени. Используйтте dd.MM.YYYY", BAD_REQUEST, e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ApiErrorResponse> handleSQLException(SQLException e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse("Неверный формат времени. Используйтте dd.MM.YYYY", BAD_REQUEST, e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


}
