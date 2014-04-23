package com.itechart.deliveryservice.controller.data;

import java.util.ArrayList;
import java.util.List;

public class ShortLetterDTO {

    private List<String> mails;
    private List<String> templateNames;

    public ShortLetterDTO() {
        mails = new ArrayList<String>();
        templateNames = new ArrayList<String>();
    }

    public List<String> getMails() {
        return mails;
    }

    public void setMails(List<String> mails) {
        this.mails = mails;
    }

    public List<String> getTemplateNames() {
        return templateNames;
    }

    public void setTemplateNames(List<String> templateNames) {
        this.templateNames = templateNames;
    }


}
