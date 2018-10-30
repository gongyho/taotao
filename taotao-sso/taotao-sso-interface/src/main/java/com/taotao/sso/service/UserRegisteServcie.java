package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

public interface UserRegisteServcie {
	/**
	 * 验证注册信息
	 * @param param
	 * @param type 1 2 3 分别是 用户名 手机 email
	 * @return
	 */
	TaotaoResult checkData(String param ,Integer type);
	
	/**
	 * 注册
	 * @param user
	 * @return
	 */
	TaotaoResult registe(TbUser user);
}
