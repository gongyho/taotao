package com.taotao.search.mapper;

import java.util.List;

import com.taotao.common.pojo.SearchItem;

public interface SearchMapper {
	/**
	 *	 查询所有的item
	 * @return
	 */
	List<SearchItem> searchAllItem();
}
