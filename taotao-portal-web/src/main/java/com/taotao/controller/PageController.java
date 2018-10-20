package com.taotao.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.Ad1Node;
import com.taotao.service.ContentService;

/**
 * 展示首页
 * @author GongYuHao
 *
 */
@Controller
public class PageController {
	@Autowired
	private ContentService contentService;
	@Value("${AD1_ID}")
	private Long AD1_ID;
	@Value("${AD1_HEIGHT}")
	private String AD1_HEIGHT;
	@Value("${AD1_WIDTH}")
	private String AD1_WIDTH;
	@Value("${AD1_HEIGHT_B}")
	private String AD1_HEIGHT_B;
	@Value("${AD1_WIDTH_B}")
	private String AD1_WIDTH_B;
	
	@RequestMapping("/index")
	public String showIndex(Model model) {
		//根据内容分类id查询
		List<TbContent> contentList = contentService.getContentByCategory(AD1_ID);
		List<Ad1Node> ad1NodeList=new ArrayList<Ad1Node>();
		Ad1Node ad1Node=null;
		for (TbContent content : contentList) {
			ad1Node = new Ad1Node();
			ad1Node.setWidth(AD1_WIDTH);
			ad1Node.setHeightB(AD1_HEIGHT);
			ad1Node.setWidthB(AD1_WIDTH_B);
			ad1Node.setHeightB(AD1_HEIGHT_B);
			ad1Node.setAlt(content.getSubTitle());
			ad1Node.setSrc(content.getPic());
			ad1Node.setSrcB(content.getPic2());
			ad1Node.setHref(content.getUrl());
			ad1NodeList.add(ad1Node);
		}
		model.addAttribute("ad1",JsonUtils.objectToJson(ad1NodeList));
		return "index";
	}
}
