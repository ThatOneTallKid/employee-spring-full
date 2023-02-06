package com.increff.pos.dto;

import com.increff.pos.dtoUtil.UserFormHelper;
import com.increff.pos.model.data.UserData;
import com.increff.pos.model.form.UserForm;
import com.increff.pos.pojo.UserPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.UserService;
import com.increff.pos.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class UserDto {

    @Autowired
    private UserService userService;

    public void add(UserForm userForm) throws ApiException {
        ValidationUtil.validateForms(userForm);
        UserFormHelper.normalizeUser(userForm);
        userService.add(UserFormHelper.convertUserFormToPojo(userForm));
    }

    public void delete(int id) {
        userService.delete(id);
    }

    public List<UserData> getAllUser() {
        List<UserPojo> list = userService.getAll();
        List<UserData> list2 = new ArrayList<UserData>();
        for (UserPojo p : list) {
            list2.add(UserFormHelper.convertUserPojoToData(p));
        }
        return list2;
    }

    public boolean checkEmailExists(String email) throws ApiException {
    	UserPojo userPojo= userService.get(email);
        return !Objects.isNull(userPojo);
    }
}
