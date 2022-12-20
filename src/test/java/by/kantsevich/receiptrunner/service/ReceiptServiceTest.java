package by.kantsevich.receiptrunner.service;

import by.kantsevich.receiptrunner.model.Receipt;
import by.kantsevich.receiptrunner.model.entity.DiscountCard;
import by.kantsevich.receiptrunner.model.entity.Product;
import by.kantsevich.receiptrunner.repository.DiscountCardRepository;
import by.kantsevich.receiptrunner.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class ReceiptServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private DiscountCardRepository discountCardRepository;
    @InjectMocks
    private ReceiptService receiptService;

    private List<Product> products;
    private Map<Integer, Integer> purchaseData;
    private DiscountCard discountCard;

    @BeforeEach
    void setUp() {
        products = List.of(
                new Product(1L, "Milk 3%", 1.5, true),
                new Product(2L, "Rye Bread 500 g", 1.25, false),
                new Product(3L, "Sausage 450g", 8.54, false)
        );

        purchaseData = new LinkedHashMap<>(Map.of(
                1, 2,
                2, 3,
                3, 4
        ));

        discountCard = new DiscountCard(1L, 1234, 3.0);
    }

    @Test
    void givenExistPurchaseData_whenCreateReceipt_thenReturnNotNullReceipt() {
        for (Product product : products) {
            when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        }
        when(discountCardRepository.findByNumber(discountCard.getNumber())).thenReturn(Optional.of(discountCard));

        Receipt receipt = receiptService.createReceipt(purchaseData, discountCard.getNumber());

        assertAll(
                () -> assertNotNull(receipt),
                () -> assertEquals(receipt.getReceiptProducts().size(), 3)
        );
    }

}