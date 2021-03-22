package com.shopping.cart.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StoreMapperTest {

    private StoreMapper storeMapper;

    @BeforeEach
    public void setUp() {
        storeMapper = new StoreMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(storeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(storeMapper.fromId(null)).isNull();
    }
}
