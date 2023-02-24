package by.kantsevich.receiptrunner.mapper;

import by.kantsevich.receiptrunner.model.Receipt;
import by.kantsevich.receiptrunner.model.product.ReceiptProduct;
import by.kantsevich.receiptrunner.util.ReceiptProductTestBuilder;
import by.kantsevich.receiptrunner.util.ReceiptTestBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TextReceiptMapperTest {

    private ReceiptMapper<String> textReceiptMapper;
    private ReceiptProduct receiptProduct;
    private Receipt receipt;

    @BeforeEach
    void setUp() {
        textReceiptMapper = new TextReceiptMapper();

        receiptProduct = ReceiptProductTestBuilder
                .receiptProduct()
                .withQty(6)
                .withName("Milk 3%")
                .withIsPromotional(true)
                .build();
        receipt = ReceiptTestBuilder
                .receipt()
                .withReceiptProducts(List.of(receiptProduct))
                .withDiscount(3.0)
                .build();
    }

    @Test
    void checkMapReturnTextReceiptWithProduct() {
        String expectedProduct = String.format(
                "|%-5.5s %-23.23s %8.8s$ %9.9s$|",
                receiptProduct.getQty(),
                receiptProduct.getName(),
                String.format("%.2f", receiptProduct.getPrice()),
                String.format("%.2f", receiptProduct.calculateTotal())
        );

        String actualTextReceipt = textReceiptMapper.map(receipt);

        assertThat(actualTextReceipt).contains(expectedProduct);
    }

    @Test
    void checkMapReturnTextReceiptWithDiscountPromotionalProduct() {
        String expectedDiscount = String.format(
                "DISC: %.2f%%",
                ReceiptProduct.DISCOUNT_PROMOTIONAL_PRODUCT
        );

        String actualTextReceipt = textReceiptMapper.map(receipt);

        assertThat(actualTextReceipt).contains(expectedDiscount);
    }

    @Test
    void checkMapReturnTextReceiptWithDiscount() {
        String expectedDiscount = String.format(
                "|DISCOUNT %40.40s%%|",
                String.format("%.2f", receipt.getDiscount())
        );
        String expectedUnDiscountedTotal = String.format(
                "|TOTAL WITHOUT DISCOUNT %26.26s$|",
                String.format("%.2f", receipt.calculateUnDiscountedTotal())
        );

        String actualTextReceipt = textReceiptMapper.map(receipt);

        assertThat(actualTextReceipt).contains(expectedUnDiscountedTotal, expectedDiscount);
    }

    @Test
    void checkMapReturnTextReceiptWithTotal() {
        String expectedTotal = String.format(
                "|TOTAL %43.43s$|",
                String.format("%.2f", receipt.calculateTotal())
        );

        String actualTextReceipt = textReceiptMapper.map(receipt);

        assertThat(actualTextReceipt).contains(expectedTotal);
    }
}
