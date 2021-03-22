package com.shopping.cart.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.shopping.cart.domain.Order} entity.
 */
public class OrderDTO extends AbstractAuditingDTO implements Serializable {
    
    private String id;

    private Integer count;

    private Integer subTotal;

    private Integer total;

    private Boolean state;


    private String productId;

    private String saleId;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Boolean isState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDTO)) {
            return false;
        }

        return id != null && id.equals(((OrderDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderDTO{" +
            "id=" + getId() +
            ", count=" + getCount() +
            ", subTotal=" + getSubTotal() +
            ", total=" + getTotal() +
            ", state='" + isState() + "'" +
            ", productId='" + getProductId() + "'" +
            ", saleId='" + getSaleId() + "'" +
            "}";
    }
}
