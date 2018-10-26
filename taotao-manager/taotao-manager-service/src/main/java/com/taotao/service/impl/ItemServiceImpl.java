package com.taotao.service.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.jedis.JedisClient;
import com.taotao.common.pojo.EasyUiDatagridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtil;
import com.taotao.common.utils.JsonUtils;
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
	@Autowired
	private JedisClient jedisClient;
	@Value("${ITEM_KEY}")
	private String ITEM_KEY;
	@Value("${ITEM_DESC_KEY}")
	private String ITEM_DESC_KEY;
	@Value("${KEY_EXPIRE}")
	private Integer KEY_EXPIRE;
	
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
	
	/**
	 * 根据商品id查询商品
	 */
	public TbItem getItemById(Long id) {
		String item_key=MessageFormat.format(ITEM_KEY, id.toString());
		try {
			//搜索缓存
			if(jedisClient.exists(item_key)) {
				String json = jedisClient.get(item_key);
				System.out.println("缓存取商品--------------------------"+item_key);
				return JsonUtils.jsonToPojo(json,TbItem.class);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//查询数据库
		TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
		
		try {
			//添加缓存
			jedisClient.set(item_key,JsonUtils.objectToJson(tbItem));
			//设置过期时间
			jedisClient.expire(item_key,KEY_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("数据库取商品----------------------------------");
		return tbItem;
	}
	
	
	/**
	 * 根据商品id查询商品描述
	 */
	public TbItemDesc getDescById(Long id) {
		String item_desc_key=MessageFormat.format(ITEM_DESC_KEY, id.toString());
		//取缓存
		try {
			if(jedisClient.exists(item_desc_key)){
				String json = jedisClient.get(item_desc_key);
				System.out.println("缓存取详情--------------------------"+item_desc_key);
				return JsonUtils.jsonToPojo(json, TbItemDesc.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(id);
		
		//设置缓存
		try {
			jedisClient.set(item_desc_key, JsonUtils.objectToJson(tbItemDesc));
			jedisClient.expire(item_desc_key,KEY_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("数据库取详情----------------------------------");
		return tbItemDesc;
	}
}
