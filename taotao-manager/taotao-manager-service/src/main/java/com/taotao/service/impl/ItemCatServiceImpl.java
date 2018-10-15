package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUiTreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.service.ItemCatService;
/**
 * 	商品分类服务
 * @author GongYuHao
 *
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	/**
	 * 根据父分类id查询分类
	 */
	public List<EasyUiTreeNode> getTreeNodeList(Long parentId) {
		
		TbItemCatExample example=new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
		
		List<EasyUiTreeNode> nodeList=new ArrayList<EasyUiTreeNode>();
		EasyUiTreeNode node=null;
		for (TbItemCat itemCat : list) {
			node =new EasyUiTreeNode();
			node.setId(itemCat.getId());
			node.setText(itemCat.getName());
			if(itemCat.getIsParent()) {
				node.setState("closed");
			}else {
				node.setState("open");
			}
			nodeList.add(node);
		}
		return nodeList;
	}

}
