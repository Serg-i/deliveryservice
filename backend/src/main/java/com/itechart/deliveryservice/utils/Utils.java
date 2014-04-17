package com.itechart.deliveryservice.utils;

public class Utils {
    public static int firstItem(int page, int count) {
        if (page <= 0)
            return 0;
        int pageCount = Math.max(1, count / Settings.rows + (count % Settings.rows == 0 ? 0 : 1));
        page = Math.min(pageCount, page);
        return Settings.rows * (page - 1);
    }
}
