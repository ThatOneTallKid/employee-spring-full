package com.increff.pos.controller;

import com.increff.pos.dto.InitDto;
import com.increff.pos.model.form.UserForm;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/site/init")
public class InitApiController extends AbstractUiController {
	@Autowired
	private InitDto initDto;

	@ApiOperation(value = "Initializes application")
	@RequestMapping(path = "", method = RequestMethod.GET)
	public ModelAndView showPage(UserForm form){
		return initDto.showPage(form);
	}

	@ApiOperation(value = "Initializes application")
	@RequestMapping(path = "", method = RequestMethod.POST)
	public ModelAndView initSite(UserForm form) throws ApiException {
		return initDto.initSite(form);
	}


}
