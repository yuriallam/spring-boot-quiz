package org.yuri.sales.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yuri.sales.dtos.SaveSaleDetailsDto;
import org.yuri.sales.entities.SaleDetails;
import org.yuri.sales.exceptions.NotFoundException;
import org.yuri.sales.services.ISaleDetailsService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/saleDetails")
public class SaleDetailsController {
    private final ISaleDetailsService saleDetailsService;

    public SaleDetailsController(
            ISaleDetailsService saleDetailsService
    ) {
        this.saleDetailsService = saleDetailsService;
    }

    @GetMapping(value = "",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SaleDetails>> getAll() {
        return ResponseEntity.ok(saleDetailsService.getAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SaleDetails> getById(@PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok(saleDetailsService.getById(id));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SaleDetails> create(@RequestBody SaveSaleDetailsDto saveSaleDetailsDto) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(saleDetailsService.create(saveSaleDetailsDto));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SaleDetails> update(@PathVariable UUID id, @RequestBody SaveSaleDetailsDto saveSaleDetailsDto) throws NotFoundException {
        return ResponseEntity.ok(saleDetailsService.update(id, saveSaleDetailsDto));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws NotFoundException {
        saleDetailsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
