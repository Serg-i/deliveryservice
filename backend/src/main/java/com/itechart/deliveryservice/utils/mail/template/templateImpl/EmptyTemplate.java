package com.itechart.deliveryservice.utils.mail.template.templateImpl;

import com.itechart.deliveryservice.entity.Contact;
import com.itechart.deliveryservice.utils.mail.template.Template;
import org.antlr.stringtemplate.StringTemplate;

public class EmptyTemplate implements Template {

    private String name;
    private String templateText;

    public EmptyTemplate() {
        init();
    }

    private void init() {
        setName("Not selected");
        setTemplateText("");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTemplateText(String templateText) {
        this.templateText = templateText;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTemplateText() {
        return templateText;
    }

    @Override
    public String getText(Contact contact) {
        return "";
    }
}
