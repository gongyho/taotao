package com.taotao.service;
/**
 * 内容分类接口
 * @author GongYuHao
 *
 */

import java.util.List;

import com.taotao.common.pojo.EasyUiTreeNode;

public interface ContentCategortyService {
	/**
	 * 获取分类列表
	 */
	List<EasyUiTreeNode> getTreeNodeList(Long id);
	/**
	 * 添加分类
	 * @param parentId
	 * @param name
	 * @return 
	 */
	Long addContentCategory(Long parentId, String name);
	/**
	 * 删除分类
	 * @param parentId
	 * @param id
	 */
	void deleteContentCategory(Long parentId, Long id);
	/**
	 * 更新分类
	 * @param id
	 * @param name
	 */
	void updateContentCategory(Long id, String name);
}
