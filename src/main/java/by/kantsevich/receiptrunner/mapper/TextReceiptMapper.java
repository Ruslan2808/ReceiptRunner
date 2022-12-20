package by.kantsevich.receiptrunner.mapper;

import by.kantsevich.receiptrunner.model.Receipt;
import by.kantsevich.receiptrunner.model.product.ReceiptProduct;

import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class TextReceiptMapper implements ReceiptMapper<String> {

    @Override
    public String map(Receipt receipt) {
        var textReceipt = new StringBuilder();

        textReceipt.append("-".repeat(52)).append("\n")
                .append(centerString(50, "CASH RECEIPT")).append("\n")
                .append(centerString(50, "SUPERMARKET 123")).append("\n")
                .append(centerString(50, "12, MILKYWAY Galaxy/ Earth")).append("\n")
                .append(centerString(50, "Tel: 123-456-7890")).append("\n");

        textReceipt.append(String.format("|%-10.10s%-23.23s %-5.5s %-10.10s|",
                "CASHIER: №", "1234", "DATE:",
                receipt.getCreationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        )).append("\n");

        textReceipt.append(String.format("|%33.33s %-5.5s %-10.10s|",
                "", "TIME:", receipt.getCreationTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
        )).append("\n");

        textReceipt.append("-".repeat(52)).append("\n")
                .append(String.format("|%-5.5s %-23.23s %9.9s %10.10s|\n", "QTY", "PRODUCT", "PRICE", "TOTAL"));

        for (ReceiptProduct receiptProduct : receipt.getReceiptProducts()) {
            textReceipt.append(
                    String.format("|%-5.5s %-23.23s %8.8s$ %9.9s$|\n",
                    receiptProduct.getQty(),
                    receiptProduct.getName(),
                    String.format("%.2f", receiptProduct.getPrice()),
                    String.format("%.2f", receiptProduct.calculateTotal())
            ));

            if (receiptProduct.getPromotional() && receiptProduct.getQty() > 5) {
                textReceipt.append(
                        String.format("|%-5.5s %5.5s %-6.6s %-31.31s|\n",
                        "", "DISC:", ReceiptProduct.DISCOUNT_PROMOTIONAL_PRODUCT + "%", "")
                );
            }
        }

        textReceipt.append("-".repeat(52)).append("\n");

        if (receipt.getDiscount() > 0) {
            textReceipt.append(
                    String.format("|TOTAL WITHOUT DISCOUNT %26.26s$|\n",
                    String.format("%.2f", receipt.calculateUnDiscountedTotal()))
            ).append(String.format("|DISCOUNT %41.41s|\n", receipt.getDiscount() + "%"));
        }

        textReceipt.append(String.format("|TOTAL %43.43s$|\n", String.format("%.2f", receipt.calculateTotal())))
                .append("-".repeat(52));

        return textReceipt.toString();
    }

    public static String centerString(int width, String s) {
        return String.format(
                "|%-" + width  + "s|",
                String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s)
        );
    }

}
