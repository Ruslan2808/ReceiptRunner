package by.kantsevich.receiptrunner.mapper;

import by.kantsevich.receiptrunner.model.Receipt;
import by.kantsevich.receiptrunner.model.ReceiptProduct;
import by.kantsevich.receiptrunner.util.ReceiptInformation;

import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class TextReceiptMapper implements ReceiptMapper<String> {

    public static final String LINE_SYMBOL = "-";
    private static final int DIVIDING_LINE_WIDTH = 52;
    private static final int WIDTH = 50;

    @Override
    public String map(Receipt receipt) {
        var textReceipt = new StringBuilder();

        String cashierAndCreationDate = String.format("|%-10.10s%-23.23s %-5.5s %-10.10s|", "CASHIER: №", "1234", "DATE:",
                receipt.getCreationDate().format(DateTimeFormatter.ofPattern(ReceiptInformation.DATE_PATTERN)));
        String creationTime = String.format("|%33.33s %-5.5s %-10.10s|", "", "TIME:",
                receipt.getCreationTime().format(DateTimeFormatter.ofPattern(ReceiptInformation.TIME_PATTERN)));
        String productsHeader = String.format("|%-5.5s %-23.23s %9.9s %10.10s|", "QTY", "PRODUCT", "PRICE", "TOTAL");

        textReceipt.append(LINE_SYMBOL.repeat(DIVIDING_LINE_WIDTH)).append("\n")
                .append(centerString(WIDTH, ReceiptInformation.TITLE)).append("\n")
                .append(centerString(WIDTH, ReceiptInformation.SHOP)).append("\n")
                .append(centerString(WIDTH, ReceiptInformation.SHOP_ADDRESS)).append("\n")
                .append(centerString(WIDTH, "Tel: " + ReceiptInformation.PHONE)).append("\n")
                .append(cashierAndCreationDate).append("\n")
                .append(creationTime).append("\n")
                .append(LINE_SYMBOL.repeat(DIVIDING_LINE_WIDTH)).append("\n")
                .append(productsHeader).append("\n");

        for (ReceiptProduct receiptProduct : receipt.getReceiptProducts()) {
            textReceipt.append(String.format("|%-5.5s %-23.23s %8.8s$ %9.9s$|",
                        receiptProduct.getQty(),
                        receiptProduct.getName(),
                        String.format("%.2f", receiptProduct.getPrice()),
                        String.format("%.2f", receiptProduct.calculateTotal()))
                    ).append("\n");

            if (receiptProduct.getIsPromotional() && receiptProduct.getQty() > 5) {
                textReceipt.append(String.format("|%-5.5s %5.5s %-6.6s %-31.31s|", "", "DISC:",
                                String.format("%.2f%%", ReceiptProduct.DISCOUNT_PROMOTIONAL_PRODUCT), "")).append("\n");
            }
        }

        textReceipt.append(LINE_SYMBOL.repeat(DIVIDING_LINE_WIDTH)).append("\n");

        if (receipt.getDiscount() > 0) {
            textReceipt.append(String.format("|TOTAL WITHOUT DISCOUNT %26.26s$|",
                            String.format("%.2f", receipt.calculateUnDiscountedTotal()))).append("\n")
                    .append(String.format("|DISCOUNT %40.40s%%|", String.format("%.2f", receipt.getDiscount()))).append("\n");
        }

        textReceipt.append(String.format("|TOTAL %43.43s$|", String.format("%.2f", receipt.calculateTotal()))).append("\n")
                .append(LINE_SYMBOL.repeat(DIVIDING_LINE_WIDTH));

        return textReceipt.toString();
    }

    public static String centerString(int width, String s) {
        return String.format(
                "|%-" + width  + "s|",
                String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s)
        );
    }
}
