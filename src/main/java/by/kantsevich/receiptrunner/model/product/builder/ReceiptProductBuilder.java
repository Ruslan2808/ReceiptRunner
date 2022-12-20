package by.kantsevich.receiptrunner.model.product.builder;

import by.kantsevich.receiptrunner.model.product.ReceiptProduct;

public class ReceiptProductBuilder implements ReceiptProductBuild{

    ReceiptProduct receiptProduct = new ReceiptProduct();

    @Override
    public ReceiptProductBuild setQty(Integer qty) {
        this.receiptProduct.setQty(qty);
        return this;
    }

    @Override
    public ReceiptProductBuild setName(String name) {
        this.receiptProduct.setName(name);
        return this;
    }

    @Override
    public ReceiptProductBuild setPromotional(Boolean isPromotional) {
        this.receiptProduct.setPromotional(isPromotional);
        return this;
    }

    @Override
    public ReceiptProductBuild setPrice(Double price) {
        this.receiptProduct.setPrice(price);
        return this;
    }

    @Override
    public ReceiptProduct build() {
        return this.receiptProduct;
    }

}
