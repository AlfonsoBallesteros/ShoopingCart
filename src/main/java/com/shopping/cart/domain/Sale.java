package com.shopping.cart.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Sale.
 */
@Document(collection = "sale")
public class Sale extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("code")
    private String code;

    @Field("date")
    private Instant date;

    @Field("medio_pago")
    private String medioPago;

    @Field("sub_total")
    private Integer subTotal;

    @Field("total")
    private Integer total;

    @Field("state")
    private Boolean state;

    @DBRef
    @Field("order")
    private Set<Order> orders = new HashSet<>();

    @DBRef
    @Field("costumer")
    @JsonIgnoreProperties(value = "sales", allowSetters = true)
    private User costumer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Sale code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Instant getDate() {
        return date;
    }

    public Sale date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public Sale medioPago(String medioPago) {
        this.medioPago = medioPago;
        return this;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    public Integer getSubTotal() {
        return subTotal;
    }

    public Sale subTotal(Integer subTotal) {
        this.subTotal = subTotal;
        return this;
    }

    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }

    public Integer getTotal() {
        return total;
    }

    public Sale total(Integer total) {
        this.total = total;
        return this;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Boolean isState() {
        return state;
    }

    public Sale state(Boolean state) {
        this.state = state;
        return this;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public Sale orders(Set<Order> orders) {
        this.orders = orders;
        return this;
    }

    public Sale addOrder(Order order) {
        this.orders.add(order);
        order.setSale(this);
        return this;
    }

    public Sale removeOrder(Order order) {
        this.orders.remove(order);
        order.setSale(null);
        return this;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sale)) {
            return false;
        }
        return id != null && id.equals(((Sale) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sale{" +
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
