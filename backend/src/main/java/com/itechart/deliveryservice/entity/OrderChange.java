package com.itechart.deliveryservice.entity;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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

    private DateTime date;

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

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }
}
