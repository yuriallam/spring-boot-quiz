package org.yuri.sales.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.yuri.sales.dtos.SaveSaleDetailsDto;
import org.yuri.sales.dtos.SaveSaleDto;
import org.yuri.sales.entities.*;
import org.yuri.sales.exceptions.NotFoundException;
import org.yuri.sales.repositories.*;
import org.yuri.sales.services.ISaleDetailsService;
import org.yuri.sales.services.ISaleService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SaleDetailsService implements ISaleDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(SaleDetailsService.class);

    private final ISaleRepository saleRepository;
    private final ISaleDetailsRepository saleDetailsRepository;
    private final IProductRepository productRepository;
    private final ISellerRepository sellerRepository;

    public SaleDetailsService(
            ISaleRepository saleRepository,
            ISaleDetailsRepository saleDetailsRepository,
            IProductRepository productRepository,
            ISellerRepository sellerRepository
    ) {
        this.saleRepository = saleRepository;
        this.saleDetailsRepository = saleDetailsRepository;
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
    }

    @Override
    public List<SaleDetails> getAll() {
        logger.info("Fetching all SaleDetails from database.");
        List<SaleDetails> saleDetails = saleDetailsRepository.findAll();
        logger.debug("Fetched {} SaleDetails records.", saleDetails.size());
        return saleDetails;
    }

    @Override
    public SaleDetails getById(UUID id) throws NotFoundException {
        logger.info("Attempting to fetch SaleDetails with Id: {}", id);
        SaleDetails saleDetails = saleDetailsRepository.findById(id).orElseThrow(() -> {
            logger.warn("SaleDetails with Id {} not found.", id);
            return new NotFoundException("SaleDetails not found");
        });
        logger.info("Successfully fetched SaleDetails with Id: {}", id);
        return saleDetails;
    }

    @Override
    public SaleDetails create(SaveSaleDetailsDto saveSaleDetailsDto) throws NotFoundException {
        logger.info("Creating a new SaleDetails.");
        SaleDetails saleDetails = toSaleDetails(saveSaleDetailsDto);
        SaleDetails savedSaleDetails = saleDetailsRepository.save(saleDetails);

        UUID saleId = saveSaleDetailsDto.getSaleId();
        updateSaleTotal(saleId, calculateTotal(saveSaleDetailsDto), 0.0);

        logger.info("Successfully created SaleDetails with Id: {}", savedSaleDetails.getId());
        return savedSaleDetails;
    }

    @Override
    public SaleDetails update(UUID id, SaveSaleDetailsDto saveSaleDetailsDto) throws NotFoundException {
        logger.info("Attempting to update SaleDetails with Id: {}", id);
        SaleDetails saleDetailsDb = getById(id);
        UUID saleId = saveSaleDetailsDto.getSaleId();
        Double oldSaleDetailsTotal = saleDetailsDb.getQuantity() * saleDetailsDb.getUnitPrice();

        SaleDetails saleDetails = toSaleDetails(saveSaleDetailsDto);
        saleDetails.setId(id);
        saleDetails.setCreatedAt(saleDetailsDb.getCreatedAt());
        SaleDetails updatedSaleDetails = saleDetailsRepository.save(saleDetails);

//        Modifying the sale's total according to the saleDetails changes
        Double newSaleDetailsTotal = calculateTotal(saveSaleDetailsDto);
        updateSaleTotal(saleId, newSaleDetailsTotal, oldSaleDetailsTotal);

        logger.info("Successfully updated SaleDetails with Id: {}", id);
        return updatedSaleDetails;
    }

    private void updateSaleTotal(UUID saleId, Double newTotal, Double oldTotal) throws NotFoundException {
        logger.info("Attempting to update Sale total with Id: {}", saleId);
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new NotFoundException("Sale not found"));
        sale.setTotal(sale.getTotal() - oldTotal + newTotal);
        saleRepository.save(sale);
        logger.info("Successfully updated Sale total with Id: {}", saleId);
    }

    @Override
    public void delete(UUID id) throws NotFoundException {
        logger.info("Attempting to delete SaleDetails with Id: {}", id);
        SaleDetails saleDetails = getById(id);
        saleDetailsRepository.delete(saleDetails);
        logger.info("Successfully deleted SaleDetails with Id: {}", id);
    }

    private SaleDetails toSaleDetails(SaveSaleDetailsDto saveSaleDetailsDto) throws NotFoundException {
        logger.debug("Converting SaveSaleDetailsDto to SaleDetails entity.");
        Optional<Product> optionalProduct = productRepository.findById(saveSaleDetailsDto.getProductId());

        if (optionalProduct.isEmpty()) {
            throw new NotFoundException("Product not found");
        }

        Product product = optionalProduct.get();

        Optional<Sale> optionalSale = saleRepository.findById(saveSaleDetailsDto.getSaleId());

        if (!optionalSale.isPresent()) {
            throw new NotFoundException("Sale not found");
        }

        Sale sale = optionalSale.get();
        logger.debug("Successfully converted SaveSaleDetailsDto to SaleDetails entity.");
        return SaleDetails.builder()
                .unitPrice(saveSaleDetailsDto.getUnitPrice())
                .quantity(saveSaleDetailsDto.getQuantity())
                .product(product)
                .sale(sale)
                .build();
    }

    private Double calculateTotal(SaveSaleDetailsDto saveSaleDetailsDto) {
        return saveSaleDetailsDto.getUnitPrice() * saveSaleDetailsDto.getQuantity();
    }

}
