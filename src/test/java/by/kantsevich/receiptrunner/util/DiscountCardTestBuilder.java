package by.kantsevich.receiptrunner.util;

import by.kantsevich.receiptrunner.model.entity.DiscountCard;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "discountCard")
@With
public class DiscountCardTestBuilder implements TestBuilder<DiscountCard> {

    private Long id = 1L;
    private Integer number = 1234;
    private Double discount = 1.0;

    @Override
    public DiscountCard build() {
        final var discountCard = new DiscountCard();

        discountCard.setId(id);
        discountCard.setNumber(number);
        discountCard.setDiscount(discount);

        return discountCard;
    }
}
