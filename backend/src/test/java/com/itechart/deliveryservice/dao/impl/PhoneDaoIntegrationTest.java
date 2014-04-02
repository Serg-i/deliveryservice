package com.itechart.deliveryservice.dao.impl;

import java.util.Date;

import javax.persistence.PersistenceException;

import com.itechart.deliveryservice.dao.*;
import com.itechart.deliveryservice.entity.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-bean-context.xml"})
@TransactionConfiguration(defaultRollback=true)
public class PhoneDaoIntegrationTest {

    @Autowired
    ContactDao contactDao;
    @Autowired
    PhoneDao phoneDao;
    
    private Phone phone;
    private Contact owner;
    private String comment = "comment";
    private String countryCode = "375";
    private String operatorCode = "33";
    private String number = "3333333";
    private PhoneType phoneType = PhoneType.HOME;
    
    public PhoneDaoIntegrationTest() {
        super();
    }

    @Before
    public final void before() {
        
        owner = new Contact();
        owner.setName("name");
        owner.setSurname("surname");
        owner.setMiddleName("middlename");
        owner.setDateOfBirth(new Date());
        owner.setEmail("email@email.com");
        contactDao.save(owner);
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
        
        phoneDao.save(phone);
        assertTrue(phone.getId() > 0);
        Phone foundPhone = phoneDao.getById(phone.getId());
        assertNotNull(foundPhone);
        assertTrue(phoneDao.getCount() == 1);
        assertTrue(contactDao.getById(owner.getId()).getPhones().size() > 0);
    }
    
    @Test(expected=NullPointerException.class)
    @Transactional
    public void shouldFailToCreatePhoneWithoutOwner() throws Throwable {
        
        phone.setOwner(null);
        phoneDao.save(phone);
    }
    
    @Test(expected=PersistenceException.class)
    @Transactional
    public void shouldFailToCreatePhoneWithoutCountryCode() {
        
        phone.setCountryCode(null);
        phoneDao.save(phone);
    }
    
    @Test(expected=PersistenceException.class)
    @Transactional
    public void shouldFailToCreatePhoneWithoutOperatorCode() {
        
        phone.setOperatorCode(null);
        phoneDao.save(phone);
    }
    
    @Test(expected=PersistenceException.class)
    @Transactional
    public void shouldFailToCreatePhoneWithoutNumber() {
        
        phone.setNumber(null);
        phoneDao.save(phone);
    }
    
    @Test(expected=PersistenceException.class)
    @Transactional
    public void shouldFailToCreatePhoneWithCommentLength201() 
            throws PersistenceException{
        
        String comment = new String(new char[201]).replace('\0', ' ');
        phone.setComment(comment);
        phoneDao.save(phone);
    }
    
    @Test
    @Transactional
    public void shouldCreatePhoneByAddingItToContact() {
        
        owner.addPhone(phone);
        contactDao.merge(owner);
        assertTrue(phoneDao.getCount() == 1);
    }
    
    @Test
    @Transactional
    public void shouldCreateAndFindTheSamePhone() {
        
        phoneDao.save(phone);
        Phone foundPhone = phoneDao.getById(phone.getId());
        assertNotNull(foundPhone);
        assertEquals(foundPhone.getCountryCode(), countryCode);
        assertEquals(foundPhone.getOperatorCode(), operatorCode);
        assertEquals(foundPhone.getNumber(), number);
        assertEquals(foundPhone.getOwner().getId(), owner.getId());
        assertEquals(foundPhone.getType(), phoneType);
        assertEquals(foundPhone.getComment(), comment);
    }

    @Test
    @Transactional
    public void shouldCreateAndUpdatePhone() {
        
        phoneDao.save(phone);
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
    public void shouldCreateAndAfterDeleterPhone() {
      
        phoneDao.save(phone);
        Phone foundPhone = phoneDao.getById(phone.getId());
        assertNotNull(foundPhone);
        phoneDao.delete(foundPhone);
        foundPhone = phoneDao.getById(foundPhone.getId());
        assertNull(foundPhone);
    }
    
    @Test
    @Transactional
    public void shouldDeleteContactAndAllContactPhones() {
      
        phoneDao.save(phone);
        Phone foundPhone = phoneDao.getById(phone.getId());
        assertNotNull(foundPhone);
        contactDao.delete(owner);
        assertNull(phoneDao.getById(phone.getId()));
    }

}
