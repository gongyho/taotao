package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.SearchService;

@Controller
@RequestMapping("/index")
public class IndexController {
	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/importall")
	@ResponseBody
	public TaotaoResult importAllItem(){
		try {
			searchService.importAllItem();
			return TaotaoResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500,null);
		}
	}
}
