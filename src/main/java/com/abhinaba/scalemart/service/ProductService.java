package com.abhinaba.scalemart.service;

import com.abhinaba.scalemart.dto.ProductDTO;
import com.abhinaba.scalemart.exception.ProductNotFoundException;
import com.abhinaba.scalemart.model.Product;
import com.abhinaba.scalemart.model.Users;
import com.abhinaba.scalemart.repository.ProductRepository;
import com.abhinaba.scalemart.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product addProduct(ProductDTO productDTO) {
        Users seller = userRepository.findById(productDTO.getSellerId())
                .orElseThrow(() -> new RuntimeException("Seller not found with ID: " + productDTO.getSellerId()));

        if (!"SELLER".equals(seller.getRole().name())) {
            throw new RuntimeException("Only Sellers can add products!");
        }

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setSeller(seller);

        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Cannot delete. Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    public Product updateProduct(Long id, @Valid ProductDTO productDTO) {
        Product existingProduct = getProductById(id);

        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());

        return productRepository.save(existingProduct);
    }

    public List<Product> getProductsBySellerUsername(String email) {
        return productRepository.findProductsBySellerUsername(email);
    }
}