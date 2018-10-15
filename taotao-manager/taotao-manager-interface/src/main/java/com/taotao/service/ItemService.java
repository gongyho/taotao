package com.taotao.service;
/**
 * 	商品相关处理的service接口
 * @author GongYuHao
 *
 */

import com.taotao.common.pojo.EasyUiDatagridResult;

public interface ItemService {
	/**
	 *	 根据当前页码和每页显示记录数查询
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUiDatagridResult getItemList(Integer page,Integer rows);
}
