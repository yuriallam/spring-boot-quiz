package org.yuri.sales.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yuri.sales.dtos.SaveSellerDto;
import org.yuri.sales.entities.Seller;
import org.yuri.sales.exceptions.NotFoundException;
import org.yuri.sales.services.ISellerService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sellers")
public class SellerController {
    private final ISellerService sellerService;

    public SellerController(
            ISellerService sellerService
    ) {
        this.sellerService = sellerService;
    }

    @GetMapping(value = "",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Seller>> getAll() {
        return ResponseEntity.ok(sellerService.getAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Seller> getById(@PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok(sellerService.getById(id));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Seller> create(@RequestBody SaveSellerDto saveSellerDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sellerService.create(saveSellerDto));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Seller> update(@PathVariable UUID id, @RequestBody SaveSellerDto saveSellerDto) throws NotFoundException {
        return ResponseEntity.ok(sellerService.update(id, saveSellerDto));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws NotFoundException {
        sellerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
