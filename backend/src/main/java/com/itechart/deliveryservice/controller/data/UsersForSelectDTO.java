package com.itechart.deliveryservice.controller.data;

import java.util.List;

public class UsersForSelectDTO {

    private List<UserNameDTO> processingManagers;
    private List<UserNameDTO> couriers;

    public List<UserNameDTO> getProcessingManagers() {
        return processingManagers;
    }

    public void setProcessingManagers(List<UserNameDTO> processingManagers) {
        this.processingManagers = processingManagers;
    }

    public List<UserNameDTO> getCouriers() {
        return couriers;
    }

    public void setCouriers(List<UserNameDTO> couriers) {
        this.couriers = couriers;
    }
}
