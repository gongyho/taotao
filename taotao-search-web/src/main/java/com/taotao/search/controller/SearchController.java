package com.taotao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.service.SearchService;

@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;
	@Value("${ITEM_ROWS}")
	private Integer ROWS;
	
	@RequestMapping("/search")
	public String search(@RequestParam("q")String q,@RequestParam(defaultValue="1",name="page")Integer page,Model model) {
		try {
			SearchResult result = searchService.searchItem(q, page, ROWS);
			model.addAttribute("totalPages", result.getPageCount());
			model.addAttribute("itemList", result.getItemList());
			model.addAttribute("query", q);
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
			return "error/exception";
		}
		return "search";
	}
	
}
