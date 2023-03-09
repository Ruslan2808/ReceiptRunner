package by.kantsevich.receiptrunner.service.impl;

import by.kantsevich.receiptrunner.exception.ProductAlreadyExistsException;
import by.kantsevich.receiptrunner.exception.ProductNotFoundException;
import by.kantsevich.receiptrunner.model.entity.Product;
import by.kantsevich.receiptrunner.repository.ProductRepository;
import by.kantsevich.receiptrunner.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * An implementation of the {@link ProductService} interface for performing operations with {@link Product}
 *
 * @author Ruslan Kantsevich
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @return {@inheritDoc}
     * @throws ProductNotFoundException {@inheritDoc}
     */
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(String.format("Product with id = %d not found", id)));
    }

    /**
     * {@inheritDoc}
     *
     * @param product {@inheritDoc}
     * @return {@inheritDoc}
     * @throws ProductAlreadyExistsException {@inheritDoc}
     */
    public void save(Product product) {
        productRepository.findById(product.getId())
                .ifPresent(p -> {
                    throw new ProductAlreadyExistsException(String.format("Product with id = %d already exists", product.getId()));
                });

        productRepository.save(product);
    }

    /**
     * {@inheritDoc}
     *
     * @param id      {@inheritDoc}
     * @param product {@inheritDoc}
     * @return {@inheritDoc}
     * @throws ProductNotFoundException {@inheritDoc}
     */
    public Product update(Long id, Product product) {
        Product updateProduct = findById(id);

        updateProduct.setName(product.getName());
        updateProduct.setPrice(product.getPrice());
        updateProduct.setIsPromotional(product.getIsPromotional());

        return productRepository.save(updateProduct);
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @throws ProductNotFoundException {@inheritDoc}
     */
    public void deleteById(Long id) {
        findById(id);

        productRepository.deleteById(id);
    }
}
