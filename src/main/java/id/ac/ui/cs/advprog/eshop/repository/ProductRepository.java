package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product){
        if (product.getProductId() == null) {
            product.setProductId(UUID.randomUUID().toString());
        }
        productData.add(product);
        return product;
    }

    public Product findById(String productId){
        for (Product p : productData) {
            if (p.getProductId().equals(productId)) return p;
        }
        return null;
    }

    public Product edit(Product product) {
        Product existingProduct = findById(product.getProductId());
        if (existingProduct != null) {
            existingProduct.setProductName(product.getProductName());
            existingProduct.setProductQuantity(product.getProductQuantity());
            return existingProduct;
        }
        return null;
    }

    public void delete(String productId) {
        productData.removeIf(p -> p.getProductId().equals(productId));
    }

    public Iterator<Product> findAll(){
        return productData.iterator();
    }
}
