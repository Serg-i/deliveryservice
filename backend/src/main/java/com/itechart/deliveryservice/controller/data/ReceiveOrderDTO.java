package com.itechart.deliveryservice.controller.data;

import com.itechart.deliveryservice.controller.validators.LongString;
import com.itechart.deliveryservice.controller.validators.Numeric;

public class ReceiveOrderDTO {

    private long id;

    private long customerId;
    private long recipientId;
    private long processingManagerId;
    private long deliveryManagerId;

    @Numeric
    private String cost;
    @LongString
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(long recipientId) {
        this.recipientId = recipientId;
    }

    public long getProcessingManagerId() {
        return processingManagerId;
    }

    public void setProcessingManagerId(long processingManagerId) {
        this.processingManagerId = processingManagerId;
    }

    public long getDeliveryManagerId() {
        return deliveryManagerId;
    }

    public void setDeliveryManagerId(long deliveryManagerId) {
        this.deliveryManagerId = deliveryManagerId;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
