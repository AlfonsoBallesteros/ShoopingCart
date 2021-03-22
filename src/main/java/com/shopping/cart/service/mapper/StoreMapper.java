package com.shopping.cart.service.mapper;


import com.shopping.cart.domain.*;
import com.shopping.cart.service.dto.StoreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Store} and its DTO {@link StoreDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface StoreMapper extends EntityMapper<StoreDTO, Store> {

    @Mapping(source = "product.id", target = "productId")
    StoreDTO toDto(Store store);

    @Mapping(source = "productId", target = "product")
    Store toEntity(StoreDTO storeDTO);

    default Store fromId(String id) {
        if (id == null) {
            return null;
        }
        Store store = new Store();
        store.setId(id);
        return store;
    }
}
