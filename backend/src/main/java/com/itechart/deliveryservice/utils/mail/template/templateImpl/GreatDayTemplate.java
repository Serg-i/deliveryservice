package com.itechart.deliveryservice.utils.mail.template.templateImpl;

import com.itechart.deliveryservice.entity.Contact;
import com.itechart.deliveryservice.utils.mail.template.Template;
import org.antlr.stringtemplate.StringTemplate;

public class GreatDayTemplate implements Template {

    private String name;

    private String templateText;

    public GreatDayTemplate() {
        init();
    }

    private void init() {
        setName("Great Day");
        setTemplateText("Dear, $name$ $surname$, we want to inform you that today a great day!");
    }

    public GreatDayTemplate(String name, String templateText) {
        setName(name);
        setTemplateText(templateText);
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setTemplateText(String templateText) {
        this.templateText = templateText;
    }

    @Override
    public String getTemplateText() {
        return templateText;
    }

    @Override
    public String getText(Contact contact) {
        StringTemplate query = createQuery();
        query.setAttribute("name", contact.getName());
        query.setAttribute("surname", contact.getSurname());
        return query.toString();
    }
    private StringTemplate createQuery() {
        StringTemplate query = new StringTemplate(
                this.getTemplateText() );
        return query;
    }
}
