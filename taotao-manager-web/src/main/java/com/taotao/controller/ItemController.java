package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUiDatagridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemService itemService;
	/**
	 * 	返回分页查询商品json数据
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyUiDatagridResult itemList(Integer page,Integer rows) {
		return itemService.getItemList(page, rows);
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult saveItem(TbItem tbItem,String desc) {
		try {
			return itemService.addItem(tbItem, desc);
		} catch (Exception e) {
			e.printStackTrace();
			return new TaotaoResult(500,"添加失败", null);
		}
	}
	
}
