package com.itechart.deliveryservice.utils.mail;

import com.itechart.deliveryservice.entity.Contact;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Sender  extends Thread{

    private Session session = null;
    private Letter letter;

    public Sender(Letter letter) {
        setDaemon(true);
        this.letter = letter;
        createSession();
    }

    public void run() {
        for(Contact contact : letter.getContactTo()){
            if(!"Не выбран".equals(letter.getTemplate().getName())){
                String message =  letter.getTemplate().getText(contact);
                letter.setText(message);
            }
            send(contact);
        }
    }

    private void send(Contact contact){
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(letter.getAddressFrom()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(contact.getEmail()));
            message.setSubject(letter.getSubject());
            message.setText(letter.getText());

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                letter.getAddressFrom(), letter.getPassword());
                    }
                });
    }
}
