package com.itechart.deliveryservice.controller.data;

import com.itechart.deliveryservice.entity.OrderState;

import java.util.Date;
import java.util.List;

public class OrderDTO {

    private long id;

    private OrderState state;

    private long customerId;
    private long recipientId;
    private long receptionManagerId;
    private long processingManagerId;
    private long deliveryManagerId;

    private String customerName;
    private String customerSurname;
    private String recipientName;
    private String recipientSurname;
    private String receptionManager;
    private String processingManager;
    private String deliveryManager;

    private Date date;

    private List<OrderChangeDTO> changes;

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

    public long getReceptionManagerId() {
        return receptionManagerId;
    }

    public void setReceptionManagerId(long receptionManagerId) {
        this.receptionManagerId = receptionManagerId;
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

    public void setChanges(List<OrderChangeDTO> changes) {
        this.changes = changes;
    }

    public List<OrderChangeDTO> getChanges() {
        return changes;
    }
}
