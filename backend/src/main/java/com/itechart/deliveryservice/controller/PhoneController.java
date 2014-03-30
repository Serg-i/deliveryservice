package com.itechart.deliveryservice.controller;
import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import com.itechart.deliveryservice.controller.data.PhoneVO;
import com.itechart.deliveryservice.dao.ContactDao;
import com.itechart.deliveryservice.dao.PhoneDao;
import com.itechart.deliveryservice.entity.Contact;
import com.itechart.deliveryservice.entity.Phone;

@Controller
@RequestMapping("/contacts")
@Transactional
public class PhoneController {

    @Autowired
    private PhoneDao phoneDao;
    @Autowired
    private ContactDao contactDao;
    @Autowired
    private DozerBeanMapper mapper;

    @RequestMapping(value = "/{contactId}/phones", method = RequestMethod.GET)
    @ResponseBody
    public List<PhoneVO> getAllPhones(@PathVariable long contactId) {
        
        Contact owner = contactDao.getById(contactId);
        List<PhoneVO> list = new ArrayList<PhoneVO>();
        for(Phone p : owner.getPhones())
            list.add(mapper.map(p, PhoneVO.class));
        return list;
    }

}
