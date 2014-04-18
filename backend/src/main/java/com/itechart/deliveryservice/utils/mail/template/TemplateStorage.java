package com.itechart.deliveryservice.utils.mail.template;

import com.itechart.deliveryservice.utils.mail.template.templateImpl.GreatDayTemplate;
import com.itechart.deliveryservice.utils.mail.template.templateImpl.NewYearTemplate;

import java.util.HashMap;

public class TemplateStorage {

    HashMap<Long, Template> templates;

    public TemplateStorage() {
        templates = new HashMap<Long, Template>();
        initTemplates();
    }

    private void initTemplates() {
        templates.put(new Long(0), null);
        templates.put(new Long(1), new NewYearTemplate());
        templates.put(new Long(2), new GreatDayTemplate());
    }

    public Template getTemplate(Long id){
        return templates.get(id);
    }
}
