package by.kantsevich.receiptrunner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class PdfIOException extends RuntimeException {

    public PdfIOException(String message) {
        super(message);
    }
}
