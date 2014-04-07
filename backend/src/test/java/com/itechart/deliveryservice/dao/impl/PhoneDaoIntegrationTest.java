package com.itechart.deliveryservice.dao.impl;

import com.itechart.deliveryservice.dao.ContactDao;
import com.itechart.deliveryservice.dao.PhoneDao;
import com.itechart.deliveryservice.entity.Contact;
import com.itechart.deliveryservice.entity.Phone;
import com.itechart.deliveryservice.entity.PhoneType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.List;

import static junit.framework.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-bean-context.xml"})
@TransactionConfiguration(defaultRollback=true)
public class PhoneDaoIntegrationTest {

    @Autowired
    ContactDao contactDao;
    @Autowired
    PhoneDao phoneDao;

    private Contact owner;
    private Phone phone;
    private String comment = "comment";
    private String countryCode = "375";
    private String operatorCode = "33";
    private String number = "3333333";
    private PhoneType phoneType = PhoneType.HOME;
    
    public PhoneDaoIntegrationTest() {
        super();
    }


    public final void init() {

        owner = contactDao.getAll().get(0);
        phone = new Phone();
        phone.setOwner(owner);
        phone.setComment(comment);
        phone.setCountryCode(countryCode);
        phone.setOperatorCode(operatorCode);
        phone.setNumber(number);
        phone.setType(phoneType);
    }

    @Test
    @Transactional
    public void shouldCreatePhoneInDB() {

        init();
        phoneDao.save(phone);
        assertTrue(phone.getId() > 0);
        Phone foundPhone = phoneDao.getById(phone.getId());
        assertNotNull(foundPhone);
        assertTrue(contactDao.getById(owner.getId()).getPhones().contains(phone));
    }
    
    @Test(expected=NullPointerException.class)
    @Transactional
    public void shouldFailToCreatePhoneWithoutOwner() throws Throwable {

        init();
        phone.setOwner(null);
        phoneDao.save(phone);
    }
    
    @Test(expected=PersistenceException.class)
    @Transactional
    public void shouldFailToCreatePhoneWithoutCountryCode() {

        init();
        phone.setCountryCode(null);
        phoneDao.save(phone);
    }
    
    @Test(expected=PersistenceException.class)
    @Transactional
    public void shouldFailToCreatePhoneWithoutOperatorCode() {

        init();
        phone.setOperatorCode(null);
        phoneDao.save(phone);
    }
    
    @Test(expected=PersistenceException.class)
    @Transactional
    public void shouldFailToCreatePhoneWithoutNumber() {

        init();
        phone.setNumber(null);
        phoneDao.save(phone);
    }
    
    @Test(expected=PersistenceException.class)
    @Transactional
    public void shouldFailToCreatePhoneWithCommentLength201() 
            throws PersistenceException{

        init();
        String comment = new String(new char[201]).replace('\0', ' ');
        phone.setComment(comment);
        phoneDao.save(phone);
    }
    
    @Test
    @Transactional
    public void shouldCreatePhoneByAddingItToContact() {

        init();
        contactDao.merge(owner);
        List<Phone> list = contactDao.getById(owner.getId()).getPhones();
        assertEquals(list.get(list.size() - 1).getNumber(), number);
    }
    
    @Test
    @Transactional
    public void shouldFindThePhone() {

        Phone foundPhone = phoneDao.getById(1);
        assertNotNull(foundPhone);
    }

    @Test
    @Transactional
    public void shouldUpdatePhone() {

        Phone phone = phoneDao.getById(1);
        phone.setCountryCode(countryCode + "0");
        phone.setOperatorCode(operatorCode + "0");
        phone.setNumber(number + "0");
        phone.setType(PhoneType.MOBILE);
        phone.setComment(comment + "0");
        phoneDao.merge(phone);
        Phone foundPhone = phoneDao.getById(phone.getId());
        assertNotNull(foundPhone);
        assertEquals(foundPhone.getCountryCode(), countryCode + "0");
        assertEquals(foundPhone.getOperatorCode(), operatorCode + "0");
        assertEquals(foundPhone.getNumber(), number + "0");
        assertEquals(foundPhone.getType(), PhoneType.MOBILE);
        assertEquals(foundPhone.getComment(), comment + "0");
    }

    @Test
    @Transactional
    public void shouldDeleteThePhone() {
      
        Phone phone = phoneDao.getById(1);
        phoneDao.delete(phone);
        phone = phoneDao.getById(phone.getId());
        assertNull(phone);
    }
    
    @Test
    @Transactional
    public void shouldDeleteContactAndAllContactPhones() {

        owner = contactDao.getById(1);
        assertNotNull(owner);
        List<Phone> list = owner.getPhones();
        assertTrue(list.size() > 0);
        contactDao.delete(owner);
        for(Phone p : list)
            assertNull(phoneDao.getById(p.getId()));
    }

}
