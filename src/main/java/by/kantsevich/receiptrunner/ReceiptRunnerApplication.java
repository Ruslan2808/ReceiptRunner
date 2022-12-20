package by.kantsevich.receiptrunner;

import by.kantsevich.receiptrunner.exception.DiscountCardNotFound;
import by.kantsevich.receiptrunner.exception.ProductNotFoundException;
import by.kantsevich.receiptrunner.model.Receipt;
import by.kantsevich.receiptrunner.parser.PurchaseDataParser;
import by.kantsevich.receiptrunner.printer.ConsoleReceiptPrinter;
import by.kantsevich.receiptrunner.printer.FileReceiptPrinter;
import by.kantsevich.receiptrunner.printer.ReceiptPrinter;
import by.kantsevich.receiptrunner.service.ReceiptService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

import java.util.Map;

@SpringBootApplication
public class ReceiptRunnerApplication implements CommandLineRunner {

    private final ReceiptService receiptService;
    private final ReceiptPrinter consoleReceiptPrinter;
    private final ReceiptPrinter fileReceiptPrinter;

    @Autowired
    public ReceiptRunnerApplication(ReceiptService receiptService,
                                    ConsoleReceiptPrinter consoleReceiptPrinter,
                                    FileReceiptPrinter fileReceiptPrinter) {
        this.receiptService = receiptService;
        this.consoleReceiptPrinter = consoleReceiptPrinter;
        this.fileReceiptPrinter = fileReceiptPrinter;
    }

    public static void main(String[] args) {
        SpringApplication.run(ReceiptRunnerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Map<Integer, Integer> products = PurchaseDataParser.getProducts(args);
        Integer numberDiscountCard = PurchaseDataParser.getNumberDiscountCard(args);

        try {
            Receipt receipt = receiptService.createReceipt(products, numberDiscountCard);

            consoleReceiptPrinter.print(receipt);
            fileReceiptPrinter.print(receipt);
        } catch (ProductNotFoundException | DiscountCardNotFound e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Error writing receipt to file");
            e.printStackTrace();
        }
    }

}
