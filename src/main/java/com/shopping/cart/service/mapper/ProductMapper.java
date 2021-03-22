package com.shopping.cart.service.mapper;


import com.shopping.cart.domain.*;
import com.shopping.cart.service.dto.ProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {



    default Product fromId(String id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
