package by.kantsevich.receiptrunner.service;

import by.kantsevich.receiptrunner.dto.product.ProductRequest;
import by.kantsevich.receiptrunner.dto.product.ProductResponse;
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
     * Returns a list of all products of type in the database
     *
     * @return the list of all products of type {@link ProductResponse} or an empty list if there are none
     * in the database
     */
    List<ProductResponse> findAll();

    /**
     * Returns a product by id or throws a {@link ProductNotFoundException}
     * if the product with the given id is not found in the database
     *
     * @param id the product id
     * @return the product of type {@link ProductResponse} with given id
     * @throws ProductNotFoundException if the product with the given id is not found in the database
     */
    ProductResponse findById(Long id);

    /**
     * Saves the product in the database
     *
     * @param productRequest the product of type {@link ProductRequest} to save
     * @return the saved product of type {@link ProductResponse}
     */
    ProductResponse save(ProductRequest productRequest);

    /**
     * Updates the product with the given id in the database or throws a {@link ProductNotFoundException}
     * if the product with the given id is not found
     *
     * @param id             the id of the updated product
     * @param productRequest the product of type {@link ProductRequest} with data to update an existing product
     * @return the updated product of type {@link ProductResponse}
     * @throws ProductNotFoundException if the product with the given id is not found in the database
     */
    ProductResponse update(Long id, ProductRequest productRequest);

    /**
     * Deletes the product with the given id from the database or throws a {@link ProductNotFoundException}
     * if the product with the given id is not found
     *
     * @param id the id of the product to be deleted
     * @throws ProductNotFoundException if the product with the given id is not found in the database
     */
    void deleteById(Long id);
}
