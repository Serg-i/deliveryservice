package com.itechart.deliveryservice.controller.data;


import com.itechart.deliveryservice.utils.SearchParams;

import java.util.Date;

public class SearchOrderDTO {

    private long customer;
    private long recipient;
    private Date date;
    private SearchParams.Operator dateOp;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getRecipient() {
        return recipient;
    }

    public void setRecipient(long recipient) {
        this.recipient = recipient;
    }

    public long getCustomer() {
        return customer;
    }

    public void setCustomer(long customer) {
        this.customer = customer;
    }

    public SearchParams.Operator getDateOp() {
        return dateOp;
    }

    public void setDateOp(SearchParams.Operator dateOp) {
        this.dateOp = dateOp;
    }

    public SearchParams createParams() {

        SearchParams sp = new SearchParams();
        if (customer > 0)
            sp.addParam("customer.id", Long.toString(customer));
        if (recipient > 0)
            sp.addParam("recipient.id", Long.toString(recipient));
        if (date != null && dateOp != null)
            sp.addParam("date", dateOp, date);
        return sp;
    }

}
