package by.kantsevich.receiptrunner.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "discount_card")
public class DiscountCard {

    @Id
    private Long id;
    private Integer number;
    private Double discount;

    public DiscountCard() {}

    public DiscountCard(Long id, Integer number, Double discount) {
        this.id = id;
        this.number = number;
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public Integer getNumber() {
        return number;
    }

    public Double getDiscount() {
        return discount;
    }

}
