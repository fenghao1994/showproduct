package com.shfound.showproduct.util;

import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public class Utils {
    public static boolean isMobilePhone(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        String regx = "^1[0-9]{10}$";
        return Pattern.matches(regx, mobile);
    }
}
