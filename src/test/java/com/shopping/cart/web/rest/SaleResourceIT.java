package com.shopping.cart.web.rest;

import com.shopping.cart.ShoppingCartApp;
import com.shopping.cart.domain.Sale;
import com.shopping.cart.repository.SaleRepository;
import com.shopping.cart.service.SaleService;
import com.shopping.cart.service.dto.SaleDTO;
import com.shopping.cart.service.mapper.SaleMapper;

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
 * Integration tests for the {@link SaleResource} REST controller.
 */
@SpringBootTest(classes = ShoppingCartApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SaleResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MEDIO_PAGO = "AAAAAAAAAA";
    private static final String UPDATED_MEDIO_PAGO = "BBBBBBBBBB";

    private static final Integer DEFAULT_SUB_TOTAL = 1;
    private static final Integer UPDATED_SUB_TOTAL = 2;

    private static final Integer DEFAULT_TOTAL = 1;
    private static final Integer UPDATED_TOTAL = 2;

    private static final Boolean DEFAULT_STATE = false;
    private static final Boolean UPDATED_STATE = true;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private SaleMapper saleMapper;

    @Autowired
    private SaleService saleService;

    @Autowired
    private MockMvc restSaleMockMvc;

    private Sale sale;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sale createEntity() {
        Sale sale = new Sale()
            .code(DEFAULT_CODE)
            .date(DEFAULT_DATE)
            .medioPago(DEFAULT_MEDIO_PAGO)
            .subTotal(DEFAULT_SUB_TOTAL)
            .total(DEFAULT_TOTAL)
            .state(DEFAULT_STATE);
        return sale;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sale createUpdatedEntity() {
        Sale sale = new Sale()
            .code(UPDATED_CODE)
            .date(UPDATED_DATE)
            .medioPago(UPDATED_MEDIO_PAGO)
            .subTotal(UPDATED_SUB_TOTAL)
            .total(UPDATED_TOTAL)
            .state(UPDATED_STATE);
        return sale;
    }

    @BeforeEach
    public void initTest() {
        saleRepository.deleteAll();
        sale = createEntity();
    }

    @Test
    public void createSale() throws Exception {
        int databaseSizeBeforeCreate = saleRepository.findAll().size();
        // Create the Sale
        SaleDTO saleDTO = saleMapper.toDto(sale);
        restSaleMockMvc.perform(post("/api/sales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(saleDTO)))
            .andExpect(status().isCreated());

        // Validate the Sale in the database
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeCreate + 1);
        Sale testSale = saleList.get(saleList.size() - 1);
        assertThat(testSale.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSale.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testSale.getMedioPago()).isEqualTo(DEFAULT_MEDIO_PAGO);
        assertThat(testSale.getSubTotal()).isEqualTo(DEFAULT_SUB_TOTAL);
        assertThat(testSale.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testSale.isState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    public void createSaleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = saleRepository.findAll().size();

        // Create the Sale with an existing ID
        sale.setId("existing_id");
        SaleDTO saleDTO = saleMapper.toDto(sale);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaleMockMvc.perform(post("/api/sales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(saleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sale in the database
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllSales() throws Exception {
        // Initialize the database
        saleRepository.save(sale);

        // Get all the saleList
        restSaleMockMvc.perform(get("/api/sales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sale.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].medioPago").value(hasItem(DEFAULT_MEDIO_PAGO)))
            .andExpect(jsonPath("$.[*].subTotal").value(hasItem(DEFAULT_SUB_TOTAL)))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.booleanValue())));
    }
    
    @Test
    public void getSale() throws Exception {
        // Initialize the database
        saleRepository.save(sale);

        // Get the sale
        restSaleMockMvc.perform(get("/api/sales/{id}", sale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sale.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.medioPago").value(DEFAULT_MEDIO_PAGO))
            .andExpect(jsonPath("$.subTotal").value(DEFAULT_SUB_TOTAL))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.booleanValue()));
    }
    @Test
    public void getNonExistingSale() throws Exception {
        // Get the sale
        restSaleMockMvc.perform(get("/api/sales/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSale() throws Exception {
        // Initialize the database
        saleRepository.save(sale);

        int databaseSizeBeforeUpdate = saleRepository.findAll().size();

        // Update the sale
        Sale updatedSale = saleRepository.findById(sale.getId()).get();
        updatedSale
            .code(UPDATED_CODE)
            .date(UPDATED_DATE)
            .medioPago(UPDATED_MEDIO_PAGO)
            .subTotal(UPDATED_SUB_TOTAL)
            .total(UPDATED_TOTAL)
            .state(UPDATED_STATE);
        SaleDTO saleDTO = saleMapper.toDto(updatedSale);

        restSaleMockMvc.perform(put("/api/sales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(saleDTO)))
            .andExpect(status().isOk());

        // Validate the Sale in the database
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeUpdate);
        Sale testSale = saleList.get(saleList.size() - 1);
        assertThat(testSale.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSale.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSale.getMedioPago()).isEqualTo(UPDATED_MEDIO_PAGO);
        assertThat(testSale.getSubTotal()).isEqualTo(UPDATED_SUB_TOTAL);
        assertThat(testSale.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testSale.isState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    public void updateNonExistingSale() throws Exception {
        int databaseSizeBeforeUpdate = saleRepository.findAll().size();

        // Create the Sale
        SaleDTO saleDTO = saleMapper.toDto(sale);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaleMockMvc.perform(put("/api/sales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(saleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sale in the database
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteSale() throws Exception {
        // Initialize the database
        saleRepository.save(sale);

        int databaseSizeBeforeDelete = saleRepository.findAll().size();

        // Delete the sale
        restSaleMockMvc.perform(delete("/api/sales/{id}", sale.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
