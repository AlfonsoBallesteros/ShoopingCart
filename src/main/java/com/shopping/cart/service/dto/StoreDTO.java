package com.shopping.cart.service.dto;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link com.shopping.cart.domain.Store} entity.
 */
public class StoreDTO extends AbstractAuditingDTO implements Serializable {
    
    private String id;

    private Instant date;

    private Integer quantity;

    private String move;


    private String productId;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StoreDTO)) {
            return false;
        }

        return id != null && id.equals(((StoreDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StoreDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", quantity=" + getQuantity() +
            ", move='" + getMove() + "'" +
            ", productId='" + getProductId() + "'" +
            "}";
    }
}
