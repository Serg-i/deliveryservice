package com.itechart.deliveryservice.entity;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private OrderState state;

    @ManyToOne
    @JoinColumn (name="customer_id")
    private Contact customer;

    private String description;

    private Date date;

    private String cost;

    @ManyToOne
    @JoinColumn (name="reception_manager_id")
    private User receptionManager ;

    @ManyToOne
    @JoinColumn (name="processing_manager_id")
    private User processingManager ;

    @ManyToOne
    @JoinColumn (name="delivery_manager__id")
    private User deliveryManager ;

    @ManyToOne
    @JoinColumn (name="recipient_id")
    private Contact recipient;

    @OneToOne(cascade = CascadeType.ALL)
    private OrderHistory history;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public OrderHistory getHistory() {
        return history;
    }

    public void setHistory(OrderHistory history) {
        this.history = history;
    }

    public Contact getCustomer() {
        return customer;
    }

    public void setCustomer(Contact customer) {
        this.customer = customer;
    }

    public User getReceptionManager() {
        return receptionManager;
    }

    public void setReceptionManager(User receptionManager) {
        this.receptionManager = receptionManager;
    }

    public User getProcessingManager() {
        return processingManager;
    }

    public void setProcessingManager(User processingManager) {
        this.processingManager = processingManager;
    }

    public User getDeliveryManager() {
        return deliveryManager;
    }

    public void setDeliveryManager(User deliveryManager) {
        this.deliveryManager = deliveryManager;
    }

    public Contact getRecipient() {
        return recipient;
    }

    public void setRecipient(Contact recipient) {
        this.recipient = recipient;
    }
}
