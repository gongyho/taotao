package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUiTreeNode;
import com.taotao.service.ItemCatService;

@Controller
@RequestMapping("item/cat")
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping("list")
	@ResponseBody
	public List<EasyUiTreeNode> getTreeNodeList(Long id){
		if(id==null) {
			System.out.println("id is null");
			id=0L;
		}
		return itemCatService.getTreeNodeList(id);
	} 
}
