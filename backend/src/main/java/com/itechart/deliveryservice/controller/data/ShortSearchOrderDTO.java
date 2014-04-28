package com.itechart.deliveryservice.controller.data;


import com.itechart.deliveryservice.utils.SearchParams;

public class ShortSearchOrderDTO {

    private long customer;
    private long recipient;

    public long getCustomer() {
        return customer;
    }

    public void setCustomer(long customer) {
        this.customer = customer;
    }

    public long getRecipient() {
        return recipient;
    }

    public void setRecipient(long recipient) {
        this.recipient = recipient;
    }

    public SearchParams createParams() {

        SearchParams sp = new SearchParams();
        if (customer > 0)
            sp.addParam("customer.id", Long.toString(customer));
        if (recipient > 0)
            sp.addParam("recipient.id", Long.toString(recipient));
        return sp;
    }
}
