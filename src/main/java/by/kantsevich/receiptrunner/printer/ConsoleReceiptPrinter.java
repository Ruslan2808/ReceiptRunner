package by.kantsevich.receiptrunner.printer;

import by.kantsevich.receiptrunner.exception.ReceiptEmptyException;
import by.kantsevich.receiptrunner.mapper.ReceiptMapper;
import by.kantsevich.receiptrunner.model.Receipt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ConsoleReceiptPrinter implements ReceiptPrinter {

    private final ReceiptMapper<String> textReceiptMapper;

    @Autowired
    public ConsoleReceiptPrinter(ReceiptMapper<String> textReceiptMapper) {
        this.textReceiptMapper = textReceiptMapper;
    }

    @Override
    public void print(Receipt receipt) {
        if (Objects.isNull(receipt)) {
            throw new ReceiptEmptyException("Receipt is empty");
        }

        String textReceipt = textReceiptMapper.map(receipt);

        System.out.println(textReceipt);
    }

}
