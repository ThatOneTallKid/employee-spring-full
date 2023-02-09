package com.increff.pos.dto;

import com.increff.pos.AbstractUnitTest;
import com.increff.pos.model.data.UserData;
import com.increff.pos.model.form.UserForm;
import com.increff.pos.service.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserDtoTest extends AbstractUnitTest {
    @Autowired
    UserDto userDto;

    @Test
    public void userTest() throws ApiException {
        UserForm userForm = new UserForm();
        userForm.setEmail("ax@fake.com");
        userForm.setPassword("12345678");
        userForm.setRole("supervisor");
        userDto.add(userForm);

        List<UserData> userDataList = userDto.getAllUser();
        assertEquals(1, userDataList.size());
    }

    @Test
    public void userDeleteTest() throws ApiException {
        UserForm userForm = new UserForm();
        userForm.setEmail("ax@fake.com");
        userForm.setPassword("12345678");
        userForm.setRole("supervisor");
        userDto.add(userForm);

        List<UserData> userDataList = userDto.getAllUser();
        assertEquals(1, userDataList.size());

        userDto.delete(userDataList.get(0).getId());
        List<UserData> userDataList2 = userDto.getAllUser();
        assertEquals(0, userDataList2.size());

    }

    @Test
    public void userGetTest() throws ApiException {
        UserForm userForm = new UserForm();
        userForm.setEmail("ax@fake.com");
        userForm.setPassword("12345678");
        userForm.setRole("supervisor");
        userDto.add(userForm);

        List<UserData> userDataList = userDto.getAllUser();
        assertEquals(1, userDataList.size());

        assertEquals(true, userDto.checkEmailExists("ax@fake.com"));
    }

    @Test
    public void checkIllegalEmailTest() throws ApiException {
        UserForm userForm = new UserForm();
        userForm.setEmail("ax@fake.com");
        userForm.setPassword("12345678");
        userForm.setRole("supervisor");
        userDto.add(userForm);

        assertEquals(false, userDto.checkEmailExists("adi@fake.com"));
    }

}
