package com.acirio.virtual_bookshelf.exception;

import com.acirio.virtual_bookshelf.dto.ErrorHandlerResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;


@ControllerAdvice
public class GlobalExceptionHandler {

    //Erro generico para quando o erro nao tiver sido previsto no codigo
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorHandlerResponse> handleExcepiton(Exception ex, HttpServletRequest request) {
        ErrorHandlerResponse response =  ErrorHandlerResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Erro interno do servidor.")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(Instant.now())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorHandlerResponse> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        ErrorHandlerResponse response =  ErrorHandlerResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error("Recurso não encontrado.")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(Instant.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorHandlerResponse> handleConflictException(ConflictException ex, HttpServletRequest request) {
        ErrorHandlerResponse response = ErrorHandlerResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .error("Conflito interno.")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(Instant.now())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorHandlerResponse> handleUnauthorizedException(UnauthorizedException ex, HttpServletRequest request) {
        ErrorHandlerResponse response = ErrorHandlerResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .error("Permissão negada.")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(Instant.now())
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
}
