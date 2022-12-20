package by.kantsevich.receiptrunner.mapper;

import by.kantsevich.receiptrunner.model.Receipt;

public interface ReceiptMapper<T> {
    T map(Receipt receipt);
}
