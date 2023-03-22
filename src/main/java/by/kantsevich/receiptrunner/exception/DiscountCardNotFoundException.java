package by.kantsevich.receiptrunner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DiscountCardNotFoundException extends RuntimeException {

    public DiscountCardNotFoundException(String message) {
        super(message);
    }
}
