package com.itechart.deliveryservice.entity;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "order_change")
public class OrderChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_id", nullable = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_state", nullable = false)
    private OrderState newState;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn (name = "user_changed_status_id", nullable = false)
    private User userChangedStatus;

    private String comment;

    @Column(nullable = false)
    private Date date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public OrderState getNewState() {
        return newState;
    }

    public void setNewState(OrderState newState) {
        this.newState = newState;
    }

    public User getUserChangedStatus() {
        return userChangedStatus;
    }

    public void setUserChangedStatus(User userChangedStatus) {
        this.userChangedStatus = userChangedStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
