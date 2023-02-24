package by.kantsevich.receiptrunner.model.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {

    @Id
    private Long id;
    private String name;
    private Double price;
    private Boolean isPromotional;

}
