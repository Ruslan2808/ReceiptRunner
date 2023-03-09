package by.kantsevich.receiptrunner.dto.discount_card;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DiscountCardRequest {

    @NotNull(message = "Number must not be empty")
    @Min(value = 1, message = "Discount card number must be greater than or equal to 1")
    private Integer number;
    @NotNull(message = "Discount must not be empty")
    @DecimalMin(value = "0.01", message = "Discount must be greater than or equal to 0.01")
    @DecimalMax(value = "100.00", message = "Discount must be less than or equal to 100.00")
    private Double discount;

}
