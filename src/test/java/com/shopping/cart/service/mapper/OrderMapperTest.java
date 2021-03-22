package com.shopping.cart.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderMapperTest {

    private OrderMapper orderMapper;

    @BeforeEach
    public void setUp() {
        orderMapper = new OrderMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(orderMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(orderMapper.fromId(null)).isNull();
    }
}
