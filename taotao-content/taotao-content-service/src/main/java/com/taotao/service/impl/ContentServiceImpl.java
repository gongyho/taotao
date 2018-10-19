package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUiDatagridResult;
import com.taotao.common.utils.IDUtil;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.service.ContentService;

/**
 * 内容服务
 * @author GongYuHao
 *
 */
@Service
public class ContentServiceImpl implements ContentService{
	@Autowired
	private TbContentMapper tbContentMapper;
	
	public EasyUiDatagridResult getContentByCategory(Long id, Integer page, Integer rows) {
		//设置分页
		PageHelper.startPage(page, rows);
		//查询
		TbContentExample example=new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(id);
		List<TbContent> list = tbContentMapper.selectByExample(example);
		//获取分页信息
		PageInfo<TbContent> info=new PageInfo<>(list);
		EasyUiDatagridResult result=new EasyUiDatagridResult();
		result.setTotal(info.getTotal());
		result.setRows(list);
		return result;
	}

	@Override
	public void addContent(TbContent content) {
		content.setId(IDUtil.generateItemId());
		content.setCreated(new Date());
		content.setUpdated(new Date());
		tbContentMapper.insertSelective(content);
	}
}
