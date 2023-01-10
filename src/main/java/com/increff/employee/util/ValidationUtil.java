package com.increff.employee.util;

import java.util.Objects;

public class ValidationUtil {
    public static <T> Boolean checkNull(T t) {
        if(Objects.isNull(t) == true) {
            return true;
        }
        return false;
    }


}
