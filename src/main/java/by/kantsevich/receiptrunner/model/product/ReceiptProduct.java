package by.kantsevich.receiptrunner.model.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReceiptProduct {

    public final static double DISCOUNT_PROMOTIONAL_PRODUCT = 10.0;

    private Integer qty;
    private String name;
    private Double price;
    private Boolean isPromotional;

    public Double calculateTotal() {
        if (isPromotional && qty > 5) {
            return price * qty * (1 - DISCOUNT_PROMOTIONAL_PRODUCT / 100);
        }

        return price * qty;
    }

}
