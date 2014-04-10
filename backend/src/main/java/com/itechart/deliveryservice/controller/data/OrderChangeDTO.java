package com.itechart.deliveryservice.controller.data;

import com.itechart.deliveryservice.entity.OrderState;

import java.util.Date;

public class OrderChangeDTO {

    private OrderState newState;
    private String userChangedStatusNickname;
    private String comment;
    private Date date;

    public OrderState getNewState() {
        return newState;
    }

    public void setNewState(OrderState newState) {
        this.newState = newState;
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

    public String getUserChangedStatusNickname() {
        return userChangedStatusNickname;
    }

    public void setUserChangedStatusNickname(String userChangedStatusNickname) {
        this.userChangedStatusNickname = userChangedStatusNickname;
    }
}
