package com.taotao.search.service;

public interface SearchService {
	/**
	 * 导入所有的商品信息到索引库
	 * @throws Exception 
	 */
	void importAllItem() throws Exception;
}
