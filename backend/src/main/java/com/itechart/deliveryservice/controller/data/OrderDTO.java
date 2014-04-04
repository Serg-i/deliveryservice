package com.itechart.deliveryservice.controller.data;

import com.itechart.deliveryservice.entity.OrderState;

import javax.validation.constraints.Pattern;
import java.util.Date;

public class OrderDTO {

    private long id;

    private OrderState state;

    private String customerName;

    private String customerSurname;

    private String recipientName;

    private String recipientSurname;

    private String receptionManager;

    private String processingManager;

    private String deliveryManager;

    private Date date;

    @Pattern(regexp = "^[0-9]+$")
    private String cost;

    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerSurname() {
        return customerSurname;
    }

    public void setCustomerSurname(String customerSurname) {
        this.customerSurname = customerSurname;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientSurname() {
        return recipientSurname;
    }

    public void setRecipientSurname(String recipientSurname) {
        this.recipientSurname = recipientSurname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReceptionManager() {
        return receptionManager;
    }

    public void setReceptionManager(String receptionManager) {
        this.receptionManager = receptionManager;
    }

    public String getProcessingManager() {
        return processingManager;
    }

    public void setProcessingManager(String processingManager) {
        this.processingManager = processingManager;
    }

    public String getDeliveryManager() {
        return deliveryManager;
    }

    public void setDeliveryManager(String deliveryManager) {
        this.deliveryManager = deliveryManager;
    }
}
