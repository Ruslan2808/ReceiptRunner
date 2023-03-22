package by.kantsevich.receiptrunner.controller;

import by.kantsevich.receiptrunner.mapper.PdfReceiptMapper;
import by.kantsevich.receiptrunner.mapper.ReceiptMapper;
import by.kantsevich.receiptrunner.parser.PurchaseDataParser;
import by.kantsevich.receiptrunner.printer.ConsoleReceiptPrinter;
import by.kantsevich.receiptrunner.printer.FileReceiptPrinter;
import by.kantsevich.receiptrunner.printer.ReceiptPrinter;
import by.kantsevich.receiptrunner.service.ReceiptService;
import by.kantsevich.receiptrunner.service.impl.ReceiptServiceImpl;

import com.itextpdf.text.Document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/receipt")
public class ReceiptController {

    private final ReceiptService receiptService;
    private final ReceiptPrinter fileReceiptPrinter;
    private final ReceiptPrinter consoleReceiptPrinter;
    private final ReceiptMapper<Document> pdfReceiptMapper;

    @Autowired
    public ReceiptController(ReceiptServiceImpl receiptServiceImpl,
                             FileReceiptPrinter fileReceiptPrinter,
                             ConsoleReceiptPrinter consoleReceiptPrinter,
                             PdfReceiptMapper pdfReceiptMapper) {
        this.receiptService = receiptServiceImpl;
        this.fileReceiptPrinter = fileReceiptPrinter;
        this.consoleReceiptPrinter = consoleReceiptPrinter;
        this.pdfReceiptMapper = pdfReceiptMapper;
    }

    @GetMapping
    public ResponseEntity<String> getReceipt(@RequestParam(value = "productId") List<Integer> productsId,
                                             @RequestParam(value = "productQty") List<Integer> productsQty,
                                             @RequestParam(value = "card", defaultValue = "0", required = false) Integer numberDiscountCard) {
        Map<Integer, Integer> products = PurchaseDataParser.getProducts(productsId, productsQty);
        var receipt = receiptService.createReceipt(products, numberDiscountCard);

        consoleReceiptPrinter.print(receipt);
        fileReceiptPrinter.print(receipt);
        pdfReceiptMapper.map(receipt);

        return ResponseEntity.ok("Receipt successfully generated. Can see it in the console, pdf and text file");
    }
}
