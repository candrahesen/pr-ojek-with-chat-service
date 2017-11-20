package com.ojek.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.StrictMath.abs;

public class StringUtil {

    public static String randomToken(int length) {
        Random rand = new Random();
        String permitted = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        StringBuilder stringBuilder = new StringBuilder();
        while (length > 0) {
            stringBuilder.append(permitted.charAt(abs(rand.nextInt())%permitted.length()));
            length--;
        }

        return stringBuilder.toString();
    }

    public static String string(String str) {
        if (str == null) {
            return "";
        }
        return str;
    }

    public static Boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*" +
                "@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String dateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyy.MMMMM.dd GGG hh:mm aaa");
        return format.format(date);
    }

    public static Date stringToDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyy.MMMMM.dd GGG hh:mm aaa");
        try {
            return format.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }

}
