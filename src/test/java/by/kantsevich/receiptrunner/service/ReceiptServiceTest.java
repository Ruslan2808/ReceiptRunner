package by.kantsevich.receiptrunner.service;

import by.kantsevich.receiptrunner.exception.DiscountCardNotFoundException;
import by.kantsevich.receiptrunner.exception.ProductNotFoundException;
import by.kantsevich.receiptrunner.model.Receipt;
import by.kantsevich.receiptrunner.model.entity.DiscountCard;
import by.kantsevich.receiptrunner.model.entity.Product;
import by.kantsevich.receiptrunner.model.product.ReceiptProduct;
import by.kantsevich.receiptrunner.repository.DiscountCardRepository;
import by.kantsevich.receiptrunner.repository.ProductRepository;
import by.kantsevich.receiptrunner.util.DiscountCardTestBuilder;
import by.kantsevich.receiptrunner.util.ProductTestBuilder;
import by.kantsevich.receiptrunner.util.ReceiptProductTestBuilder;
import by.kantsevich.receiptrunner.util.ReceiptTestBuilder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.temporal.ChronoUnit;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReceiptServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private DiscountCardRepository discountCardRepository;
    @InjectMocks
    private ReceiptService receiptService;

    @Test
    void checkCreateReceiptShouldReturnReceipt() {
        Map<Integer, Integer> products = new LinkedHashMap<>();
        products.put(1, 2);
        int numberDiscountCard = 1234;
        Product product = ProductTestBuilder
                .product()
                .withName("Milk 3%")
                .build();
        DiscountCard discountCard = DiscountCardTestBuilder
                .discountCard()
                .build();
        ReceiptProduct receiptProduct = ReceiptProductTestBuilder
                .receiptProduct()
                .withQty(2)
                .withName(product.getName())
                .build();
        Receipt expectedReceipt = ReceiptTestBuilder
                .receipt()
                .withReceiptProducts(List.of(receiptProduct))
                .withDiscount(discountCard.getDiscount())
                .build();

        doReturn(Optional.of(product)).when(productRepository).findById(product.getId());
        doReturn(Optional.of(discountCard)).when(discountCardRepository).findByNumber(discountCard.getNumber());

        Receipt actualReceipt = receiptService.createReceipt(products, numberDiscountCard);

        assertAll(
                () -> assertThat(actualReceipt.getCreationDate()).isEqualTo(expectedReceipt.getCreationDate()),
                () -> assertThat(actualReceipt.getCreationTime()).isCloseTo(
                        expectedReceipt.getCreationTime(), within(1, ChronoUnit.SECONDS)
                ),
                () -> assertThat(actualReceipt.getReceiptProducts()).isEqualTo(expectedReceipt.getReceiptProducts()),
                () -> assertThat(actualReceipt.getDiscount()).isEqualTo(expectedReceipt.getDiscount())
        );
    }

    @Test
    void checkCreateReceiptShouldThrowProductNotFoundExceptionDueEmptyProducts() {
        Map<Integer, Integer> products = new LinkedHashMap<>();
        int numberDiscountCard = 1234;

        assertThatException()
                .isThrownBy(() -> receiptService.createReceipt(products, numberDiscountCard))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void checkCreateReceiptShouldThrowProductNotFoundException() {
        Map<Integer, Integer> products = new LinkedHashMap<>();
        products.put(1, 2);
        int numberDiscountCard = 1234;

        doReturn(Optional.empty()).when(productRepository).findById(1L);

        assertThatException()
                .isThrownBy(() -> receiptService.createReceipt(products, numberDiscountCard))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void checkCreateReceiptShouldThrowDiscountCardNotFoundException() {
        Map<Integer, Integer> products = new LinkedHashMap<>();
        products.put(1, 2);
        int numberDiscountCard = 1234;
        Product product = ProductTestBuilder
                .product()
                .build();

        doReturn(Optional.of(product)).when(productRepository).findById(1L);
        doReturn(Optional.empty()).when(discountCardRepository).findByNumber(numberDiscountCard);

        assertThatException()
                .isThrownBy(() -> receiptService.createReceipt(products, numberDiscountCard))
                .isInstanceOf(DiscountCardNotFoundException.class);
    }
}
