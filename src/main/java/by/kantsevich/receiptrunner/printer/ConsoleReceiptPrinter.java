package by.kantsevich.receiptrunner.printer;

import by.kantsevich.receiptrunner.mapper.ReceiptMapper;
import by.kantsevich.receiptrunner.model.Receipt;
import by.kantsevich.receiptrunner.mapper.TextReceiptMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsoleReceiptPrinter implements ReceiptPrinter {

    private final ReceiptMapper<String> textReceiptMapper;

    @Autowired
    public ConsoleReceiptPrinter(TextReceiptMapper textReceiptMapper) {
        this.textReceiptMapper = textReceiptMapper;
    }

    @Override
    public void print(Receipt receipt) {
        String textReceipt = textReceiptMapper.map(receipt);

        System.out.println(textReceipt);
    }

}
