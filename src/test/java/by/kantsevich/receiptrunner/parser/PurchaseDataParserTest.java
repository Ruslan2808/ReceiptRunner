package by.kantsevich.receiptrunner.parser;

import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PurchaseDataParserTest {

    @Nested
    class CommandLinePurchaseDataParserTest {
        private String[] args;

        @BeforeEach()
        void setUpCommandLinePurchaseData() {
            args = new String[] {"1-2", "2-3", "3-4", "card-1234"};
        }

        @Test
        void givenCorrectCommandLinePurchaseData_whenGetProducts_thenReturnProducts() {
            Map<Integer, Integer> products = PurchaseDataParser.getProducts(args);

            assertAll(
                    () -> assertEquals(products.get(1), 2),
                    () -> assertEquals(products.get(2), 3),
                    () -> assertEquals(products.get(3), 4)
            );
        }

        @Test
        void givenCorrectCommandLinePurchaseData_whenGetNumberDiscountCard_thenReturnNumberDiscountCard() {
            Integer numberDiscountCard = PurchaseDataParser.getNumberDiscountCard(args);

            assertEquals(numberDiscountCard, 1234);
        }

    }

    @Nested
    class UrlPurchaseDataParserTest {

        private List<Integer> productsId;
        private List<Integer> productsQty;

        @BeforeEach
        void setUpUrlPurchaseData() {
            productsId = List.of(1, 2 ,3);
            productsQty = List.of(2, 3 ,4);
        }

        @Test
        void givenCorrectUrlPurchaseData_whenGetProducts_thenReturnProducts() {
            Map<Integer, Integer> products = PurchaseDataParser.getProducts(productsId, productsQty);

            assertAll(
                    () -> assertEquals(products.get(1), 2),
                    () -> assertEquals(products.get(2), 3),
                    () -> assertEquals(products.get(3), 4)
            );
        }

    }

}