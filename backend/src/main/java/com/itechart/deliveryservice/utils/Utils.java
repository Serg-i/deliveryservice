package com.itechart.deliveryservice.utils;

public class Utils {
    public static int firstItem(int page, int count) {
        if (page <= 0)
            return 0;
        int pageCount = Math.max(1, count / Settings.getRows() + (count % Settings.getRows() == 0 ? 0 : 1));
        page = Math.min(pageCount, page);
        return Settings.getRows() * (page - 1);
    }
}
