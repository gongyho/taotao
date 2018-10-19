package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUiTreeNode;
import com.taotao.common.utils.IDUtil;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCategortyService;

/**
 * 内容分类业务成实现类
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategortyService {
	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;

	/**
	 * 获取分类列表
	 */
	public List<EasyUiTreeNode> getTreeNodeList(Long id) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(id);

		List<TbContentCategory> categoryList = tbContentCategoryMapper.selectByExample(example);

		List<EasyUiTreeNode> treeNodeList = new ArrayList<EasyUiTreeNode>();
		EasyUiTreeNode node = null;
		for (TbContentCategory category : categoryList) {
			node = new EasyUiTreeNode();
			node.setId(category.getId());
			node.setText(category.getName());
			node.setState(category.getIsParent() ? "closed" : "open");
			treeNodeList.add(node);
		}

		return treeNodeList;
	}

	/**
	 * 添加分类
	 */
	public Long addContentCategory(Long parentId, String name) {
		// 设置父节不是叶子节点
		TbContentCategory parentNode = tbContentCategoryMapper.selectByPrimaryKey(parentId);
		parentNode.setIsParent(true);
		tbContentCategoryMapper.updateByPrimaryKey(parentNode);

		Long id = IDUtil.generateItemId();
		TbContentCategory category = new TbContentCategory();
		category.setId(id);
		category.setCreated(new Date());
		category.setUpdated(new Date());
		category.setParentId(parentId);
		category.setIsParent(false);
		category.setName(name);
		category.setStatus(TbContentCategory.STATUS_OK);
		category.setSortOrder(1);

		tbContentCategoryMapper.insertSelective(category);
		return id;
	}

	/**
	 * 删除分类
	 */
	public void deleteContentCategory(Long parentId, Long id) {
		// 删除的节点有直接点 递归删除
		deleteR(id);

		// 如果父节点没有子节点
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		int count = tbContentCategoryMapper.countByExample(example);
		if (count <= 1) {
			TbContentCategory category = tbContentCategoryMapper.selectByPrimaryKey(parentId);
			category.setIsParent(false);
		}
		
		//删除当前节点
		tbContentCategoryMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 更新分类
	 */
	public void updateContentCategory(Long id, String name) {
		TbContentCategory category = tbContentCategoryMapper.selectByPrimaryKey(id);
		category.setName(name);
		category.setUpdated(new Date());
		tbContentCategoryMapper.updateByPrimaryKey(category);
	}
	
	/**
	 * 递归删除
	 * @param id 要删除的id
	 */
	private void deleteR(Long id) {
		// 如果删除的节点为父节点jn
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(id);
		List<TbContentCategory> childNodeList = tbContentCategoryMapper.selectByExample(example);
		if (childNodeList.size() > 0) {
			for (TbContentCategory  childNode: childNodeList) {
				deleteR(childNode.getId());//递归
				tbContentCategoryMapper.deleteByPrimaryKey(childNode.getId());
			}
		}
	}

}
