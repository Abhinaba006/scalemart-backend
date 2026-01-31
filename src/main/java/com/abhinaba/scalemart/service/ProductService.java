package com.abhinaba.scalemart.service;

import com.abhinaba.scalemart.exception.ProductNotFoundException;
import com.abhinaba.scalemart.model.Product;
import com.abhinaba.scalemart.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    // 2. Delete by ID
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product updateProduct(Long id, Product newProductData) {
        // 1. Check if it exists first
        Product existingProduct = getProductById(id); // Re-use our method (throws 404 if missing)

        // 2. Update fields
        existingProduct.setName(newProductData.getName());
        existingProduct.setPrice(newProductData.getPrice());

        // 3. Save (Spring detects the ID exists and performs an UPDATE instead of INSERT)
        return productRepository.save(existingProduct);
    }
}