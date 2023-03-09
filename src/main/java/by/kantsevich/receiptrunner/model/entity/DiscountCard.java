package by.kantsevich.receiptrunner.model.entity;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "discount_card")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DiscountCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer number;
    private Double discount;

}
