package com.itechart.deliveryservice.entity;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private OrderState state;

    private long customer;

    private String description;

    private Date date;

    private int cost;

    @Column(name = "reception_manager")
    private long receptionManager ;

    @Column(name = "processing_manager")
    private long processingManager ;

    @Column(name = "delivery_manager")
    private long deliveryManager ;

    private long recipient;

    public Order() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public long getCustomer() {
        return customer;
    }

    public void setCustomer(long customer) {
        this.customer = customer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public long getReceptionManager() {
        return receptionManager;
    }

    public void setReceptionManager(long receptionManager) {
        this.receptionManager = receptionManager;
    }

    public long getProcessingManager() {
        return processingManager;
    }

    public void setProcessingManager(long processingManager) {
        this.processingManager = processingManager;
    }

    public long getDeliveryManager() {
        return deliveryManager;
    }

    public void setDeliveryManager(long deliveryManager) {
        this.deliveryManager = deliveryManager;
    }

    public long getRecipient() {
        return recipient;
    }

    public void setRecipient(long recipient) {
        this.recipient = recipient;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
