package by.kantsevich.receiptrunner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PdfNotFoundException extends PdfIOException {

    public PdfNotFoundException(String message) {
        super(message);
    }
}
