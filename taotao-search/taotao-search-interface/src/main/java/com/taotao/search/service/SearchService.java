package com.taotao.search.service;

import com.taotao.common.pojo.SearchResult;

public interface SearchService {
	/**
	 * 导入所有的商品信息到索引库
	 * @throws Exception 
	 */
	void importAllItem() throws Exception;
	
	/**
	 * 搜索商品
	 * @return
	 */
	SearchResult searchItem(String q,Integer page,Integer rows) throws Exception;
}
