package org.yuri.sales.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.yuri.sales.dtos.SaveSellerDto;
import org.yuri.sales.entities.Seller;
import org.yuri.sales.exceptions.NotFoundException;
import org.yuri.sales.repositories.ISellerRepository;
import org.yuri.sales.services.ISellerService;

import java.util.List;
import java.util.UUID;

@Service
public class SellerService implements ISellerService {
    private static final Logger logger = LoggerFactory.getLogger(SellerService.class);
    private final ISellerRepository sellerRepository;

    public SellerService(
            ISellerRepository sellerRepository
    ) {
        this.sellerRepository = sellerRepository;
    }

    @Override
    public List<Seller> getAll() {
        logger.info("Fetching all Sellers from the database.");
        List<Seller> sellers = sellerRepository.findAll();
        logger.debug("Fetched {} Sellers records.", sellers.size());
        return sellers;
    }

    @Override
    public Seller getById(UUID id) throws NotFoundException {
        logger.info("Attempting to fetch Seller with Id: {}", id);
        Seller seller = sellerRepository.findById(id).orElseThrow(() -> {
            logger.warn("Seller with Id {} not found.", id);
            return new NotFoundException("Seller not found");
        });
        logger.info("Successfully fetched Seller with Id: {}", id);
        return seller;
    }

    @Override
    public Seller create(SaveSellerDto saveSellerDto) {
        logger.info("Creating a new Seller.");
        Seller seller = toSeller(saveSellerDto);
        Seller savedSeller = sellerRepository.save(seller);
        logger.info("Successfully created Seller with Id: {}", savedSeller.getId());
        return savedSeller;
    }

    @Override
    public Seller update(UUID id, SaveSellerDto saveSellerDto) throws NotFoundException {
        logger.info("Attempting to update Seller with Id: {}", id);
        getById(id);
        Seller seller = toSeller(saveSellerDto);
        seller.setId(id);
        Seller updatedSeller = sellerRepository.save(seller);
        logger.info("Successfully updated Seller with Id: {}", id);
        return updatedSeller;
    }

    @Override
    public void delete(UUID id) throws NotFoundException {
        logger.info("Attempting to delete Seller with Id: {}", id);
        Seller seller = getById(id);
        sellerRepository.delete(seller);
        logger.info("Successfully deleted Seller with Id: {}", id);
    }

    private Seller toSeller(SaveSellerDto saveSellerDto) {
        logger.debug("Converting SaveSellerDto to Seller entity.");
        return Seller.builder()
                .firstName(saveSellerDto.getFirstName())
                .lastName(saveSellerDto.getLastName())
                .mobile(saveSellerDto.getMobile())
                .build();
    }

}
