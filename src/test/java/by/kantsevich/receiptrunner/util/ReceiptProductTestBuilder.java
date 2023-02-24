package by.kantsevich.receiptrunner.util;

import by.kantsevich.receiptrunner.model.product.ReceiptProduct;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "receiptProduct")
@With
public class ReceiptProductTestBuilder implements TestBuilder<ReceiptProduct> {

    private Integer qty = 1;
    private String name = "";
    private Double price = 1.0;
    private Boolean isPromotional = false;

    @Override
    public ReceiptProduct build() {
        final var product = new ReceiptProduct();

        product.setQty(qty);
        product.setName(name);
        product.setPrice(price);
        product.setIsPromotional(isPromotional);

        return product;
    }
}
