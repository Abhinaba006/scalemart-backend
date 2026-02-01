package com.abhinaba.scalemart.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.abhinaba.scalemart.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.seller.username = :username")
    List<Product> findProductsBySellerUsername(@Param("username") String email);
}