package by.kantsevich.receiptrunner.parser;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PurchaseDataParserTest {

    @Nested
    class CommandLinePurchaseDataParserTest {

        @Test
        void checkGetProductsShouldReturnOrderedProducts() {
            var args = new String[] {"2-1", "1-2", "3-3", "5-4", "4-5"};

            Map<Integer, Integer> actualProducts = PurchaseDataParser.getProducts(args);

            assertAll(
                    () -> assertThat(actualProducts.keySet()).containsExactly(2, 1, 3, 5, 4),
                    () -> assertThat(actualProducts.values()).containsExactly(1, 2, 3, 4, 5)
            );
        }

        @Test
        void checkGetProductsShouldReturnOrderedSumProducts() {
            var args = new String[] {"2-1", "1-2", "3-3", "3-4", "5-4", "1-5", "4-5"};

            Map<Integer, Integer> actualProducts = PurchaseDataParser.getProducts(args);

            assertAll(
                    () -> assertThat(actualProducts.keySet()).containsExactly(2, 1, 3, 5, 4),
                    () -> assertThat(actualProducts.values()).containsExactly(1, 7, 7, 4, 5)
            );
        }

        @ParameterizedTest
        @MethodSource(value = "notValidProductStringArguments")
        void checkGetProductsShouldReturnEmptyProducts(String[] args) {
            Map<Integer, Integer> actualProducts = PurchaseDataParser.getProducts(args);

            assertThat(actualProducts).isEmpty();
        }

        @ParameterizedTest
        @MethodSource(value = "discountCardStringArguments")
        void checkGetNumberDiscountCardShouldReturn1234(String[] args) {
            int expectedNumberDiscountCard = 1234;

            Integer actualNumberDiscountCard = PurchaseDataParser.getNumberDiscountCard(args);

            assertThat(actualNumberDiscountCard).isEqualTo(expectedNumberDiscountCard);
        }

        @ParameterizedTest
        @MethodSource(value = "notValidDiscountCardStringArguments")
        void checkGetNumberDiscountCardShouldReturn0(String[] args) {
            Integer actualNumberDiscountCard = PurchaseDataParser.getNumberDiscountCard(args);

            assertThat(actualNumberDiscountCard).isZero();
        }

        private static Stream<Arguments> notValidProductStringArguments() {
            return Stream.of(
                    arguments((Object) new String[] {}),
                    arguments((Object) new String[] {"1-", "2-", "3-"}),
                    arguments((Object) new String[] {"-1", "-2", "-3"}),
                    arguments((Object) new String[] {"0-0", "0-1", "1-0"}),
                    arguments((Object) new String[] {"1-a", "2-b", "3-c"}),
                    arguments((Object) new String[] {"a-1", "b-2", "c-3"}),
                    arguments((Object) new String[] {"1*1", "2+2", "3.3"}),
                    arguments((Object) new String[] {"1", "2", "3"}),
                    arguments((Object) new String[] {"1-", "-2", "0-1", "1-0", "3-a", "b-4", "5+5", "6"})
            );
        }

        private static Stream<Arguments> discountCardStringArguments() {
            return Stream.of(
                    arguments((Object) new String[] {"1-2", "2-3", "3-4", "card-1234"}),
                    arguments((Object) new String[] {"1-2", "2-3", "3-4", "CARD-1234"}),
                    arguments((Object) new String[] {"1-2", "2-3", "3-4", "CaRd-1234"}),
                    arguments((Object) new String[] {"1-2", "2-3", "3-4", "cArD-1234"}),
                    arguments((Object) new String[] {"1-2", "2-3", "3-4", "Card-1234"}),
                    arguments((Object) new String[] {"1-2", "2-3", "3-4", "card-1234", "card-5678"})
            );
        }

        private static Stream<Arguments> notValidDiscountCardStringArguments() {
            return Stream.of(
                    arguments((Object) new String[] {"1-2", "2-3", "3-4", "card-"}),
                    arguments((Object) new String[] {"1-2", "2-3", "3-4", "-card"}),
                    arguments((Object) new String[] {"1-2", "2-3", "3-4", "card-0"}),
                    arguments((Object) new String[] {"1-2", "2-3", "3-4", "card1234"}),
                    arguments((Object) new String[] {"1-2", "2-3", "3-4", "1234-card"})
            );
        }
    }

    @Nested
    class UrlPurchaseDataParserTest {

        @Test
        void checkGetProductsShouldReturnOrderedProducts() {
            List<Integer> productsId = List.of(2, 1, 3, 5, 4);
            List<Integer> productsQty = List.of(1, 2, 3, 4, 5);

            Map<Integer, Integer> actualProducts = PurchaseDataParser.getProducts(productsId, productsQty);

            assertAll(
                    () -> assertThat(actualProducts.keySet()).containsExactly(2, 1, 3, 5, 4),
                    () -> assertThat(actualProducts.values()).containsExactly(1, 2, 3, 4, 5)
            );
        }

        @Test
        void checkGetProductsShouldReturnOrderedSumProducts() {
            List<Integer> productsId = List.of(2, 1, 3, 3, 5, 1, 4);
            List<Integer> productsQty = List.of(1, 2, 3, 4, 4, 5, 5);

            Map<Integer, Integer> actualProducts = PurchaseDataParser.getProducts(productsId, productsQty);

            assertAll(
                    () -> assertThat(actualProducts.keySet()).containsExactly(2, 1, 3, 5, 4),
                    () -> assertThat(actualProducts.values()).containsExactly(1, 7, 7, 4, 5)
            );
        }

        @ParameterizedTest
        @MethodSource("productListsArguments")
        void checkGetProductsShouldReturnSize3(List<Integer> productsId, List<Integer> productsQty) {
            int expectedSize = Math.min(productsId.size(), productsQty.size());

            Map<Integer, Integer> actualProducts = PurchaseDataParser.getProducts(productsId, productsQty);

            assertThat(actualProducts).hasSize(expectedSize);
        }

        @ParameterizedTest
        @MethodSource("notValidProductListsArguments")
        void checkGetProductsShouldReturnEmptyProducts(List<Integer> productsId, List<Integer> productsQty) {
            Map<Integer, Integer> actualProducts = PurchaseDataParser.getProducts(productsId, productsQty);

            assertThat(actualProducts).isEmpty();
        }

        private static Stream<Arguments> productListsArguments() {
            return Stream.of(
                    arguments(List.of(1, 2, 3, 4, 5), List.of(1, 2, 3)),
                    arguments(List.of(1, 2, 3), List.of(1, 2, 3, 4, 5))
            );
        }

        private static Stream<Arguments> notValidProductListsArguments() {
            return Stream.of(
                    arguments(List.of(1), List.of()),
                    arguments(List.of(), List.of(1)),
                    arguments(List.of(), List.of())
            );
        }
    }
}
