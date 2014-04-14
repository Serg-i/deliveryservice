package com.itechart.deliveryservice.controller.data;

import java.util.List;

public class UsersForSelectDTO {

    private List<UserNameDTO> procissingManagers;
    private List<UserNameDTO> couriers;

    public List<UserNameDTO> getProcissingManagers() {
        return procissingManagers;
    }

    public void setProcissingManagers(List<UserNameDTO> procissingManagers) {
        this.procissingManagers = procissingManagers;
    }

    public List<UserNameDTO> getCouriers() {
        return couriers;
    }

    public void setCouriers(List<UserNameDTO> couriers) {
        this.couriers = couriers;
    }
}
