package com.itechart.deliveryservice.utils.mail.template;

import com.itechart.deliveryservice.entity.Contact;

public interface Template {

    public String getName();
    public String getTemplateText();
    public String getText(Contact contact);
}
