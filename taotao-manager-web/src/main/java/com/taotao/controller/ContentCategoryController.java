package com.taotao.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUiTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ContentCategortyService;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
	@Autowired
	private ContentCategortyService contentCategortyService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUiTreeNode> getTreeNodeList(@RequestParam(defaultValue="0")Long id){
		return contentCategortyService.getTreeNodeList(id);
	}
	
	/**
	 *  parentId: 97
	 *	name: 新建分类
	 */
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult addContentCategory(Long parentId,String name) {
		
		try {
			Long id=contentCategortyService.addContentCategory(parentId,name);
			Map<String ,Long> data=new HashMap<String ,Long>();
			data.put("id", id);
			return TaotaoResult.build(200,null, data);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500,"添加失败！");
		}
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult deleteContentCategory(Long parentId ,Long id ){
		try {
			contentCategortyService.deleteContentCategory(parentId,id);
			return TaotaoResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, "删除失败！");
		}
		
	}
	
	
	@RequestMapping("/update")
	@ResponseBody
	public TaotaoResult updateContentCategory(Long id,String name){
		try {
			contentCategortyService.updateContentCategory(id,name);
			return TaotaoResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500,"更新失败！");
		}
	}
}
