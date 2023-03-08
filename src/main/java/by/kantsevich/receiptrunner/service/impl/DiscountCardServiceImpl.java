package by.kantsevich.receiptrunner.service.impl;

import by.kantsevich.receiptrunner.exception.DiscountCardAlreadyExistsException;
import by.kantsevich.receiptrunner.exception.DiscountCardNotFoundException;
import by.kantsevich.receiptrunner.model.entity.DiscountCard;
import by.kantsevich.receiptrunner.repository.DiscountCardRepository;
import by.kantsevich.receiptrunner.service.DiscountCardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountCardServiceImpl implements DiscountCardService {

    private final DiscountCardRepository discountCardRepository;

    @Autowired
    public DiscountCardServiceImpl(DiscountCardRepository discountCardRepository) {
        this.discountCardRepository = discountCardRepository;
    }

    public List<DiscountCard> findAll() {
        return discountCardRepository.findAll();
    }

    public DiscountCard findById(Long id) {
        return discountCardRepository.findById(id)
                .orElseThrow(() -> new DiscountCardNotFoundException(String.format("Discount card with id = %d not found", id)));
    }

    public void save(DiscountCard discountCard) {
        discountCardRepository.findById(discountCard.getId())
                .ifPresent(p -> {
                    throw new DiscountCardAlreadyExistsException(String.format("Discount card with id = %d already exists", discountCard.getId()));
                });
        discountCardRepository.findByNumber(discountCard.getNumber())
                .ifPresent(p -> {
                    throw new DiscountCardAlreadyExistsException(String.format("Discount card with number = %d already exists", discountCard.getNumber()));
                });

        discountCardRepository.save(discountCard);
    }

    public DiscountCard update(Long id, DiscountCard discountCard) {
        DiscountCard updateDiscountCard = findById(id);

        discountCardRepository.findByNumber(discountCard.getNumber())
                .ifPresent(p -> {
                    throw new DiscountCardAlreadyExistsException(String.format("Discount card with number = %d already exists", discountCard.getNumber()));
                });

        updateDiscountCard.setNumber(discountCard.getNumber());
        updateDiscountCard.setDiscount(discountCard.getDiscount());

        return discountCardRepository.save(updateDiscountCard);
    }

    public void deleteById(Long id) {
        findById(id);

        discountCardRepository.deleteById(id);
    }
}
