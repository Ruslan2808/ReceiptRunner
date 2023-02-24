package by.kantsevich.receiptrunner.model.product;

import by.kantsevich.receiptrunner.util.ReceiptProductTestBuilder;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReceiptProductTest {

    @Test
    void checkCalculateTotalReturnTotal() {
        ReceiptProduct receiptProduct = ReceiptProductTestBuilder
                .receiptProduct()
                .withQty(6)
                .build();
        Double expectedTotal = 6.0;

        Double actualTotal = receiptProduct.calculateTotal();

        assertThat(actualTotal).isEqualTo(expectedTotal);
    }

    @Test
    void checkCalculateTotalReturnZeroWhenZeroQty() {
        ReceiptProduct receiptProduct = ReceiptProductTestBuilder
                .receiptProduct()
                .withQty(0)
                .build();

        Double actualTotal = receiptProduct.calculateTotal();

        assertThat(actualTotal).isZero();
    }

    @Test
    void checkCalculateTotalReturnZeroWhenZeroPrice() {
        ReceiptProduct receiptProduct = ReceiptProductTestBuilder
                .receiptProduct()
                .withPrice(0.0)
                .build();

        Double actualTotal = receiptProduct.calculateTotal();

        assertThat(actualTotal).isZero();
    }

    @Test
    void checkCalculateTotalReturnTotalWhenMaxQty() {
        ReceiptProduct receiptProduct = ReceiptProductTestBuilder
                .receiptProduct()
                .withQty(Integer.MAX_VALUE)
                .withPrice(1000.0)
                .build();
        Double expectedTotal = receiptProduct.getQty() * receiptProduct.getPrice();

        Double actualTotal = receiptProduct.calculateTotal();

        assertThat(actualTotal).isEqualTo(expectedTotal);
    }

    @Test
    void checkCalculateTotalReturnTotalWhenMaxPrice() {
        ReceiptProduct receiptProduct = ReceiptProductTestBuilder
                .receiptProduct()
                .withQty(1000)
                .withPrice(Double.MAX_VALUE)
                .build();
        Double expectedTotal = receiptProduct.getQty() * receiptProduct.getPrice();

        Double actualTotal = receiptProduct.calculateTotal();

        assertThat(actualTotal).isEqualTo(expectedTotal);
    }

    @Test
    void checkCalculateTotalReturnTotalWhenMaxQtyAndPrice() {
        ReceiptProduct receiptProduct = ReceiptProductTestBuilder
                .receiptProduct()
                .withQty(Integer.MAX_VALUE)
                .withPrice(Double.MAX_VALUE)
                .build();
        Double expectedTotal = receiptProduct.getQty() * receiptProduct.getPrice();

        Double actualTotal = receiptProduct.calculateTotal();

        assertThat(actualTotal).isEqualTo(expectedTotal);
    }

    @Test
    void checkCalculateTotalReturnTotalWithDiscount() {
        ReceiptProduct receiptProduct = ReceiptProductTestBuilder
                .receiptProduct()
                .withQty(6)
                .withIsPromotional(true)
                .build();
        Double expectedTotal = 5.40;

        Double actualTotal = receiptProduct.calculateTotal();

        assertThat(actualTotal).isEqualTo(expectedTotal);
    }

    @Test
    void checkCalculateTotalReturnTotalWithoutDiscount() {
        ReceiptProduct receiptProduct = ReceiptProductTestBuilder
                .receiptProduct()
                .withQty(5)
                .withIsPromotional(true)
                .build();
        Double expectedTotal = 5.0;

        Double actualTotal = receiptProduct.calculateTotal();

        assertThat(actualTotal).isEqualTo(expectedTotal);
    }
}
