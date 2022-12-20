package by.kantsevich.receiptrunner.service;

import by.kantsevich.receiptrunner.model.entity.DiscountCard;
import by.kantsevich.receiptrunner.model.entity.Product;
import by.kantsevich.receiptrunner.model.product.builder.ReceiptProductBuilder;
import by.kantsevich.receiptrunner.exception.DiscountCardNotFound;
import by.kantsevich.receiptrunner.exception.ProductNotFoundException;
import by.kantsevich.receiptrunner.model.Receipt;
import by.kantsevich.receiptrunner.model.product.ReceiptProduct;
import by.kantsevich.receiptrunner.repository.DiscountCardRepository;
import by.kantsevich.receiptrunner.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReceiptService {

    private final ProductRepository productRepository;
    private final DiscountCardRepository discountCardRepository;

    @Autowired
    public ReceiptService(ProductRepository productRepository,
                          DiscountCardRepository discountCardRepository) {
        this.productRepository = productRepository;
        this.discountCardRepository = discountCardRepository;
    }

    public Receipt createReceipt(Map<Integer, Integer> products,
                                 Integer numberDiscountCard) throws ProductNotFoundException, DiscountCardNotFound {
        List<ReceiptProduct> receiptProducts = new ArrayList<>();
        double discount = 0.0;

        if (products.isEmpty()) {
            throw new ProductNotFoundException("It's impossible to generate a receipt because the list of products is empty");
        }

        for (Integer productId : products.keySet()) {
            Product product = productRepository.findById(Long.valueOf(productId))
                    .orElseThrow(() -> new ProductNotFoundException(String.format("Product with id = %d not found", productId)));

            ReceiptProduct receiptProduct = new ReceiptProductBuilder()
                    .setQty(products.get(productId))
                    .setName(product.getName())
                    .setPromotional(product.getPromotional())
                    .setPrice(product.getPrice())
                    .build();

            receiptProducts.add(receiptProduct);
        }

        if (numberDiscountCard != 0) {
            DiscountCard discountCard = discountCardRepository.findByNumber(numberDiscountCard)
                    .orElseThrow(() -> new DiscountCardNotFound(String.format("Discount card with number = %d not found", numberDiscountCard)));

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
