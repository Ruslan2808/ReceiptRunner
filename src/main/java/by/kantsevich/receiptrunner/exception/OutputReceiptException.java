package by.kantsevich.receiptrunner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class OutputReceiptException extends RuntimeException {

    public OutputReceiptException(String message) {
        super(message);
    }
}
