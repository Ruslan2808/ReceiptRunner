package by.kantsevich.receiptrunner.model.product.builder;

import by.kantsevich.receiptrunner.model.product.ReceiptProduct;

public interface ReceiptProductBuild {
    ReceiptProductBuild setQty(Integer qty);
    ReceiptProductBuild setName(String name);
    ReceiptProductBuild setPromotional(Boolean isPromotional);
    ReceiptProductBuild setPrice(Double price);
    ReceiptProduct build();
}
