package by.kantsevich.receiptrunner.advice;

import by.kantsevich.receiptrunner.exception.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({ProductNotFoundException.class, DiscountCardNotFoundException.class})
    public ResponseEntity<ApiError> handlePurchaseDataNotFound(RuntimeException e) {
        var purchaseDataNotFoundError = getApiError(HttpStatus.NOT_FOUND, e);

        return new ResponseEntity<>(purchaseDataNotFoundError, purchaseDataNotFoundError.getStatus());
    }

    @ExceptionHandler({PdfIOException.class, OutputReceiptException.class})
    public ResponseEntity<ApiError> handleIOReceipt(RuntimeException e) {
        var ioReceiptError = getApiError(HttpStatus.INTERNAL_SERVER_ERROR, e);

        return new ResponseEntity<>(ioReceiptError, ioReceiptError.getStatus());
    }

    @ExceptionHandler(PdfNotFoundException.class)
    public ResponseEntity<ApiError> handlePdfNotFound(RuntimeException e) {
        var pdfNotFoundError = getApiError(HttpStatus.NOT_FOUND, e);

        return new ResponseEntity<>(pdfNotFoundError, pdfNotFoundError.getStatus());
    }

    @ExceptionHandler(ReceiptEmptyException.class)
    public ResponseEntity<ApiError> handleReceiptEmpty(RuntimeException e) {
        var receiptEmptyError = getApiError(HttpStatus.BAD_REQUEST, e);

        return new ResponseEntity<>(receiptEmptyError, receiptEmptyError.getStatus());
    }

    private ApiError getApiError(HttpStatus httpStatus, RuntimeException e) {
        return new ApiError(
                httpStatus,
                LocalDateTime.now(),
                e.getMessage()
        );
    }
}
