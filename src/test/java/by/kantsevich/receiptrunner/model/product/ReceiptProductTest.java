package by.kantsevich.receiptrunner.model.product;

import by.kantsevich.receiptrunner.util.ReceiptProductTestBuilder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ReceiptProductTest {

    @Test
    void checkCalculateTotalShouldReturnTotal() {
        ReceiptProduct receiptProduct = ReceiptProductTestBuilder
                .receiptProduct()
                .withQty(6)
                .build();
        Double expectedTotal = 6.0;

        Double actualTotal = receiptProduct.calculateTotal();

        assertThat(actualTotal).isEqualTo(expectedTotal);
    }

    @Test
    void checkCalculateTotalShouldReturnTotalWithDiscount() {
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
    void checkCalculateTotalShouldReturnTotalWithoutDiscount() {
        ReceiptProduct receiptProduct = ReceiptProductTestBuilder
                .receiptProduct()
                .withQty(5)
                .withIsPromotional(true)
                .build();
        Double expectedTotal = 5.0;

        Double actualTotal = receiptProduct.calculateTotal();

        assertThat(actualTotal).isEqualTo(expectedTotal);
    }

    @ParameterizedTest
    @MethodSource(value = "zeroProductArguments")
    void checkCalculateTotalShouldReturn0(int qty, double price) {
        ReceiptProduct receiptProduct = ReceiptProductTestBuilder
                .receiptProduct()
                .withQty(qty)
                .withPrice(price)
                .build();

        Double actualTotal = receiptProduct.calculateTotal();

        assertThat(actualTotal).isZero();
    }

    @ParameterizedTest
    @MethodSource(value = "maxProductArguments")
    void checkCalculateTotalShouldReturnTotalWhenMaxArguments(int qty, double price) {
        ReceiptProduct receiptProduct = ReceiptProductTestBuilder
                .receiptProduct()
                .withQty(qty)
                .withPrice(price)
                .build();
        Double expectedTotal = receiptProduct.getQty() * receiptProduct.getPrice();

        Double actualTotal = receiptProduct.calculateTotal();

        assertThat(actualTotal).isEqualTo(expectedTotal);
    }

    private static Stream<Arguments> maxProductArguments() {
        return Stream.of(
                arguments(Integer.MAX_VALUE, 1000.0),
                arguments(1000, Double.MAX_VALUE),
                arguments(Integer.MAX_VALUE, Double.MAX_VALUE)
        );
    }

    private static Stream<Arguments> zeroProductArguments() {
        return Stream.of(
                arguments(0, 1.0),
                arguments(1, 0.0),
                arguments(0, 0.0)
        );
    }
}
