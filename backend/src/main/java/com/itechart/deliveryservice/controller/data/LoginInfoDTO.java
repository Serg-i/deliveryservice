package com.itechart.deliveryservice.controller.data;

import com.itechart.deliveryservice.entity.UserRole;

public class LoginInfoDTO {

    private String name;
    private UserRole role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

}
