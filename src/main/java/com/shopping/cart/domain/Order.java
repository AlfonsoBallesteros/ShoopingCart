package com.shopping.cart.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;

/**
 * A Order.
 */
@Document(collection = "order")
public class Order extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("count")
    private Integer count;

    @Field("sub_total")
    private Integer subTotal;

    @Field("total")
    private Integer total;

    @Field("state")
    private Boolean state;

    @DBRef
    @Field("product")
    @JsonIgnoreProperties(value = "orders", allowSetters = true)
    private Product product;

    @DBRef
    @Field("sale")
    @JsonIgnoreProperties(value = "orders", allowSetters = true)
    private Sale sale;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public Order count(Integer count) {
        this.count = count;
        return this;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getSubTotal() {
        return subTotal;
    }

    public Order subTotal(Integer subTotal) {
        this.subTotal = subTotal;
        return this;
    }

    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }

    public Integer getTotal() {
        return total;
    }

    public Order total(Integer total) {
        this.total = total;
        return this;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Boolean isState() {
        return state;
    }

    public Order state(Boolean state) {
        this.state = state;
        return this;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Product getProduct() {
        return product;
    }

    public Order product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Sale getSale() {
        return sale;
    }

    public Order sale(Sale sale) {
        this.sale = sale;
        return this;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return id != null && id.equals(((Order) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", count=" + getCount() +
            ", subTotal=" + getSubTotal() +
            ", total=" + getTotal() +
            ", state='" + isState() + "'" +
            "}";
    }
}
