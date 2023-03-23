package by.kantsevich.receiptrunner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ReceiptEmptyException extends RuntimeException {

    public ReceiptEmptyException(String message) {
        super(message);
    }
}
