package com.taotao.sso.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.common.jedis.JedisClient;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.service.UserLoginService;

@Service
public class UserLoginServviceImpl implements UserLoginService{
	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${KEY_PREFIX}")
	private String KEY_PREFIX;
	@Value("${EXPIRE}")
	private Integer EXPIRE;
	
	public TaotaoResult login(String username, String password) {
		//密码加密
		password = DigestUtils.md5DigestAsHex(password.getBytes());
		
		TbUserExample example =new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		criteria.andPasswordEqualTo(password);

		List<TbUser> list = userMapper.selectByExample(example);
		//登陆失败
		if(list==null || list.size() != 1) {
			return TaotaoResult.build(400, "登陆失败，请核对用户名和密码");
		}
		TbUser user= list.get(0);
		//设置密码为空
		user.setPassword(null);
		//加入redis缓存
		String token = IDUtil.generateUUID();
		jedisClient.set(KEY_PREFIX+token, JsonUtils.objectToJson(user));
		jedisClient.expire(KEY_PREFIX+token, EXPIRE);
		return TaotaoResult.ok(token);
	}
	
}
