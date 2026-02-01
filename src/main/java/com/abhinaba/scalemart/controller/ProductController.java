package com.abhinaba.scalemart.controller;

import com.abhinaba.scalemart.model.Product;
import com.abhinaba.scalemart.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.batch.core.job.Job;

import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    // --- BATCH VARIABLES ---
    private final JobOperator jobOperator;
    private final Job job;

    // Constructor Injection for Service + Batch Components
    public ProductController(ProductService productService, JobOperator jobOperator, Job job) {
        this.productService = productService;
        this.jobOperator = jobOperator;
        this.job = job;
    }


    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public Product createProduct(@Valid @RequestBody Product product) {
        return productService.addProduct(product);
    }

    // GET /products/1
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // DELETE /products/1
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @PostMapping("/batch/import")
    public String importCsvToDb() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("startAt", System.currentTimeMillis())
                    .toJobParameters();

            jobOperator.start(job, jobParameters);

            return "Batch Job Started Successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Job Failed: " + e.getMessage();
        }
    }

}