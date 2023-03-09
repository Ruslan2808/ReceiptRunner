package by.kantsevich.receiptrunner.mapper;

import by.kantsevich.receiptrunner.dto.product.ProductRequest;
import by.kantsevich.receiptrunner.dto.product.ProductResponse;
import by.kantsevich.receiptrunner.model.entity.Product;

import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .isPromotional(product.getIsPromotional())
                .build();
    }

    public Product mapToProduct(ProductRequest productRequest) {
        return Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .isPromotional(productRequest.getIsPromotional())
                .build();
    }
}
