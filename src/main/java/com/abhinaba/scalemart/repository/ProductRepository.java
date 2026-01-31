package com.abhinaba.scalemart.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.abhinaba.scalemart.model.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Empty!
    // Magic: Spring Data JPA automatically writes the SQL for you.
    // .save(), .findAll(), .findById(), .delete() exist automatically.
}