package com.itechart.deliveryservice.controller.data;

import com.itechart.deliveryservice.utils.Settings;

import java.util.List;

public class TableDTO<Type> {

    private int count;
    private int pagesCount;
    private List<Type> currentPage;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        this.pagesCount = count / Settings.rows + (count % Settings.rows == 0 ? 0 : 1);
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

    public List<Type> getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(List<Type> currentPage) {
        this.currentPage = currentPage;
    }
}
