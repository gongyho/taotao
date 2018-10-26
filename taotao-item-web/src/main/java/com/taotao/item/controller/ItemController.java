package com.taotao.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.item.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemService itemService;
	@RequestMapping("/{id}")
	public String getItem(@PathVariable Long id,Model model) {
		TbItem tbItem = itemService.getItemById(id);
		TbItemDesc itemDesc = itemService.getDescById(id);
		Item item=new Item(tbItem);
		
		model.addAttribute("item", item);
		model.addAttribute("itemDesc",itemDesc);
		return "item";
	}
}
