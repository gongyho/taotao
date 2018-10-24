package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.jedis.JedisClient;
import com.taotao.common.pojo.EasyUiDatagridResult;
import com.taotao.common.utils.IDUtil;
import com.taotao.common.utils.JsonUtils;
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
	@Value("${CONTENT_KEY}")
	private String CONTENT_KEY; //内容缓存的key
	@Autowired
	private JedisClient jedisClient;
	/**
	 * 根据分类获取内容 分页
	 */
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

	/**
	 * 添加
	 */
	public void addContent(TbContent content) {
		content.setId(IDUtil.generateItemId());
		content.setCreated(new Date());
		content.setUpdated(new Date());
		tbContentMapper.insertSelective(content);
		
		//清空缓存
		jedisClient.hdel(CONTENT_KEY, content.getCategoryId().toString());
		System.out.println("-------------------------清缓存---------------------------------");
	}
	/**
	 * 删除
	 */
	public void deleteContent(String[] id) {
		//获取分类id 用来清缓存
		Long categoryId = tbContentMapper.selectByPrimaryKey(Long.valueOf(id[0])).getCategoryId();
		
		for (int i = 0; i < id.length; i++) {
			tbContentMapper.deleteByPrimaryKey(Long.valueOf(id[i]));
		}
		
		//清空缓存
		jedisClient.hdel(CONTENT_KEY, categoryId.toString());
		System.out.println("-------------------------清缓存---------------------------------");
	}

	/**
	 * 编辑
	 */
	public void updateContent(TbContent content) {
		content.setUpdated(new Date());
		tbContentMapper.updateByPrimaryKeySelective(content);
		
		jedisClient.hdel(CONTENT_KEY, content.getCategoryId().toString());
		System.out.println("-------------------------清缓存---------------------------------");
	}

	/**
	 * 通过分类id 获取内容
	 */
	public List<TbContent> getContentByCategory(Long categoryId) {
		//尝试缓存取  不能影响正常 流程
		try {
			if(jedisClient.hexists(CONTENT_KEY, categoryId.toString())) {
				String json =jedisClient.hget(CONTENT_KEY, categoryId.toString());
				System.out.println("缓存中取数据-------------------------------------------------------------------------------");
				return JsonUtils.jsonToList(json, TbContent.class);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		TbContentExample example=new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		 List<TbContent> contentList = tbContentMapper.selectByExample(example);
		
		//写入缓存  不能影响正常业务  要try
		try {
			jedisClient.hset(CONTENT_KEY, categoryId.toString(), JsonUtils.objectToJson(contentList));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("数据库取数据-------------------------------------------------------------------------------");
		return contentList;
	}
}
