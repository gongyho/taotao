package com.taotao.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserRegisteServcie;

@Controller
public class UserRegisteController {
	@Autowired
	private UserRegisteServcie userRegisteServcie;

	@RequestMapping(value = "/user/check/{param}/{type}", method = RequestMethod.GET)
	@ResponseBody
	public TaotaoResult checkData(@PathVariable String param, @PathVariable Integer type) {
		return userRegisteServcie.checkData(param, type);
	}

	@RequestMapping(value = "/user/registe", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult registe(TbUser user) {
		return userRegisteServcie.registe(user);
	}
}
