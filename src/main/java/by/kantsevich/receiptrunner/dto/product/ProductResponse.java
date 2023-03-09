package by.kantsevich.receiptrunner.dto.product;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class ProductResponse {

    private Long id;
    private String name;
    private Double price;
    private Boolean isPromotional;

}
