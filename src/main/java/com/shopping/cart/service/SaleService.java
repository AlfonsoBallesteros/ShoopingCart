package com.shopping.cart.service;

import com.shopping.cart.domain.Sale;
import com.shopping.cart.repository.SaleRepository;
import com.shopping.cart.service.dto.SaleDTO;
import com.shopping.cart.service.mapper.SaleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Sale}.
 */
@Service
public class SaleService {

    private final Logger log = LoggerFactory.getLogger(SaleService.class);

    private final SaleRepository saleRepository;

    private final SaleMapper saleMapper;

    public SaleService(SaleRepository saleRepository, SaleMapper saleMapper) {
        this.saleRepository = saleRepository;
        this.saleMapper = saleMapper;
    }

    /**
     * Save a sale.
     *
     * @param saleDTO the entity to save.
     * @return the persisted entity.
     */
    public SaleDTO save(SaleDTO saleDTO) {
        log.debug("Request to save Sale : {}", saleDTO);
        Sale sale = saleMapper.toEntity(saleDTO);
        sale = saleRepository.save(sale);
        return saleMapper.toDto(sale);
    }

    /**
     * Get all the sales.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<SaleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sales");
        return saleRepository.findAll(pageable)
            .map(saleMapper::toDto);
    }


    /**
     * Get one sale by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<SaleDTO> findOne(String id) {
        log.debug("Request to get Sale : {}", id);
        return saleRepository.findById(id)
            .map(saleMapper::toDto);
    }

    /**
     * Delete the sale by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Sale : {}", id);
        saleRepository.deleteById(id);
    }
}
