package by.kantsevich.receiptrunner.mapper;

import by.kantsevich.receiptrunner.model.Receipt;
import by.kantsevich.receiptrunner.model.product.ReceiptProduct;
import by.kantsevich.receiptrunner.model.product.builder.ReceiptProductBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TextReceiptMapperTest {

    private ReceiptMapper<String> textReceiptMapper;
    private Receipt receipt;

    @BeforeEach
    void setUp() {
        textReceiptMapper = new TextReceiptMapper();

        ReceiptProduct milk = new ReceiptProductBuilder()
                .setQty(6)
                .setName("Milk 3%")
                .setPrice(1.5)
                .setPromotional(true)
                .build();

        receipt = new Receipt(
                LocalDate.of(2022, 12, 20),
                LocalTime.of(16, 34, 21),
                List.of(milk),
                3.0
        );
    }

    @Test
    void givenReceipt_whenMapReceiptToText_thenReturnNotEmptyTextReceipt() {
        String textReceipt = textReceiptMapper.map(receipt);

        assertFalse(textReceipt.isEmpty());
    }

}