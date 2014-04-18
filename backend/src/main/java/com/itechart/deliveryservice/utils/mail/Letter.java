package com.itechart.deliveryservice.utils.mail;

import com.itechart.deliveryservice.entity.Contact;
import com.itechart.deliveryservice.utils.mail.template.Template;

import java.util.ArrayList;
import java.util.List;

public class Letter {

    private String addressFrom;
    private List<Contact> contactTo;
    private Template template;
    private String password;
    private String subject;
    private String text;

    public Letter() {
        contactTo = new ArrayList<Contact>();
    }

    public List<Contact> getContactTo() {
        return contactTo;
    }

    public void setContactTo(List<Contact> contactTo) {
        this.contactTo = contactTo;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public String getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(String addressFrom) {
        this.addressFrom = addressFrom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
