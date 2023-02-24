package by.kantsevich.receiptrunner.controller;

import by.kantsevich.receiptrunner.exception.DiscountCardNotFoundException;
import by.kantsevich.receiptrunner.exception.ProductNotFoundException;
import by.kantsevich.receiptrunner.parser.PurchaseDataParser;
import by.kantsevich.receiptrunner.printer.ConsoleReceiptPrinter;
import by.kantsevich.receiptrunner.printer.FileReceiptPrinter;
import by.kantsevich.receiptrunner.printer.ReceiptPrinter;
import by.kantsevich.receiptrunner.service.ReceiptService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import java.util.*;

@RestController
@RequestMapping("/receipt")
public class ReceiptController {

    private final ReceiptService receiptService;
    private final ReceiptPrinter fileReceiptPrinter;
    private final ReceiptPrinter consoleReceiptPrinter;

    @Autowired
    public ReceiptController(ReceiptService receiptService,
                             FileReceiptPrinter fileReceiptPrinter,
                             ConsoleReceiptPrinter consoleReceiptPrinter) {
        this.receiptService = receiptService;
        this.fileReceiptPrinter = fileReceiptPrinter;
        this.consoleReceiptPrinter = consoleReceiptPrinter;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String getReceipt(
            @RequestParam(value = "productId") List<Integer> productsId,
            @RequestParam(value = "productQty") List<Integer> productsQty,
            @RequestParam(value = "card", defaultValue = "0", required = false) Integer numberDiscountCard
    ) throws ProductNotFoundException, DiscountCardNotFoundException, IOException {

        Map<Integer, Integer> products = PurchaseDataParser.getProducts(productsId, productsQty);

        var receipt = receiptService.createReceipt(products, numberDiscountCard);

        consoleReceiptPrinter.print(receipt);
        fileReceiptPrinter.print(receipt);

        return "Receipt successfully generated. Can see it in the console and file";
    }

}
