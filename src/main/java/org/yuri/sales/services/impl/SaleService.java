package org.yuri.sales.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.yuri.sales.dtos.SaveProductDto;
import org.yuri.sales.dtos.SaveSaleDto;
import org.yuri.sales.entities.Client;
import org.yuri.sales.entities.Product;
import org.yuri.sales.entities.Sale;
import org.yuri.sales.entities.Seller;
import org.yuri.sales.exceptions.NotFoundException;
import org.yuri.sales.repositories.IClientRepository;
import org.yuri.sales.repositories.IProductRepository;
import org.yuri.sales.repositories.ISaleRepository;
import org.yuri.sales.repositories.ISellerRepository;
import org.yuri.sales.services.IProductService;
import org.yuri.sales.services.ISaleService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SaleService implements ISaleService {

    private static final Logger logger = LoggerFactory.getLogger(SaleService.class);
    private final ISaleRepository saleRepository;
    private final IClientRepository clientRepository;
    private final ISellerRepository sellerRepository;

    public SaleService(
            ISaleRepository saleRepository,
            IClientRepository clientRepository,
            ISellerRepository sellerRepository
    ) {
        this.saleRepository = saleRepository;
        this.clientRepository = clientRepository;
        this.sellerRepository = sellerRepository;
    }

    @Override
    public List<Sale> getAll() {
        logger.info("Fetching all Sales from the database.");
        List<Sale> sales = saleRepository.findAll();
        logger.debug("Fetched {} Sales records.", sales.size());
        return sales;
    }

    @Override
    public Sale getById(UUID id) throws NotFoundException {
        logger.info("Attempting to fetch Sale with Id: {}", id);
        Sale sale = saleRepository.findById(id).orElseThrow(() -> {
            logger.warn("Sale with Id {} not found.", id);
            return new NotFoundException("Sale not found");
        });
        logger.info("Successfully fetched Sale with Id: {}", id);
        return sale;
    }

    @Override
    public Sale create(SaveSaleDto saveSaleDto) throws NotFoundException {
        logger.info("Creating a new Sale.");
        Sale sale = toSale(saveSaleDto);
        Sale savedSale = saleRepository.save(sale);
        logger.info("Successfully created Sale with Id: {}", savedSale.getId());
        return savedSale;
    }

    @Override
    public Sale update(UUID id, SaveSaleDto saveSaleDto) throws NotFoundException {
        logger.info("Attempting to update Sale with Id: {}", id);
        Sale saleDb = getById(id);
        Sale sale = toSale(saveSaleDto);
        sale.setId(id);
        sale.setCreatedAt(saleDb.getCreatedAt());
        Sale updatedSale = saleRepository.save(sale);
        logger.info("Successfully updated Sale with Id: {}", id);
        return updatedSale;
    }

    @Override
    public void delete(UUID id) throws NotFoundException {
        logger.info("Attempting to delete Sale with Id: {}", id);
        Sale sale = getById(id);
        saleRepository.delete(sale);
        logger.info("Successfully deleted Sale with Id: {}", id);
    }

    private Sale toSale(SaveSaleDto saveSaleDto) throws NotFoundException {
        logger.debug("Converting SaveSaleDto to Sale entity.");

        Client client = clientRepository.findById(saveSaleDto.getClientId())
                .orElseThrow(() -> new NotFoundException("Client not found"));

        Seller seller = sellerRepository.findById(saveSaleDto.getSellerId())
                .orElseThrow(() -> new NotFoundException("Seller not found"));

        logger.debug("Successfully converted SaveSaleDto to Sale entity.");
        return Sale.builder()
                .client(client)
                .seller(seller)
                .total(saveSaleDto.getTotal())
                .build();
    }
}
