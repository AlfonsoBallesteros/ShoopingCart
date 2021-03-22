package com.shopping.cart.service.mapper;


import com.shopping.cart.domain.*;
import com.shopping.cart.service.dto.SaleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sale} and its DTO {@link SaleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SaleMapper extends EntityMapper<SaleDTO, Sale> {


    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "removeOrder", ignore = true)
    Sale toEntity(SaleDTO saleDTO);

    default Sale fromId(String id) {
        if (id == null) {
            return null;
        }
        Sale sale = new Sale();
        sale.setId(id);
        return sale;
    }
}
