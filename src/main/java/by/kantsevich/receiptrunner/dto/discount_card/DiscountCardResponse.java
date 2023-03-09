package by.kantsevich.receiptrunner.dto.discount_card;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class DiscountCardResponse {

    private Long id;
    private Integer number;
    private Double discount;

}
