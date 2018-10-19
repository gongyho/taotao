package com.taotao.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUiDatagridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;

@Controller
@RequestMapping("/content")
public class ContentCentroller {
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/query/list")
	@ResponseBody
	public EasyUiDatagridResult queryList(Long categoryId,Integer page,Integer rows) {
		EasyUiDatagridResult result=null;
		//页面刚加载
		if(categoryId == 0) {
			result =new EasyUiDatagridResult();
			result.setTotal(0L);
			result.setRows(new ArrayList<>());
			return result;
		}
		//查询
		try {
			result=contentService.getContentByCategory(categoryId, page, rows);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result=new EasyUiDatagridResult();
			result.setTotal(0L);
			result.setRows(new ArrayList<>());
			return result;
		}
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult addContent(TbContent content) {
		try {
			contentService.addContent(content);
			return TaotaoResult.ok();
		} catch (Exception e) {
			
			e.printStackTrace();
			return TaotaoResult.build(500, "添加失败！");
		}
	}
}
