package by.kantsevich.receiptrunner.service;

import by.kantsevich.receiptrunner.exception.ProductAlreadyExistsException;
import by.kantsevich.receiptrunner.exception.ProductNotFoundException;
import by.kantsevich.receiptrunner.model.entity.Product;

import java.util.List;

/**
 * Interface for performing operations with {@link Product}
 *
 * @author Ruslan Kantsevich
 */
public interface ProductService {
    /**
     * Returns a list of all products in the database
     *
     * @return the list of all products or an empty list if there are none in the database
     */
    List<Product> findAll();

    /**
     * Returns a product by id or throws a {@link ProductNotFoundException}
     * if the product with the given id is not found in the database
     *
     * @param id the product id
     * @return the product with given id
     * @throws ProductNotFoundException if the product with the given id is not found in the database
     */
    Product findById(Long id);

    /**
     * Saves the product in the database or throws a {@link ProductAlreadyExistsException} if a product with
     * the given id already exists
     *
     * @param product the product to save
     * @throws ProductAlreadyExistsException if a product with the given id already exists in the database
     */
    void save(Product product);

    /**
     * Updates the product with the given id in the database or throws a {@link ProductNotFoundException}
     * if the product with the given id is not found
     *
     * @param id      the id of the updated product
     * @param product the product with data to update an existing product
     * @return the updated product
     * @throws ProductNotFoundException if the product with the given id is not found in the database
     */
    Product update(Long id, Product product);

    /**
     * Deletes the product with the given id from the database or throws a {@link ProductNotFoundException}
     * if the product with the given id is not found
     *
     * @param id the id of the product to be deleted
     * @throws ProductNotFoundException if the product with the given id is not found in the database
     */
    void deleteById(Long id);
}
