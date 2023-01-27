package com.increff.pos.model.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
public class UserForm {

	@Email
	@NotBlank(message = "Email must not be blank")
	private String email;

	@NotBlank(message = "Password must not be blank")
	@Size(min = 8, message = "Password must be atleast 8 character!")
	private String password;

	@NotBlank(message = "Role must not be blank")
	private String role;

}
