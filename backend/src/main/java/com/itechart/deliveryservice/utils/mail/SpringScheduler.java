package com.itechart.deliveryservice.utils.mail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.itechart.deliveryservice.dao.ContactDao;
import com.itechart.deliveryservice.entity.Contact;
import com.itechart.deliveryservice.utils.Settings;
import com.itechart.deliveryservice.utils.mail.Letter;
import com.itechart.deliveryservice.utils.mail.Sender;
import com.itechart.deliveryservice.utils.mail.template.templateImpl.EmptyTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class SpringScheduler {

    @Autowired
    private ContactDao contactDao;
    private boolean isBirthday = false;

    @Scheduled(fixedRate = 86400000)
    public void remindContactBD() {

        List<Contact> allContacts =  contactDao.getAll();
        List<Contact> bdContacts =  new ArrayList<Contact>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");

        for(Contact contact : allContacts){
            if(!"".equals(sdf.format(contact.getDateOfBirth()))){
                if(sdf.format(contact.getDateOfBirth()).equals(sdf.format(new Date()))){
                    isBirthday = true;
                    bdContacts.add(contact);
                }
            }
        }
        if(isBirthday){

            Letter letter = new Letter();

            letter.setAddressFrom(Settings.getMailAddressFrom());
            letter.setPassword(Settings.getMailPassword());

            Contact contact = new Contact();
            contact.setEmail(Settings.getMailAddressFrom());
            List<Contact> contacts = new ArrayList<Contact>();
            contacts.add(contact);
            letter.setContactTo(contacts);

            letter.setSubject("BIRTHDAY");
            letter.setTemplate(new EmptyTemplate());

            String message = "Today's Birthday(s) of ";
            for(Contact bdContact : bdContacts){
                message+= bdContact.getName()+" "+bdContact.getSurname()+", ";
            }
            letter.setText(message.substring(0, message.length() - 2) + ".");
            Sender sender = new Sender(letter);
            sender.start();
        }
    }
}