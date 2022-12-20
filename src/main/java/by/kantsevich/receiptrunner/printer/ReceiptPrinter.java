package by.kantsevich.receiptrunner.printer;

import by.kantsevich.receiptrunner.model.Receipt;

import java.io.IOException;

public interface ReceiptPrinter {
    void print(Receipt receipt) throws IOException;
}
