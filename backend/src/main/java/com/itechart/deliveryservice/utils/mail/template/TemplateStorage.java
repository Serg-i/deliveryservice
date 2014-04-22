package com.itechart.deliveryservice.utils.mail.template;

import com.itechart.deliveryservice.utils.mail.template.templateImpl.EmptyTemplate;
import com.itechart.deliveryservice.utils.mail.template.templateImpl.GreatDayTemplate;
import com.itechart.deliveryservice.utils.mail.template.templateImpl.NewYearTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TemplateStorage {

    HashMap<Long, Template> templates;

    public TemplateStorage() {
        templates = new HashMap<Long, Template>();
        initTemplates();
    }

    private void initTemplates() {
        templates.put(new Long(0), new EmptyTemplate());
        templates.put(new Long(1), new NewYearTemplate());
        templates.put(new Long(2), new GreatDayTemplate());
    }

    public Template getTemplate(Long id){
        return templates.get(id);
    }

    public List<String> getAll(){
        List<String> tempNames = new ArrayList<String>() ;
        for(int id = 0; id < templates.size(); id++){
            tempNames.add(templates.get(id).getName());
        }
        return tempNames;
    }
}
