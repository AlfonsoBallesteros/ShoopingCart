package com.shopping.cart.service;

import com.shopping.cart.domain.Store;
import com.shopping.cart.repository.StoreRepository;
import com.shopping.cart.service.dto.StoreDTO;
import com.shopping.cart.service.mapper.StoreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Store}.
 */
@Service
public class StoreService {

    private final Logger log = LoggerFactory.getLogger(StoreService.class);

    private final StoreRepository storeRepository;

    private final StoreMapper storeMapper;

    public StoreService(StoreRepository storeRepository, StoreMapper storeMapper) {
        this.storeRepository = storeRepository;
        this.storeMapper = storeMapper;
    }

    /**
     * Save a store.
     *
     * @param storeDTO the entity to save.
     * @return the persisted entity.
     */
    public StoreDTO save(StoreDTO storeDTO) {
        log.debug("Request to save Store : {}", storeDTO);
        Store store = storeMapper.toEntity(storeDTO);
        store = storeRepository.save(store);
        return storeMapper.toDto(store);
    }

    /**
     * Get all the stores.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<StoreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Stores");
        return storeRepository.findAll(pageable)
            .map(storeMapper::toDto);
    }


    /**
     * Get one store by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<StoreDTO> findOne(String id) {
        log.debug("Request to get Store : {}", id);
        return storeRepository.findById(id)
            .map(storeMapper::toDto);
    }

    /**
     * Delete the store by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Store : {}", id);
        storeRepository.deleteById(id);
    }
}
