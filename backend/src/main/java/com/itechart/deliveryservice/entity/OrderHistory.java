package com.itechart.deliveryservice.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class OrderHistory {

    @Id
    @GeneratedValue
    private long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderChange> orderChanges = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<OrderChange> getOrderChanges() {
        return orderChanges;
    }

    public void setOrderChanges(List<OrderChange> orderChanges) {
        this.orderChanges = orderChanges;
    }
}
