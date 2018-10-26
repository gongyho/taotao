package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUiDatagridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtil;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
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
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Resource(name="itemChangeTopic")
	private Destination itemChangeTopic;
	
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
	/**
	 * 添加商品
	 */
	public TaotaoResult addItem(TbItem item, String desc) {
		//设置商品信息
		Long itemId = IDUtil.generateItemId();
		//生成订单id
		//添加商品信息
		item.setId(itemId);
		item.setStatus(TbItem.STATUS_OK);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		tbItemMapper.insertSelective(item);
		//添加描述
		TbItemDesc tbItemDesc=new TbItemDesc();
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setItemId(itemId);
		tbItemDesc.setCreated(new Date());
		tbItemDesc.setUpdated(new Date());
		tbItemDescMapper.insertSelective(tbItemDesc);
		
		//发布消息
		jmsTemplate.send(itemChangeTopic, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(itemId.toString());
			}
		});
		
		return TaotaoResult.ok();
	}
}
