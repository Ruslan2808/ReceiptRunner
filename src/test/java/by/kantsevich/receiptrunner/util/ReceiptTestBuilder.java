package by.kantsevich.receiptrunner.util;

import by.kantsevich.receiptrunner.model.Receipt;
import by.kantsevich.receiptrunner.model.product.ReceiptProduct;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(staticName = "receipt")
@With
public class ReceiptTestBuilder implements TestBuilder<Receipt> {

    private LocalDate creationDate = LocalDate.now();
    private LocalTime creationTime = LocalTime.now();
    private List<ReceiptProduct> receiptProducts = new ArrayList<>();
    private Double discount = 0.0;

    @Override
    public Receipt build() {
        final var receipt = new Receipt();

        receipt.setCreationDate(creationDate);
        receipt.setCreationTime(creationTime);
        receipt.setReceiptProducts(receiptProducts);
        receipt.setDiscount(discount);

        return receipt;
    }
}
