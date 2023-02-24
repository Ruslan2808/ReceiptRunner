package by.kantsevich.receiptrunner.util;

import by.kantsevich.receiptrunner.model.entity.Product;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "product")
@With
public class ProductTestBuilder implements TestBuilder<Product> {

    private Long id = 1L;
    private String name = "";
    private Double price = 1.0;
    private Boolean isPromotional = false;

    @Override
    public Product build() {
        final var product = new Product();

        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        product.setIsPromotional(isPromotional);

        return product;
    }
}
