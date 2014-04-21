package com.itechart.deliveryservice.utils;

import org.springframework.stereotype.Component;

@Component
public class Settings {

    private static int rows;
    private static String mailAddressFrom;
    private static String mailPassword;
    public static void fill(int rows, String mailAddressFrom, String mailPassword) {
        Settings.rows = rows;
        Settings.mailAddressFrom = mailAddressFrom;
        Settings.mailPassword = mailPassword;
    }
    public static int getRows() {
        return rows;
    }

    public static String getMailAddressFrom() {
        return mailAddressFrom;
    }

    public static String getMailPassword() {
        return mailPassword;
    }
}
