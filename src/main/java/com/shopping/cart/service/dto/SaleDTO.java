package com.shopping.cart.service.dto;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link com.shopping.cart.domain.Sale} entity.
 */
public class SaleDTO extends AbstractAuditingDTO implements Serializable {
    
    private String id;

    private String code;

    private Instant date;

    private String medioPago;

    private Integer subTotal;

    private Integer total;

    private Boolean state;

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SaleDTO)) {
            return false;
        }

        return id != null && id.equals(((SaleDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaleDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", date='" + getDate() + "'" +
            ", medioPago='" + getMedioPago() + "'" +
            ", subTotal=" + getSubTotal() +
            ", total=" + getTotal() +
            ", state='" + isState() + "'" +
            "}";
    }
}
