package by.kantsevich.receiptrunner.printer;

import by.kantsevich.receiptrunner.exception.ReceiptEmptyException;
import by.kantsevich.receiptrunner.mapper.ReceiptMapper;
import by.kantsevich.receiptrunner.model.Receipt;
import by.kantsevich.receiptrunner.mapper.TextReceiptMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import java.util.Objects;

@Component
public class FileReceiptPrinter implements ReceiptPrinter {

    public static final Path FILE_PATH = Path.of("receipts.txt");

    private final ReceiptMapper<String> textReceiptMapper;

    @Autowired
    public FileReceiptPrinter(TextReceiptMapper textReceiptMapper) {
        this.textReceiptMapper = textReceiptMapper;
    }

    @Override
    public void print(Receipt receipt) throws IOException {
        if (Objects.isNull(receipt)) {
            throw new ReceiptEmptyException("Receipt is empty");
        }

        String textReceipt = textReceiptMapper.map(receipt) + "\n";

        if (!Files.exists(FILE_PATH)) {
            Files.createFile(FILE_PATH);
        }

        Files.write(FILE_PATH, textReceipt.getBytes(), StandardOpenOption.APPEND);
        System.out.printf("The receipt is written to the file %s%n", FILE_PATH);
    }

}
