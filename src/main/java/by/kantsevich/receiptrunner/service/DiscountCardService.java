package by.kantsevich.receiptrunner.service;

import by.kantsevich.receiptrunner.exception.DiscountCardAlreadyExistsException;
import by.kantsevich.receiptrunner.exception.DiscountCardNotFoundException;
import by.kantsevich.receiptrunner.model.entity.DiscountCard;

import java.util.List;

/**
 * Interface for performing operations with {@link DiscountCard}
 *
 * @author Ruslan Kantsevich
 */
public interface DiscountCardService {
    /**
     * Returns a list of all discount cards in the database
     *
     * @return the list of all discount cards or an empty list if there are none in the database
     */
    List<DiscountCard> findAll();

    /**
     * Returns a discount card by id or throws a {@link DiscountCardNotFoundException}
     * if the discount card with the given id is not found in the database
     *
     * @param id the discount card id
     * @return the discount card with given id
     * @throws DiscountCardNotFoundException if the discount card with the given id is not found in the database
     */
    DiscountCard findById(Long id);

    /**
     * Saves the discount card in the database or throws a {@link DiscountCardAlreadyExistsException}
     * if a discount card with the given id or number already exists
     *
     * @param discountCard the discount card to save
     * @throws DiscountCardAlreadyExistsException if a discount card with the given id or number already exists
     *                                            in the database
     */
    void save(DiscountCard discountCard);

    /**
     * Updates the discount card with the given id in the database or throws a {@link DiscountCardNotFoundException}
     * if the discount card with the given id is not found or throws a {@link DiscountCardAlreadyExistsException}
     * if the discount card with the given number already exists
     *
     * @param id           the id of the updated discount card
     * @param discountCard the discount card with data to update an existing discount card
     * @return the updated discount card
     * @throws DiscountCardNotFoundException      if the discount card with the given id is not found in the database
     * @throws DiscountCardAlreadyExistsException if the discount card with the given number already exists in the database
     */
    DiscountCard update(Long id, DiscountCard discountCard);

    /**
     * Deletes the discount card with the given id from the database or throws a {@link DiscountCardNotFoundException}
     * if the discount card with the given id is not found
     *
     * @param id the id of the discount card to be deleted
     * @throws DiscountCardNotFoundException if the discount card with the given id is not found in the database
     */
    void deleteById(Long id);
}
