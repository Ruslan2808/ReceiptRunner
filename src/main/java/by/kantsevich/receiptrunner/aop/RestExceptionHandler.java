package by.kantsevich.receiptrunner.advice;

import by.kantsevich.receiptrunner.exception.ApiError;
import by.kantsevich.receiptrunner.exception.DiscountCardNotFoundException;
import by.kantsevich.receiptrunner.exception.ProductNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({ProductNotFoundException.class, DiscountCardNotFoundException.class})
    public ResponseEntity<ApiError> handlePurchaseDataNotFound(RuntimeException e) {
        var apiError = new ApiError(
                HttpStatus.NOT_FOUND,
                LocalDateTime.now(),
                e.getMessage()
        );

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}
