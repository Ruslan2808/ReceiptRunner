package by.kantsevich.receiptrunner.model;

import by.kantsevich.receiptrunner.model.product.ReceiptProduct;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.*;

public class Receipt {

    private LocalDate creationDate;
    private LocalTime creationTime;
    private List<ReceiptProduct> receiptProducts;
    private Double discount;

    public Receipt(LocalDate creationDate,
                   LocalTime creationTime,
                   List<ReceiptProduct> receiptProducts,
                   Double discount) {
        this.creationDate = creationDate;
        this.creationTime = creationTime;
        this.receiptProducts = receiptProducts;
        this.discount = discount;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalTime getCreationTime() {
        return creationTime;
    }

    public List<ReceiptProduct> getReceiptProducts() {
        return receiptProducts;
    }

    public Double getDiscount() {
        return discount;
    }

    public Double calculateUnDiscountedTotal() {
        return receiptProducts.stream()
                .map(ReceiptProduct::calculateTotal)
                .reduce(Double::sum)
                .orElse(0.0);
    }

    public Double calculateTotal() {
        return calculateUnDiscountedTotal() * (1 - this.discount / 100);
    }

}
