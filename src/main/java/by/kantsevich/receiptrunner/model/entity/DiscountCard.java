package by.kantsevich.receiptrunner.model.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "discount_card")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DiscountCard {

    @Id
    private Long id;
    private Integer number;
    private Double discount;

}
