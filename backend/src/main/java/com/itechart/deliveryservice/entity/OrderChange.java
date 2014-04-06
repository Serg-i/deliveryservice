package com.itechart.deliveryservice.entity;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class OrderChange {

    @Id
    @GeneratedValue
    @JsonIgnore
    private long id;

    private OrderState newState;

    @ManyToOne
    private User userChangedStatus;

    private String comment;

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
}
