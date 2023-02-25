package by.kantsevich.receiptrunner.printer;

import by.kantsevich.receiptrunner.exception.ReceiptEmptyException;
import by.kantsevich.receiptrunner.mapper.TextReceiptMapper;
import by.kantsevich.receiptrunner.model.Receipt;
import by.kantsevich.receiptrunner.model.product.ReceiptProduct;
import by.kantsevich.receiptrunner.util.ReceiptProductTestBuilder;
import by.kantsevich.receiptrunner.util.ReceiptTestBuilder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FileReceiptPrinterTest {

    @Mock
    private TextReceiptMapper textReceiptMapper;
    @InjectMocks
    private FileReceiptPrinter fileReceiptPrinter;
    @Captor
    private ArgumentCaptor<Receipt> receiptCaptor;

    @Test
    void checkPrintShouldThrowReceiptEmptyException()  {
        assertThatException()
                .isThrownBy(() -> fileReceiptPrinter.print(null))
                .isInstanceOf(ReceiptEmptyException.class);
    }

    @Test
    void checkPrintShouldCallMap() throws IOException {
        fileReceiptPrinter.print(new Receipt());

        verify(textReceiptMapper).map(any(Receipt.class));
    }

    @Test
    void checkPrintShouldPassMapArgument() throws IOException {
        ReceiptProduct receiptProduct = ReceiptProductTestBuilder
                .receiptProduct()
                .withName("Milk 3%")
                .build();
        Receipt receipt = ReceiptTestBuilder
                .receipt()
                .withReceiptProducts(List.of(receiptProduct))
                .build();

        fileReceiptPrinter.print(receipt);

        verify(textReceiptMapper).map(receiptCaptor.capture());
        assertThat(receiptCaptor.getValue()).isEqualTo(receipt);
    }
}
