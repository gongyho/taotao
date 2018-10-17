package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUiTreeNode;
import com.taotao.service.ItemCatService;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	
	/**
	 * 	按id查询子分类 	
	 * @param id 默认值0 查询一级目录
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUiTreeNode> getTreeNodeList(@RequestParam(defaultValue="0") Long id){
		return itemCatService.getTreeNodeList(id);
	} 
}
