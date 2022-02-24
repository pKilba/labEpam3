package com.epam.esm.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order extends RepresentationModel<Order> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "orders_certificates_id", nullable = false)
    private long certificate;
    @Column(name = "orders_users_id", nullable = false)
    private long user;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column(name = "order_date", nullable = false)
    private Timestamp orderDate;

    @Column(nullable = false, updatable = false)
    private BigDecimal cost;

    public Order() {
    }

    public Order(long user, long certificate, Timestamp orderDate, BigDecimal cost) {
        this.user = user;
        this.certificate = certificate;
        this.orderDate = orderDate;
        this.cost = cost;
    }

    public Order(long id, long user, long
            certificate, Timestamp orderDate, BigDecimal cost) {
        this.id = id;
        this.user = user;
        this.certificate
                = certificate;
        this.orderDate = orderDate;
        this.cost = cost;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public long getCertificate() {
        return certificate;
    }

    public void setCertificate(long certificate) {
        this.certificate = certificate;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return id == order.id && certificate == order.certificate && user == order.user && Objects.equals(orderDate, order.orderDate) && Objects.equals(cost, order.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, certificate, user, orderDate, cost);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", certificate=" + certificate +
                ", user=" + user +
                ", orderDate=" + orderDate +
                ", cost=" + cost +
                '}';
    }
}
