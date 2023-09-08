package org.yuri.sales.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yuri.sales.dtos.SaveClientDto;
import org.yuri.sales.entities.Client;
import org.yuri.sales.exceptions.NotFoundException;
import org.yuri.sales.services.IClientService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final IClientService clientService;

    public ClientController(
            IClientService clientService
    ) {
        this.clientService = clientService;
    }

    @GetMapping(value = "",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Client>> getAll() {
        return ResponseEntity.ok(clientService.getAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> getById(@PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok(clientService.getById(id));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> create(@RequestBody SaveClientDto saveClientDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.create(saveClientDto));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> update(@PathVariable UUID id, @RequestBody SaveClientDto saveClientDto) throws NotFoundException {
        return ResponseEntity.ok(clientService.update(id, saveClientDto));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws NotFoundException {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
