package com.taotao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUiDatagridResult;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
/**
 * 	商品相关处理的service实现类
 * @author GongYuHao
 *
 */
@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper tbItemMapper;
	
	/**
	 * 	根据页码和每页记录数查询商品
	 */
	public EasyUiDatagridResult getItemList(Integer page, Integer rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//设置查询条件
		TbItemExample example=new TbItemExample();
		//查询
		List<TbItem> list = tbItemMapper.selectByExample(example);
		//获取分页信息
		PageInfo<TbItem> info =new PageInfo<>(list);
		//封装数据
		EasyUiDatagridResult result=new EasyUiDatagridResult();
		result.setTotal(info.getTotal());
		result.setRows(list);
		return result;
	}
}
