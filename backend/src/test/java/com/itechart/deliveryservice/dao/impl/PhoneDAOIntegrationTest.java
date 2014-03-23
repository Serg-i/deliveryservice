package com.itechart.deliveryservice.dao.impl;

import java.util.Date;

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
public class PhoneDAOIntegrationTest {

    @Autowired
    ContactDAO contactDAO;
    @Autowired
    PhoneDAO phoneDAO;
    
    private Phone phone;
    private Contact owner;
    private String comment = "comment";
    private String countryCode = "375";
    private String operatorCode = "33";
    private String number = "3333333";
    private PhoneType phoneType = PhoneType.HOME;
    
    
    public PhoneDAOIntegrationTest() {
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
	    contactDAO.insert(owner);
	    phone = new Phone();
	    phone.setOwner(owner);
	    phone.setComment(comment);
	    phone.setCountryCode(countryCode);
	    phone.setOperatorCode(operatorCode);
	    phone.setNumber(number);
	    phone.setPhoneType(phoneType);
    }
    
    @Test
    @Transactional
    public void shouldCreatePhoneInDB() {
    	
        phoneDAO.insert(phone);
        assertTrue(phone.getId() > 0);
        Phone foundPhone = phoneDAO.find(phone.getId());
        assertNotNull(foundPhone);
    }

    @Test
    @Transactional
    public void shouldCreateAndFindTheSamePhone() {
    	
        phoneDAO.insert(phone);
        Phone foundPhone = phoneDAO.find(phone.getId());
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
    	
    	phoneDAO.insert(phone);
    	phone.setCountryCode(countryCode + "0");
    	phone.setOperatorCode(operatorCode + "0");
    	phone.setNumber(number + "0");
    	phone.setPhoneType(PhoneType.MOBILE);
    	phone.setComment(comment + "0");
    	phoneDAO.update(phone);
    	Phone foundPhone = phoneDAO.find(phone.getId());
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
      
        phoneDAO.insert(phone);
        Phone foundPhone = phoneDAO.find(phone.getId());
        assertNotNull(foundPhone);
        phoneDAO.delete(foundPhone);
        foundPhone = phoneDAO.find(foundPhone.getId());
        assertNull(foundPhone);
    }
	
}
