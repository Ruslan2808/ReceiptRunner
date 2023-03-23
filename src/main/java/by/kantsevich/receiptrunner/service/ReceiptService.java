package by.kantsevich.receiptrunner.service;

import by.kantsevich.receiptrunner.model.Receipt;

import java.util.Map;

public interface ReceiptService {
    Receipt createReceipt(Map<Integer, Integer> products, Integer numberDiscountCard);
}
