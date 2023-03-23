package by.kantsevich.receiptrunner.service.impl;

import by.kantsevich.receiptrunner.exception.DiscountCardNotFoundException;
import by.kantsevich.receiptrunner.exception.ProductNotFoundException;
import by.kantsevich.receiptrunner.model.Receipt;
import by.kantsevich.receiptrunner.model.ReceiptProduct;
import by.kantsevich.receiptrunner.repository.DiscountCardRepository;
import by.kantsevich.receiptrunner.repository.ProductRepository;
import by.kantsevich.receiptrunner.service.ReceiptService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    private final ProductRepository productRepository;
    private final DiscountCardRepository discountCardRepository;

    @Autowired
    public ReceiptServiceImpl(ProductRepository productRepository,
                              DiscountCardRepository discountCardRepository) {
        this.productRepository = productRepository;
        this.discountCardRepository = discountCardRepository;
    }

    public Receipt createReceipt(Map<Integer, Integer> products, Integer numberDiscountCard) {
        List<ReceiptProduct> receiptProducts = new ArrayList<>();
        double discount = 0.0;

        if (products.isEmpty()) {
            throw new ProductNotFoundException("It's impossible to generate a receipt because the list of products is empty");
        }

        for (Integer productId : products.keySet()) {
            var product = productRepository.findById(Long.valueOf(productId))
                    .orElseThrow(() -> new ProductNotFoundException(String.format("Product with id = %d not found", productId)));

            var receiptProduct = ReceiptProduct.builder()
                    .qty(products.get(productId))
                    .name(product.getName())
                    .isPromotional(product.getIsPromotional())
                    .price(product.getPrice())
                    .build();

            receiptProducts.add(receiptProduct);
        }

        if (numberDiscountCard != 0) {
            var discountCard = discountCardRepository.findByNumber(numberDiscountCard)
                    .orElseThrow(() -> new DiscountCardNotFoundException(String.format("Discount card with number = %d not found", numberDiscountCard)));

            discount = discountCard.getDiscount();
        }

        return new Receipt(
                LocalDate.now(),
                LocalTime.now(),
                receiptProducts,
                discount
        );
    }
}
