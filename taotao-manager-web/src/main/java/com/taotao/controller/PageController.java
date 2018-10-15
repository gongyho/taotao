package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 	显示页面
 * @author GongYuHao
 *
 */
@Controller
public class PageController {
	/**
	 * 	展示首页
	 * @return
	 */
	@RequestMapping("/")
	public String showIndex() {
		return "index";
	}
	/**
	 * 	展示页面
	 */
	@RequestMapping("/{page}")
	public String shwoPage(@PathVariable String page) {
		return page;
	}
}
