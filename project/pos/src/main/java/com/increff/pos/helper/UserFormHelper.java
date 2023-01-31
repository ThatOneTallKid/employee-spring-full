package com.increff.pos.helper;

import com.increff.pos.model.data.UserData;
import com.increff.pos.model.form.UserForm;
import com.increff.pos.pojo.UserPojo;
import com.increff.pos.util.ConvertUtil;

public class UserFormHelper {

    public static UserPojo convertUserFormToPojo(UserForm f) {
        UserPojo p = ConvertUtil.convert(f,UserPojo.class);
        return p;
    }
    public static void normalizeUser(UserForm p) {
        p.setEmail(p.getEmail().toLowerCase().trim());
        p.setRole(p.getRole().toLowerCase().trim());
    }
    public static UserData convertUserPojoToData(UserPojo p) {
        UserData d = ConvertUtil.convert(p,UserData.class);
        return d;
    }
}
