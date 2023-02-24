package by.kantsevich.receiptrunner.printer;

import by.kantsevich.receiptrunner.exception.ReceiptEmptyException;
import by.kantsevich.receiptrunner.mapper.TextReceiptMapper;
import by.kantsevich.receiptrunner.model.Receipt;
import by.kantsevich.receiptrunner.model.product.ReceiptProduct;
import by.kantsevich.receiptrunner.util.ReceiptProductTestBuilder;
import by.kantsevich.receiptrunner.util.ReceiptTestBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintStream;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsoleReceiptPrinterTest {

    @Mock
    private TextReceiptMapper textReceiptMapper;
    @InjectMocks
    private ConsoleReceiptPrinter consoleReceiptPrinter;

    @Test
    void checkPrinterThrowsReceiptEmptyException() {
        assertThatException()
                .isThrownBy(() -> consoleReceiptPrinter.print(null))
                .isInstanceOf(ReceiptEmptyException.class);
    }

    @Nested
    class ConsoleReceiptPrinterMapTest {

        @Captor
        private ArgumentCaptor<Receipt> receiptCaptor;

        @Test
        void checkPrintCallMap() {
            consoleReceiptPrinter.print(new Receipt());

            verify(textReceiptMapper).map(any(Receipt.class));
        }

        @Test
        void checkPrintPassingMapArgument() {
            ReceiptProduct receiptProduct = ReceiptProductTestBuilder
                    .receiptProduct()
                    .withName("Milk 3%")
                    .build();
            Receipt receipt = ReceiptTestBuilder
                    .receipt()
                    .withReceiptProducts(List.of(receiptProduct))
                    .build();

            consoleReceiptPrinter.print(receipt);

            verify(textReceiptMapper).map(receiptCaptor.capture());
            assertThat(receiptCaptor.getValue()).isEqualTo(receipt);
        }
    }

    @Nested
    class ConsoleReceiptPrinterPrintlnTest {

        @Mock
        private PrintStream printStream;
        @Captor
        private ArgumentCaptor<String> textReceiptCaptor;

        @BeforeEach
        void setUp() {
            System.setOut(printStream);
        }

        @Test
        void checkPrintCallPrintln() {
            when(textReceiptMapper.map(any(Receipt.class))).thenReturn(anyString());

            consoleReceiptPrinter.print(new Receipt());

            verify(printStream).println(anyString());
        }

        @Test
        void checkPrintPassingPrintlnArgument() {
            String expectedTextReceipt = "";

            when(textReceiptMapper.map(any(Receipt.class))).thenReturn(expectedTextReceipt);

            consoleReceiptPrinter.print(new Receipt());

            verify(printStream).println(textReceiptCaptor.capture());
            assertThat(textReceiptCaptor.getValue()).isEqualTo(expectedTextReceipt);
        }
    }
}
