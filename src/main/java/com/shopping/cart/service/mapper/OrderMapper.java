package com.shopping.cart.service.mapper;


import com.shopping.cart.domain.*;
import com.shopping.cart.service.dto.OrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Order} and its DTO {@link OrderDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class, SaleMapper.class})
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "sale.id", target = "saleId")
    OrderDTO toDto(Order order);

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "saleId", target = "sale")
    Order toEntity(OrderDTO orderDTO);

    default Order fromId(String id) {
        if (id == null) {
            return null;
        }
        Order order = new Order();
        order.setId(id);
        return order;
    }
}
