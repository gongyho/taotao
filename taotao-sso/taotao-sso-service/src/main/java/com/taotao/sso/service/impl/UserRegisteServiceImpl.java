package com.taotao.sso.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtil;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.service.UserRegisteServcie;
@Service
public class UserRegisteServiceImpl implements UserRegisteServcie {
	@Autowired
	private TbUserMapper userMapper;
	
	public TaotaoResult checkData(String param, Integer type) {
		TbUserExample example=new TbUserExample();
		Criteria criteria = example.createCriteria();
		if(type==1) {
			//用户名为空
			if(null==param || param.trim().length()==0) {
				return TaotaoResult.ok(false);
			}
			criteria.andUsernameEqualTo(param);
		}else if(type==2){
			criteria.andPhoneEqualTo(param);
		}else if(type==3) {
			criteria.andEmailEqualTo(param);
		}else {
			return TaotaoResult.build(400, "非法参数");
		}
		int count = userMapper.countByExample(example);
		if(count>0) {
			return TaotaoResult.ok(false);
		}
		return TaotaoResult.ok(true);
	}

	@Override
	public TaotaoResult registe(TbUser user) {
		TaotaoResult bad=TaotaoResult.build(400, "注册失败，晴校验数据后重试！");
		if(StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
			return bad;
		}
		
		TaotaoResult checkData = null;
		//校验用户名
		checkData = checkData(user.getUsername(), 1);
		if(!(boolean) checkData.getData()) {
			return bad;
		}
		//校验电话
		if(!StringUtils.isEmpty(user.getPhone())) {
			checkData = checkData(user.getPhone(), 2);
			if(!(boolean) checkData.getData()) {
				return bad;
			}
		}
		//校验邮箱
		if(!StringUtils.isEmpty(user.getEmail())) {
			checkData = checkData(user.getEmail(), 3);
			if(!(boolean) checkData.getData()) {
				return bad;
			}
		}
		//补全数据
		user.setId(IDUtil.generateItemId());
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		//加密
		String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Password);
		userMapper.insertSelective(user);
		return TaotaoResult.ok();
	}
}
