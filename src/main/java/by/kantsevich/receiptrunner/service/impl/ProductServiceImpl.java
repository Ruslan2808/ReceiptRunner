package by.kantsevich.receiptrunner.service.impl;

import by.kantsevich.receiptrunner.dto.product.ProductRequest;
import by.kantsevich.receiptrunner.dto.product.ProductResponse;
import by.kantsevich.receiptrunner.exception.ProductNotFoundException;
import by.kantsevich.receiptrunner.mapper.ProductMapper;
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
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::mapToProductResponse)
                .toList();
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @return {@inheritDoc}
     * @throws ProductNotFoundException {@inheritDoc}
     */
    public ProductResponse findById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::mapToProductResponse)
                .orElseThrow(() -> new ProductNotFoundException(String.format("Product with id = %d not found", id)));
    }

    /**
     * {@inheritDoc}
     *
     * @param productRequest {@inheritDoc}
     * @return {@inheritDoc}
     */
    public ProductResponse save(ProductRequest productRequest) {
        Product product = productMapper.mapToProduct(productRequest);

        Product saveProduct = productRepository.save(product);

        return productMapper.mapToProductResponse(saveProduct);
    }

    /**
     * {@inheritDoc}
     *
     * @param id             {@inheritDoc}
     * @param productRequest {@inheritDoc}
     * @return {@inheritDoc}
     * @throws ProductNotFoundException {@inheritDoc}
     */
    public ProductResponse update(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(String.format("Product with id = %d not found", id)));

        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setIsPromotional(productRequest.getIsPromotional());

        Product updateProduct = productRepository.save(product);

        return productMapper.mapToProductResponse(updateProduct);
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
