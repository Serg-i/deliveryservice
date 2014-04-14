package com.itechart.deliveryservice.controller.data;

import com.itechart.deliveryservice.controller.validators.LongString;
import com.itechart.deliveryservice.entity.OrderState;

public class NewStateDTO {

    private OrderState newState;
    @LongString
    private String comment;

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
}
