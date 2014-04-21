package com.itechart.deliveryservice.controller.data;

import com.itechart.deliveryservice.controller.validators.BasicString;
import com.itechart.deliveryservice.entity.UserRole;

import javax.validation.constraints.Size;

public class UserUpdateDTO {
    private long id;
    @BasicString
    private String username;
    @Size(min=6)
    private String password;
    private UserRole role;
    private long contactId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }
}
