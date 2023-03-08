package by.kantsevich.receiptrunner.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.*;

@Entity
@Table(name = "discount_card")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class DiscountCard {

    @Id
    private Long id;
    @NotNull(message = "Number must not be empty")
    @Min(value = 1, message = "Discount card number must be greater than or equal to 1")
    private Integer number;
    @NotNull(message = "Discount must not be empty")
    @DecimalMin(value = "0.01", message = "Discount must be greater than or equal to 0.01")
    @DecimalMax(value = "100.00", message = "Discount must be less than or equal to 100.00")
    private Double discount;

}
