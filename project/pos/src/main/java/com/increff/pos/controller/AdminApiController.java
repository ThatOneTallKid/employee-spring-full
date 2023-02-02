package com.increff.pos.controller;

import com.increff.pos.dto.UserDto;
import com.increff.pos.model.data.UserData;
import com.increff.pos.model.form.UserForm;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping(path = "/api/admin/user")
public class AdminApiController {

	@Autowired
	private UserDto userDto;

	@ApiOperation(value = "Adds a user")
	@RequestMapping(path = "", method = RequestMethod.POST)
	public void addUser(@RequestBody UserForm form) throws ApiException {
		userDto.add(form);
	}

	@ApiOperation(value = "Deletes a user")
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable int id) {
		userDto.delete(id);
	}

	@ApiOperation(value = "Gets list of all users")
	@RequestMapping(path = "", method = RequestMethod.GET)
	public List<UserData> getAllUser() {
		return userDto.getAllUser();
	}

}
