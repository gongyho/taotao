package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EasyUiTreeNode;
/**
 * 	商品分类服务接口
 * @author GongYuHao
 *
 */
public interface ItemCatService {
	/**
	 * 	根据分类父id查询分类
	 * @param parentId
	 * @return
	 */
	List<EasyUiTreeNode> getTreeNodeList(Long parentId);
}
