package com.increff.pos.dto;

import com.increff.pos.controller.AbstractUiController;
import com.increff.pos.model.data.InfoData;
import com.increff.pos.model.form.UserForm;
import com.increff.pos.service.ApiException;
import com.increff.pos.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Component
public class InitDto extends AbstractUiController {
    @Autowired
    private UserDto userDto;
    @Autowired
    private InfoData info;

    @Value("${app.admin_email}")
    private String admin_email;


    public ModelAndView showPage(UserForm form) {
        info.setMessage("");
        return mav("init.html");
    }

    public ModelAndView initSite(UserForm form) throws  ApiException {
        if(StringUtil.isEmpty(form.getEmail()) || StringUtil.isEmpty(form.getPassword())) {
            info.setMessage("Email or Password cannot be empty");
        }
        else if (userDto.checkEmailExists(form.getEmail())) {
            info.setMessage("You already have an account, please use existing credentials");
        }
        else if(Objects.equals(form.getEmail(), admin_email))
        {
            form.setRole("supervisor");
            userDto.add(form);
            info.setMessage("Signed Up Successfully, you can login now");
        }
        else
        {
            form.setRole("operator");
            userDto.add(form);
            info.setMessage("Signed Up Successfully, you can login now");
        }
        return mav("init.html");
    }
}
