package by.kantsevich.receiptrunner.model;

import by.kantsevich.receiptrunner.model.product.ReceiptProduct;
import by.kantsevich.receiptrunner.util.ReceiptProductTestBuilder;
import by.kantsevich.receiptrunner.util.ReceiptTestBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ReceiptTest {

    @Nested
    class ReceiptTotalTest {

        private Receipt receipt;

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
        void checkCalculateTotalShouldReturnTotalWithDiscount() {
            double expectedTotal = 4.12;

            Double actualTotal = receipt.calculateTotal();

            assertThat(actualTotal).isEqualTo(expectedTotal);
        }

        @Test
        void checkCalculateUnDiscountedTotalShouldReturnTotalWithoutDiscount() {
            double expectedTotal = 4.25;

            Double actualTotal = receipt.calculateUnDiscountedTotal();

            assertThat(actualTotal).isEqualTo(expectedTotal);
        }
    }

    @Nested
    class ReceiptTotalZeroArgumentsTest {

        @ParameterizedTest
        @MethodSource(value = "zeroProductArguments")
        void checkCalculateTotalShouldReturn0(int qty, double price) {
            ReceiptProduct receiptProductOne = ReceiptProductTestBuilder
                    .receiptProduct()
                    .withQty(qty)
                    .withPrice(price)
                    .withName("Milk 3%")
                    .build();
            ReceiptProduct receiptProductTwo = ReceiptProductTestBuilder
                    .receiptProduct()
                    .withQty(qty)
                    .withPrice(price)
                    .withName("Rye Bread 500 g")
                    .build();
            Receipt receipt = ReceiptTestBuilder
                    .receipt()
                    .withReceiptProducts(List.of(receiptProductOne, receiptProductTwo))
                    .withDiscount(3.0)
                    .build();

            Double actualTotal = receipt.calculateTotal();

            assertThat(actualTotal).isZero();
        }

        @ParameterizedTest
        @MethodSource(value = "zeroProductArguments")
        void checkCalculateUnDiscountedTotalShouldReturn0(int qty, double price) {
            ReceiptProduct receiptProductOne = ReceiptProductTestBuilder
                    .receiptProduct()
                    .withQty(qty)
                    .withPrice(price)
                    .withName("Milk 3%")
                    .build();
            ReceiptProduct receiptProductTwo = ReceiptProductTestBuilder
                    .receiptProduct()
                    .withQty(qty)
                    .withPrice(price)
                    .withName("Rye Bread 500 g")
                    .build();
            Receipt receipt = ReceiptTestBuilder
                    .receipt()
                    .withReceiptProducts(List.of(receiptProductOne, receiptProductTwo))
                    .withDiscount(3.0)
                    .build();

            Double actualTotal = receipt.calculateUnDiscountedTotal();

            assertThat(actualTotal).isZero();
        }

        private static Stream<Arguments> zeroProductArguments() {
            return Stream.of(
                    arguments(0, 1.0),
                    arguments(1, 0.0),
                    arguments(0, 0.0)
            );
        }
    }

    @Nested
    class ReceiptTotalMaxArgumentsTest {

        @Test
        void checkCalculateTotalShouldReturnTotalWhenMaxQty() {
            ReceiptProduct receiptProductOne = ReceiptProductTestBuilder
                    .receiptProduct()
                    .withQty(Integer.MAX_VALUE)
                    .withPrice(1000.0)
                    .withName("Milk 3%")
                    .build();
            ReceiptProduct receiptProductTwo = ReceiptProductTestBuilder
                    .receiptProduct()
                    .withQty(Integer.MAX_VALUE)
                    .withPrice(1000.0)
                    .withName("Rye Bread 500 g")
                    .build();
            Receipt receipt = ReceiptTestBuilder
                    .receipt()
                    .withReceiptProducts(List.of(receiptProductOne, receiptProductTwo))
                    .withDiscount(3.0)
                    .build();
            Double expectedTotal = 4.16611827518E12;

            Double actualTotal = receipt.calculateTotal();

            assertThat(actualTotal).isEqualTo(expectedTotal);
        }

        @Test
        void checkCalculateTotalShouldReturnTotalWhenMaxPrice() {
            ReceiptProduct receiptProductOne = ReceiptProductTestBuilder
                    .receiptProduct()
                    .withQty(1000)
                    .withPrice(Double.MAX_VALUE)
                    .withName("Milk 3%")
                    .build();
            ReceiptProduct receiptProductTwo = ReceiptProductTestBuilder
                    .receiptProduct()
                    .withQty(1000)
                    .withPrice(Double.MAX_VALUE)
                    .withName("Rye Bread 500 g")
                    .build();
            Receipt receipt = ReceiptTestBuilder
                    .receipt()
                    .withReceiptProducts(List.of(receiptProductOne, receiptProductTwo))
                    .withDiscount(3.0)
                    .build();
            Double expectedTotal = 9.223372036854776E16;

            Double actualTotal = receipt.calculateTotal();

            assertThat(actualTotal).isEqualTo(expectedTotal);
        }

        @Test
        void checkCalculateTotalShouldReturnTotalWhenMaxQtyAndPrice() {
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
            Receipt receipt = ReceiptTestBuilder
                    .receipt()
                    .withReceiptProducts(List.of(receiptProductOne, receiptProductTwo))
                    .withDiscount(3.0)
                    .build();
            Double expectedTotal = 9.223372036854776E16;

            Double actualTotal = receipt.calculateTotal();

            assertThat(actualTotal).isEqualTo(expectedTotal);
        }

        @Test
        void checkCalculateUnDiscountedTotalShouldReturnUnDiscountedTotalWhenMaxQty() {
            ReceiptProduct receiptProductOne = ReceiptProductTestBuilder
                    .receiptProduct()
                    .withQty(Integer.MAX_VALUE)
                    .withPrice(1000.0)
                    .withName("Milk 3%")
                    .build();
            ReceiptProduct receiptProductTwo = ReceiptProductTestBuilder
                    .receiptProduct()
                    .withQty(Integer.MAX_VALUE)
                    .withPrice(1000.0)
                    .withName("Rye Bread 500 g")
                    .build();
            Receipt receipt = ReceiptTestBuilder
                    .receipt()
                    .withReceiptProducts(List.of(receiptProductOne, receiptProductTwo))
                    .withDiscount(3.0)
                    .build();
            Double expectedTotal = 4.294967294E12;

            Double actualTotal = receipt.calculateUnDiscountedTotal();

            assertThat(actualTotal).isEqualTo(expectedTotal);
        }

        @Test
        void checkCalculateUnDiscountedTotalShouldReturnUnDiscountedTotalWhenMaxPrice() {
            ReceiptProduct receiptProductOne = ReceiptProductTestBuilder
                    .receiptProduct()
                    .withQty(1000)
                    .withPrice(Double.MAX_VALUE)
                    .withName("Milk 3%")
                    .build();
            ReceiptProduct receiptProductTwo = ReceiptProductTestBuilder
                    .receiptProduct()
                    .withQty(1000)
                    .withPrice(Double.MAX_VALUE)
                    .withName("Rye Bread 500 g")
                    .build();
            Receipt receipt = ReceiptTestBuilder
                    .receipt()
                    .withReceiptProducts(List.of(receiptProductOne, receiptProductTwo))
                    .withDiscount(3.0)
                    .build();

            Double actualTotal = receipt.calculateUnDiscountedTotal();

            assertThat(actualTotal).isInfinite();
        }

        @Test
        void checkCalculateUnDiscountedTotalShouldReturnUnDiscountedTotalWhenMaxQtyAndPrice() {
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
            Receipt receipt = ReceiptTestBuilder
                    .receipt()
                    .withReceiptProducts(List.of(receiptProductOne, receiptProductTwo))
                    .withDiscount(3.0)
                    .build();

            Double actualTotal = receipt.calculateUnDiscountedTotal();

            assertThat(actualTotal).isInfinite();
        }
    }
}
