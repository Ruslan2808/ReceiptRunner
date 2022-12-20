package by.kantsevich.receiptrunner.repository;

import by.kantsevich.receiptrunner.model.entity.Product;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void givenExistProductId_whenFindProductById_thanReturnNotNullProduct() {
        Long productId = 1L;

        Optional<Product> product = productRepository.findById(productId);

        assertNotNull(product.get());
    }

}