package com.shopping.cart.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SaleMapperTest {

    private SaleMapper saleMapper;

    @BeforeEach
    public void setUp() {
        saleMapper = new SaleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(saleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(saleMapper.fromId(null)).isNull();
    }
}
