package org.yuri.sales.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yuri.sales.dtos.SaveSaleDto;
import org.yuri.sales.entities.Sale;
import org.yuri.sales.exceptions.NotFoundException;
import org.yuri.sales.services.ISaleService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sales")
public class SaleController {
    private final ISaleService saleService;

    public SaleController(
            ISaleService saleService
    ) {
        this.saleService = saleService;
    }

    @GetMapping(value = "",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Sale>> getAll() {
        return ResponseEntity.ok(saleService.getAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Sale> getById(@PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok(saleService.getById(id));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Sale> create(@RequestBody SaveSaleDto saveSaleDto) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(saleService.create(saveSaleDto));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Sale> update(@PathVariable UUID id, @RequestBody SaveSaleDto saveSaleDto) throws NotFoundException {
        return ResponseEntity.ok(saleService.update(id, saveSaleDto));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws NotFoundException {
        saleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
