package com.itechart.deliveryservice.controller.data;

import java.util.List;

public class LetterDTO {

    private List<Long> contactToId;
    private Long template;
    private String subject;
    private String text;

    public LetterDTO() {
    }

    public List<Long> getContactToId() {
        return contactToId;
    }

    public void setContactToId(List<Long> contactToId) {
        this.contactToId = contactToId;
    }

    public Long getTemplate() {
        return template;
    }

    public void setTemplate(Long template) {
        this.template = template;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

