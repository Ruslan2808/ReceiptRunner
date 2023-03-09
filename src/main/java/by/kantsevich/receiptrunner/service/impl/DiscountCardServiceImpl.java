package by.kantsevich.receiptrunner.service.impl;

import by.kantsevich.receiptrunner.dto.discount_card.DiscountCardRequest;
import by.kantsevich.receiptrunner.dto.discount_card.DiscountCardResponse;
import by.kantsevich.receiptrunner.exception.DiscountCardAlreadyExistsException;
import by.kantsevich.receiptrunner.exception.DiscountCardNotFoundException;
import by.kantsevich.receiptrunner.mapper.DiscountCardMapper;
import by.kantsevich.receiptrunner.model.entity.DiscountCard;
import by.kantsevich.receiptrunner.repository.DiscountCardRepository;
import by.kantsevich.receiptrunner.service.DiscountCardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * An implementation of the {@link DiscountCardService} interface for performing operations with {@link DiscountCard}
 *
 * @author Ruslan Kantsevich
 */
@Service
public class DiscountCardServiceImpl implements DiscountCardService {

    private final DiscountCardRepository discountCardRepository;
    private final DiscountCardMapper discountCardMapper;

    @Autowired
    public DiscountCardServiceImpl(DiscountCardRepository discountCardRepository, DiscountCardMapper discountCardMapper) {
        this.discountCardRepository = discountCardRepository;
        this.discountCardMapper = discountCardMapper;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    public List<DiscountCardResponse> findAll() {
        return discountCardRepository.findAll()
                .stream()
                .map(discountCardMapper::mapToDiscountCardResponse)
                .toList();
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @return {@inheritDoc}
     * @throws DiscountCardNotFoundException {@inheritDoc}
     */
    public DiscountCardResponse findById(Long id) {
        return discountCardRepository.findById(id)
                .map(discountCardMapper::mapToDiscountCardResponse)
                .orElseThrow(() -> new DiscountCardNotFoundException(String.format("Discount card with id = %d not found", id)));
    }

    /**
     * {@inheritDoc}
     *
     * @param discountCardRequest {@inheritDoc}
     * @return {@inheritDoc}
     * @throws DiscountCardAlreadyExistsException {@inheritDoc}
     */
    public DiscountCardResponse save(DiscountCardRequest discountCardRequest) {
        discountCardRepository.findByNumber(discountCardRequest.getNumber())
                .ifPresent(p -> {
                    throw new DiscountCardAlreadyExistsException(String.format("Discount card with number = %d already exists", discountCardRequest.getNumber()));
                });
        DiscountCard discountCard = discountCardMapper.mapToDiscountCard(discountCardRequest);

        DiscountCard saveDiscountCard = discountCardRepository.save(discountCard);

        return discountCardMapper.mapToDiscountCardResponse(saveDiscountCard);
    }

    /**
     * {@inheritDoc}
     *
     * @param id                  {@inheritDoc}
     * @param discountCardRequest {@inheritDoc}
     * @return {@inheritDoc}
     * @throws DiscountCardNotFoundException      {@inheritDoc}
     * @throws DiscountCardAlreadyExistsException {@inheritDoc}
     */
    public DiscountCardResponse update(Long id, DiscountCardRequest discountCardRequest) {
        DiscountCard discountCard = discountCardRepository.findById(id)
                .orElseThrow(() -> new DiscountCardNotFoundException(String.format("Discount card with id = %d not found", id)));
        discountCardRepository.findByNumber(discountCardRequest.getNumber())
                .ifPresent(p -> {
                    throw new DiscountCardAlreadyExistsException(String.format("Discount card with number = %d already exists", discountCardRequest.getNumber()));
                });

        discountCard.setNumber(discountCardRequest.getNumber());
        discountCard.setDiscount(discountCardRequest.getDiscount());

        DiscountCard updateDiscountCard = discountCardRepository.save(discountCard);

        return discountCardMapper.mapToDiscountCardResponse(updateDiscountCard);
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @throws DiscountCardNotFoundException {@inheritDoc}
     */
    public void deleteById(Long id) {
        findById(id);

        discountCardRepository.deleteById(id);
    }
}
