package com.itechart.deliveryservice.controller.data;

import com.itechart.deliveryservice.controller.validators.LongString;
import com.itechart.deliveryservice.controller.validators.Numeric;
import com.itechart.deliveryservice.entity.PhoneType;
import com.sun.istack.NotNull;

import javax.validation.constraints.Size;

public class PhoneDTO {

    private long id;
    private long ownerId;
    @Size(max = 4)
    @Numeric
    private String countryCode;
    @Size(max = 3)
    @Numeric
    private String operatorCode;
    @Size(max = 8)
    @Numeric
    private String number;
    @NotNull
    private PhoneType type;
    @LongString
    private String comment;
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public long getOwnerId() {
        return ownerId;
    }
    
    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }
    
    public String getCountryCode() {
        return countryCode;
    }
    
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    
    public String getOperatorCode() {
        return operatorCode;
    }
    
    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }
    
    public String getNumber() {
        return number;
    }
    
    public void setNumber(String number) {
        this.number = number;
    }

    public PhoneType getType() {
        return type;
    }
    
    public void setType(PhoneType type) {
        this.type = type;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }

}
