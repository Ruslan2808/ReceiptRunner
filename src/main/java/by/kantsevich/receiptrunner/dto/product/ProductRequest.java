package by.kantsevich.receiptrunner.dto.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductRequest {

    @NotBlank(message = "Name must not be empty")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9 .%-]*$", message = "Name must start with a letter and can only contain letters, numbers, %, . and -")
    private String name;
    @NotNull(message = "Price must not be empty")
    @DecimalMin(value = "0.01", message = "Price must be greater than or equal to 0.01")
    private Double price;
    @NotNull(message = "Promotional must not be empty")
    private Boolean isPromotional;

}
