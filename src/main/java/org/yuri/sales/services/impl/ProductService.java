package org.yuri.sales.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.yuri.sales.dtos.SaveProductDto;
import org.yuri.sales.entities.Product;
import org.yuri.sales.exceptions.NotFoundException;
import org.yuri.sales.repositories.IProductRepository;
import org.yuri.sales.services.IProductService;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService implements IProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final IProductRepository productRepository;

    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAll() {
        logger.info("Fetching all Products from database.");
        List<Product> products = productRepository.findAll();
        logger.debug("Fetched {} products.", products.size());
        return products;
    }

    @Override
    public Product getById(UUID id) throws NotFoundException {
        logger.info("Attempting to fetch Product with Id: {}", id);
        Product product = productRepository.findById(id).orElseThrow(() -> {
            logger.warn("Product with Id {} not found.", id);
            return new NotFoundException("Product not found");
        });
        logger.info("Successfully fetched Product with Id: {}", id);
        return product;
    }

    @Override
    public Product create(SaveProductDto saveProductDto) {
        logger.info("Creating a new Product.");
        Product product = toProduct(saveProductDto);
        Product savedProduct = productRepository.save(product);
        logger.info("Successfully created Product with Id: {}", savedProduct.getId());
        return savedProduct;
    }

    @Override
    public Product update(UUID id, SaveProductDto saveProductDto) throws NotFoundException {
        logger.info("Attempting to update Product with Id: {}", id);
        Product productDb = getById(id);
        Product product = toProduct(saveProductDto);
        product.setId(id);
        product.setCreatedAt(productDb.getCreatedAt());
        Product updatedProduct = productRepository.save(product);
        logger.info("Successfully updated Product with Id: {}", id);
        return updatedProduct;
    }

    @Override
    public void delete(UUID id) throws NotFoundException {
        logger.info("Attempting to delete Product with Id: {}", id);
        Product product = getById(id);
        productRepository.delete(product);
        logger.info("Successfully deleted Product with Id: {}", id);
    }

    private Product toProduct(SaveProductDto saveProductDto) {
        logger.debug("Converting SaveProductDto to Product entity.");
        return Product.builder()
                .name(saveProductDto.getName())
                .description(saveProductDto.getDescription())
                .category(saveProductDto.getCategory())
                .build();
    }
}
