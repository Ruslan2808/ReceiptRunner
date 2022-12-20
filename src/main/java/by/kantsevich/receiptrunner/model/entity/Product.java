package by.kantsevich.receiptrunner.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    private Long id;
    private String name;
    private Double price;
    private Boolean isPromotional;

    public Product() {}

    public Product(Long id, String name, Double price, Boolean isPromotional) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.isPromotional = isPromotional;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Boolean getPromotional() {
        return isPromotional;
    }

}
