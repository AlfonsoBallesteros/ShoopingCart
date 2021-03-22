package com.shopping.cart.web.rest;

import com.shopping.cart.ShoppingCartApp;
import com.shopping.cart.domain.Order;
import com.shopping.cart.repository.OrderRepository;
import com.shopping.cart.service.OrderService;
import com.shopping.cart.service.dto.OrderDTO;
import com.shopping.cart.service.mapper.OrderMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OrderResource} REST controller.
 */
@SpringBootTest(classes = ShoppingCartApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OrderResourceIT {

    private static final Integer DEFAULT_COUNT = 1;
    private static final Integer UPDATED_COUNT = 2;

    private static final Integer DEFAULT_SUB_TOTAL = 1;
    private static final Integer UPDATED_SUB_TOTAL = 2;

    private static final Integer DEFAULT_TOTAL = 1;
    private static final Integer UPDATED_TOTAL = 2;

    private static final Boolean DEFAULT_STATE = false;
    private static final Boolean UPDATED_STATE = true;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MockMvc restOrderMockMvc;

    private Order order;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Order createEntity() {
        Order order = new Order()
            .count(DEFAULT_COUNT)
            .subTotal(DEFAULT_SUB_TOTAL)
            .total(DEFAULT_TOTAL)
            .state(DEFAULT_STATE);
        return order;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Order createUpdatedEntity() {
        Order order = new Order()
            .count(UPDATED_COUNT)
            .subTotal(UPDATED_SUB_TOTAL)
            .total(UPDATED_TOTAL)
            .state(UPDATED_STATE);
        return order;
    }

    @BeforeEach
    public void initTest() {
        orderRepository.deleteAll();
        order = createEntity();
    }

    @Test
    public void createOrder() throws Exception {
        int databaseSizeBeforeCreate = orderRepository.findAll().size();
        // Create the Order
        OrderDTO orderDTO = orderMapper.toDto(order);
        restOrderMockMvc.perform(post("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderDTO)))
            .andExpect(status().isCreated());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeCreate + 1);
        Order testOrder = orderList.get(orderList.size() - 1);
        assertThat(testOrder.getCount()).isEqualTo(DEFAULT_COUNT);
        assertThat(testOrder.getSubTotal()).isEqualTo(DEFAULT_SUB_TOTAL);
        assertThat(testOrder.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testOrder.isState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    public void createOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderRepository.findAll().size();

        // Create the Order with an existing ID
        order.setId("existing_id");
        OrderDTO orderDTO = orderMapper.toDto(order);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderMockMvc.perform(post("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllOrders() throws Exception {
        // Initialize the database
        orderRepository.save(order);

        // Get all the orderList
        restOrderMockMvc.perform(get("/api/orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(order.getId())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)))
            .andExpect(jsonPath("$.[*].subTotal").value(hasItem(DEFAULT_SUB_TOTAL)))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.booleanValue())));
    }
    
    @Test
    public void getOrder() throws Exception {
        // Initialize the database
        orderRepository.save(order);

        // Get the order
        restOrderMockMvc.perform(get("/api/orders/{id}", order.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(order.getId()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT))
            .andExpect(jsonPath("$.subTotal").value(DEFAULT_SUB_TOTAL))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.booleanValue()));
    }
    @Test
    public void getNonExistingOrder() throws Exception {
        // Get the order
        restOrderMockMvc.perform(get("/api/orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateOrder() throws Exception {
        // Initialize the database
        orderRepository.save(order);

        int databaseSizeBeforeUpdate = orderRepository.findAll().size();

        // Update the order
        Order updatedOrder = orderRepository.findById(order.getId()).get();
        updatedOrder
            .count(UPDATED_COUNT)
            .subTotal(UPDATED_SUB_TOTAL)
            .total(UPDATED_TOTAL)
            .state(UPDATED_STATE);
        OrderDTO orderDTO = orderMapper.toDto(updatedOrder);

        restOrderMockMvc.perform(put("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderDTO)))
            .andExpect(status().isOk());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
        Order testOrder = orderList.get(orderList.size() - 1);
        assertThat(testOrder.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testOrder.getSubTotal()).isEqualTo(UPDATED_SUB_TOTAL);
        assertThat(testOrder.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testOrder.isState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    public void updateNonExistingOrder() throws Exception {
        int databaseSizeBeforeUpdate = orderRepository.findAll().size();

        // Create the Order
        OrderDTO orderDTO = orderMapper.toDto(order);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderMockMvc.perform(put("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteOrder() throws Exception {
        // Initialize the database
        orderRepository.save(order);

        int databaseSizeBeforeDelete = orderRepository.findAll().size();

        // Delete the order
        restOrderMockMvc.perform(delete("/api/orders/{id}", order.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
