package by.kantsevich.receiptrunner.service;

import by.kantsevich.receiptrunner.model.entity.DiscountCard;

import java.util.List;

public interface DiscountCardService {
    List<DiscountCard> findAll();

    DiscountCard findById(Long id);

    void save(DiscountCard discountCard);

    DiscountCard update(Long id, DiscountCard discountCard);

    void deleteById(Long id);
}
