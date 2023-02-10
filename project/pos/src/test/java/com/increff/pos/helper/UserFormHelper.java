package com.increff.pos.helper;

import com.increff.pos.model.form.UserForm;

public class UserFormHelper {
    public static UserForm createUser(String email, String password, String role) {
        UserForm userForm = new UserForm();
        userForm.setEmail(email);
        userForm.setPassword(password);
        userForm.setRole(role);
        return userForm;
    }
}
