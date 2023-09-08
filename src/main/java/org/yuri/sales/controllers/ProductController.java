package org.yuri.sales.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yuri.sales.dtos.SaveProductDto;
import org.yuri.sales.entities.Product;
import org.yuri.sales.exceptions.NotFoundException;
import org.yuri.sales.services.IProductService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final IProductService productService;

    public ProductController(
            IProductService productService
    ) {
        this.productService = productService;
    }

    @GetMapping(value = "",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getById(@PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok(productService.getById(id));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> create(@RequestBody SaveProductDto saveProductDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(saveProductDto));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> update(@PathVariable UUID id, @RequestBody SaveProductDto saveProductDto) throws NotFoundException {
        return ResponseEntity.ok(productService.update(id, saveProductDto));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws NotFoundException {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
