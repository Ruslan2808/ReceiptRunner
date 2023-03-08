package by.kantsevich.receiptrunner.aop.advice;

import by.kantsevich.receiptrunner.exception.*;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({ProductNotFoundException.class, DiscountCardNotFoundException.class})
    public ResponseEntity<ApiError> handlePurchaseDataNotFound(RuntimeException e) {
        var apiError = new ApiError(
                HttpStatus.NOT_FOUND,
                LocalDateTime.now(),
                List.of(e.getMessage())
        );

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler({ProductAlreadyExistsException.class, DiscountCardAlreadyExistsException.class})
    public ResponseEntity<ApiError> handlePurchaseDataAlreadyExists(RuntimeException e) {
        var apiError = new ApiError(
                HttpStatus.CONFLICT,
                LocalDateTime.now(),
                List.of(e.getMessage())
        );

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        var errorMessages = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        var apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                errorMessages
        );

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
