package com.shopping.cart.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductMapperTest {

    private ProductMapper productMapper;

    @BeforeEach
    public void setUp() {
        productMapper = new ProductMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(productMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productMapper.fromId(null)).isNull();
    }
}
