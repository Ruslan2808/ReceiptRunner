package by.kantsevich.receiptrunner.mapper;

import by.kantsevich.receiptrunner.dto.discount_card.DiscountCardRequest;
import by.kantsevich.receiptrunner.dto.discount_card.DiscountCardResponse;
import by.kantsevich.receiptrunner.model.entity.DiscountCard;

import org.springframework.stereotype.Component;

@Component
public class DiscountCardMapper {

    public DiscountCardResponse mapToDiscountCardResponse(DiscountCard discountCard) {
        return DiscountCardResponse.builder()
                .id(discountCard.getId())
                .number(discountCard.getNumber())
                .discount(discountCard.getDiscount())
                .build();
    }

    public DiscountCard mapToDiscountCard(DiscountCardRequest discountCardRequest) {
        return DiscountCard.builder()
                .number(discountCardRequest.getNumber())
                .discount(discountCardRequest.getDiscount())
                .build();
    }
}
