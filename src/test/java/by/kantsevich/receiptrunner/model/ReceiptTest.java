package by.kantsevich.receiptrunner.model;

import by.kantsevich.receiptrunner.model.product.ReceiptProduct;
import by.kantsevich.receiptrunner.util.ReceiptProductTestBuilder;
import by.kantsevich.receiptrunner.util.ReceiptTestBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReceiptTest {

    private Receipt receipt;

    @Nested
    class ReceiptTotalTest {

        @BeforeEach
        void setUp() {
            ReceiptProduct receiptProductOne = ReceiptProductTestBuilder
                    .receiptProduct()
                    .withQty(2)
                    .withName("Milk 3%")
                    .build();
            ReceiptProduct receiptProductTwo = ReceiptProductTestBuilder
                    .receiptProduct()
                    .withQty(3)
                    .withName("Rye Bread 500 g")
                    .withPrice(0.75)
                    .build();
            receipt = ReceiptTestBuilder
                    .receipt()
                    .withReceiptProducts(List.of(receiptProductOne, receiptProductTwo))
                    .withDiscount(3.0)
                    .build();
        }

        @Test
        void checkCalculateTotalReturnTotalWithDiscount() {
            double expectedTotal = 4.12;

            Double actualTotal = receipt.calculateTotal();

            assertThat(actualTotal).isEqualTo(expectedTotal);
        }

        @Test
        void checkCalculateUnDiscountedTotalReturnTotalWithoutDiscount() {
            double expectedTotal = 4.25;

            Double actualTotal = receipt.calculateUnDiscountedTotal();

            assertThat(actualTotal).isEqualTo(expectedTotal);
        }
    }

    @Nested
    class ReceiptTotalWhenZeroQtyTest {

        @BeforeEach
        void setUp() {
            ReceiptProduct receiptProductOne = ReceiptProductTestBuilder
                    .receiptProduct()
                    .withQty(0)
                    .withName("Milk 3%")
                    .build();
            ReceiptProduct receiptProductTwo = ReceiptProductTestBuilder
                    .receiptProduct()
                    .withQty(0)
                    .withName("Rye Bread 500 g")
                    .build();
            receipt = ReceiptTestBuilder
                    .receipt()
                    .withReceiptProducts(List.of(receiptProductOne, receiptProductTwo))
                    .withDiscount(3.0)
                    .build();
        }

        @Test
        void checkCalculateTotalReturnZero() {
            Double actualTotal = receipt.calculateTotal();

            assertThat(actualTotal).isZero();
        }

        @Test
        void checkCalculateUnDiscountedTotalReturnZero() {
            Double actualTotal = receipt.calculateUnDiscountedTotal();

            assertThat(actualTotal).isZero();
        }
    }

    @Nested
    class ReceiptTotalWhenZeroPriceTest {

        @BeforeEach
        void setUp() {
            ReceiptProduct receiptProductOne = ReceiptProductTestBuilder
                    .receiptProduct()
                    .withPrice(0.0)
                    .withName("Milk 3%")
                    .build();
            ReceiptProduct receiptProductTwo = ReceiptProductTestBuilder
                    .receiptProduct()
                    .withPrice(0.0)
                    .withName("Rye Bread 500 g")
                    .build();
            receipt = ReceiptTestBuilder
                    .receipt()
                    .withReceiptProducts(List.of(receiptProductOne, receiptProductTwo))
                    .withDiscount(3.0)
                    .build();
        }

        @Test
        void checkCalculateTotalReturnZero() {
            Double actualTotal = receipt.calculateTotal();

            assertThat(actualTotal).isZero();
        }

        @Test
        void checkCalculateUnDiscountedTotalReturnZero() {
            Double actualTotal = receipt.calculateUnDiscountedTotal();

            assertThat(actualTotal).isZero();
        }
    }

    @Nested
    class ReceiptTotalWhenMaxQtyTest {

        @BeforeEach
        void setUp() {
            ReceiptProduct receiptProductOne = ReceiptProductTestBuilder
                    .receiptProduct()
                    .withQty(Integer.MAX_VALUE)
                    .withPrice(1000.0)
                    .withName("Milk 3%")
                    .build();
            ReceiptProduct receiptProductTwo = ReceiptProductTestBuilder
                    .receiptProduct()
                    .withQty(Integer.MAX_VALUE)
                    .withPrice(2000.0)
                    .withName("Rye Bread 500 g")
                    .build();
            receipt = ReceiptTestBuilder
                    .receipt()
                    .withReceiptProducts(List.of(receiptProductOne, receiptProductTwo))
                    .withDiscount(3.0)
                    .build();
        }

        @Test
        void checkCalculateTotalReturnTotal() {
            Double expectedTotal = Math.round(
                    receipt.calculateUnDiscountedTotal() * (1 - receipt.getDiscount() / 100.0) * 100.0
            ) / 100.0;

            Double actualTotal = receipt.calculateTotal();

            assertThat(actualTotal).isEqualTo(expectedTotal);
        }

        @Test
        void checkCalculateUnDiscountedTotalReturnUnDiscountedTotal() {
            Double expectedTotal = receipt.getReceiptProducts().stream()
                    .map(ReceiptProduct::calculateTotal)
                    .reduce(Double::sum)
                    .orElse(0.0);

            Double actualTotal = receipt.calculateUnDiscountedTotal();

            assertThat(actualTotal).isEqualTo(expectedTotal);
        }
    }

    @Nested
    class ReceiptTotalWhenMaxPriceTest {

        @BeforeEach
        void setUp() {
            ReceiptProduct receiptProductOne = ReceiptProductTestBuilder
                    .receiptProduct()
                    .withQty(1000)
                    .withPrice(Double.MAX_VALUE)
                    .withName("Milk 3%")
                    .build();
            ReceiptProduct receiptProductTwo = ReceiptProductTestBuilder
                    .receiptProduct()
                    .withQty(2000)
                    .withPrice(Double.MAX_VALUE)
                    .withName("Rye Bread 500 g")
                    .build();
            receipt = ReceiptTestBuilder
                    .receipt()
                    .withReceiptProducts(List.of(receiptProductOne, receiptProductTwo))
                    .withDiscount(3.0)
                    .build();
        }

        @Test
        void checkCalculateTotalReturnTotal() {
            Double expectedTotal = Math.round(
                    receipt.calculateUnDiscountedTotal() * (1 - receipt.getDiscount() / 100.0) * 100.0
            ) / 100.0;

            Double actualTotal = receipt.calculateTotal();

            assertThat(actualTotal).isEqualTo(expectedTotal);
        }

        @Test
        void checkCalculateUnDiscountedTotalReturnUnDiscountedTotal() {
            Double expectedTotal = receipt.getReceiptProducts().stream()
                    .map(ReceiptProduct::calculateTotal)
                    .reduce(Double::sum)
                    .orElse(0.0);

            Double actualTotal = receipt.calculateUnDiscountedTotal();

            assertThat(actualTotal).isEqualTo(expectedTotal);
        }
    }

    @Nested
    class ReceiptTotalWhenMaxPriceAndQtyTest {

        @BeforeEach
        void setUp() {
            ReceiptProduct receiptProductOne = ReceiptProductTestBuilder
                    .receiptProduct()
                    .withQty(Integer.MAX_VALUE)
                    .withPrice(Double.MAX_VALUE)
                    .withName("Milk 3%")
                    .build();
            ReceiptProduct receiptProductTwo = ReceiptProductTestBuilder
                    .receiptProduct()
                    .withQty(Integer.MAX_VALUE)
                    .withPrice(Double.MAX_VALUE)
                    .withName("Rye Bread 500 g")
                    .build();
            receipt = ReceiptTestBuilder
                    .receipt()
                    .withReceiptProducts(List.of(receiptProductOne, receiptProductTwo))
                    .withDiscount(3.0)
                    .build();
        }

        @Test
        void checkCalculateTotalReturnTotal() {
            Double expectedTotal = Math.round(
                    receipt.calculateUnDiscountedTotal() * (1 - receipt.getDiscount() / 100.0) * 100.0
            ) / 100.0;

            Double actualTotal = receipt.calculateTotal();

            assertThat(actualTotal).isEqualTo(expectedTotal);
        }

        @Test
        void checkCalculateUnDiscountedTotalReturnUnDiscountedTotal() {
            Double expectedTotal = receipt.getReceiptProducts().stream()
                    .map(ReceiptProduct::calculateTotal)
                    .reduce(Double::sum)
                    .orElse(0.0);

            Double actualTotal = receipt.calculateUnDiscountedTotal();

            assertThat(actualTotal).isEqualTo(expectedTotal);
        }
    }
}
