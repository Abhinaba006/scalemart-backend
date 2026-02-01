package com.abhinaba.scalemart.config;

import com.abhinaba.scalemart.model.Product;
import com.abhinaba.scalemart.repository.ProductRepository;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.data.RepositoryItemWriter;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.infrastructure.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;

@Configuration
public class BatchConfig {

    private final ProductRepository productRepository;
    PlatformTransactionManager TransactionManager;


    public BatchConfig(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 1. READER: Reads the CSV file
    @Bean
    public FlatFileItemReader<Product> reader() {
        return new FlatFileItemReaderBuilder<Product>()
                .name("productItemReader")
                .resource(new ClassPathResource("products.csv")) // Finds file in resources
                .delimited()
                .names("name", "price", "description") // Matches CSV header names
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(Product.class); // Converts row to Product object
                }})
                .linesToSkip(1) // Skip the header row
                .build();
    }

    // 2. PROCESSOR: Optional logic (e.g., make names Uppercase)
    @Bean
    public ItemProcessor<Product, Product> processor() {
        return product -> {
            // Let's add a tag so we know it came from Batch
            product.setDescription("BATCH IMPORTED: " + product.getDescription());
            return product;
        };
    }

    // 3. WRITER: Saves to Oracle Database
    @Bean
    public RepositoryItemWriter<Product> writer() {
        RepositoryItemWriter<Product> writer = new RepositoryItemWriter<>(productRepository);
        writer.setMethodName("save"); // Calls productRepository.save()
        return writer;
    }

    // 4. STEP: The Worker (Reader -> Processor -> Writer)
    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("csv-import-step", jobRepository)
                .<Product, Product>chunk(10)  // Remove transactionManager parameter
                .reader(reader())
                .processor(processor())  // Optional
                .writer(writer())
                .transactionManager(transactionManager)  // Set it separately
                .build();
    }

    // 5. JOB: The Manager (Executes the Step)
    @Bean
    public Job importUserJob(JobRepository jobRepository, Step step1) {
        return new JobBuilder("importUserJob", jobRepository)
                .start(step1)
                .build();
    }
}