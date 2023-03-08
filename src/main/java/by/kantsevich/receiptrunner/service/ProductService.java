package by.kantsevich.receiptrunner.service;

import by.kantsevich.receiptrunner.model.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    Product findById(Long id);

    void save(Product product);

    Product update(Long id, Product product);

    void deleteById(Long id);
}
