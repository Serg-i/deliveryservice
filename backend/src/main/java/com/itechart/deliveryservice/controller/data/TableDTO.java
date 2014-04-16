package com.itechart.deliveryservice.controller.data;

import java.util.List;

public class TableDTO<Type> {

    private int count;
    private List<Type> currentPage;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Type> getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(List<Type> currentPage) {
        this.currentPage = currentPage;
    }
}
