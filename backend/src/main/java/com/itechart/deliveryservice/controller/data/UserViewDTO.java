package com.itechart.deliveryservice.controller.data;

import com.itechart.deliveryservice.entity.UserRole;

public class UserViewDTO {

    private long id;
    private String username;
    private String password;
    private UserRole role;
    private long contactId;
    private String contactName;
    private String contactSurname;
    private String contactMiddleName;

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

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactSurname() {
        return contactSurname;
    }

    public void setContactSurname(String contactSurname) {
        this.contactSurname = contactSurname;
    }

    public String getContactMiddleName() {
        return contactMiddleName;
    }

    public void setContactMiddleName(String midleName) {
        this.contactMiddleName = midleName;
    }
}
