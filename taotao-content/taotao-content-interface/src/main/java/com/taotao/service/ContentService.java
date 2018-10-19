package com.taotao.service;

import com.taotao.common.pojo.EasyUiDatagridResult;
import com.taotao.pojo.TbContent;
/**
 * 内容服务接口
 * @author GongYuHao
 *
 */
public interface ContentService {
	/**
	 * 通过分类的id查询内容
	 * @param id
	 * @return
	 */
	EasyUiDatagridResult getContentByCategory(Long id,Integer page, Integer rows);
	/**
	 * 添加内容
	 * @param content
	 */
	void addContent(TbContent content);
}
