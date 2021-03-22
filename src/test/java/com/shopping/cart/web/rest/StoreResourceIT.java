package com.shopping.cart.web.rest;

import com.shopping.cart.ShoppingCartApp;
import com.shopping.cart.domain.Store;
import com.shopping.cart.repository.StoreRepository;
import com.shopping.cart.service.StoreService;
import com.shopping.cart.service.dto.StoreDTO;
import com.shopping.cart.service.mapper.StoreMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link StoreResource} REST controller.
 */
@SpringBootTest(classes = ShoppingCartApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StoreResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final String DEFAULT_MOVE = "AAAAAAAAAA";
    private static final String UPDATED_MOVE = "BBBBBBBBBB";

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private StoreService storeService;

    @Autowired
    private MockMvc restStoreMockMvc;

    private Store store;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Store createEntity() {
        Store store = new Store()
            .date(DEFAULT_DATE)
            .quantity(DEFAULT_QUANTITY)
            .move(DEFAULT_MOVE);
        return store;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Store createUpdatedEntity() {
        Store store = new Store()
            .date(UPDATED_DATE)
            .quantity(UPDATED_QUANTITY)
            .move(UPDATED_MOVE);
        return store;
    }

    @BeforeEach
    public void initTest() {
        storeRepository.deleteAll();
        store = createEntity();
    }

    @Test
    public void createStore() throws Exception {
        int databaseSizeBeforeCreate = storeRepository.findAll().size();
        // Create the Store
        StoreDTO storeDTO = storeMapper.toDto(store);
        restStoreMockMvc.perform(post("/api/stores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isCreated());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeCreate + 1);
        Store testStore = storeList.get(storeList.size() - 1);
        assertThat(testStore.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testStore.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testStore.getMove()).isEqualTo(DEFAULT_MOVE);
    }

    @Test
    public void createStoreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = storeRepository.findAll().size();

        // Create the Store with an existing ID
        store.setId("existing_id");
        StoreDTO storeDTO = storeMapper.toDto(store);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStoreMockMvc.perform(post("/api/stores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllStores() throws Exception {
        // Initialize the database
        storeRepository.save(store);

        // Get all the storeList
        restStoreMockMvc.perform(get("/api/stores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(store.getId())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].move").value(hasItem(DEFAULT_MOVE)));
    }
    
    @Test
    public void getStore() throws Exception {
        // Initialize the database
        storeRepository.save(store);

        // Get the store
        restStoreMockMvc.perform(get("/api/stores/{id}", store.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(store.getId()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.move").value(DEFAULT_MOVE));
    }
    @Test
    public void getNonExistingStore() throws Exception {
        // Get the store
        restStoreMockMvc.perform(get("/api/stores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateStore() throws Exception {
        // Initialize the database
        storeRepository.save(store);

        int databaseSizeBeforeUpdate = storeRepository.findAll().size();

        // Update the store
        Store updatedStore = storeRepository.findById(store.getId()).get();
        updatedStore
            .date(UPDATED_DATE)
            .quantity(UPDATED_QUANTITY)
            .move(UPDATED_MOVE);
        StoreDTO storeDTO = storeMapper.toDto(updatedStore);

        restStoreMockMvc.perform(put("/api/stores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isOk());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeUpdate);
        Store testStore = storeList.get(storeList.size() - 1);
        assertThat(testStore.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testStore.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testStore.getMove()).isEqualTo(UPDATED_MOVE);
    }

    @Test
    public void updateNonExistingStore() throws Exception {
        int databaseSizeBeforeUpdate = storeRepository.findAll().size();

        // Create the Store
        StoreDTO storeDTO = storeMapper.toDto(store);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStoreMockMvc.perform(put("/api/stores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteStore() throws Exception {
        // Initialize the database
        storeRepository.save(store);

        int databaseSizeBeforeDelete = storeRepository.findAll().size();

        // Delete the store
        restStoreMockMvc.perform(delete("/api/stores/{id}", store.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
