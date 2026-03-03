package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;

import java.util.List;

public interface ProductWriterService {
    Product create(Product product);
    void deleteProductById(String productId);
    Product update(Product product);
}
