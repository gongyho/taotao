package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;

/**
 * 用户登陆服务
 * @author GongYuHao
 *
 */
public interface UserLoginService {
	TaotaoResult login(String username,String password);
}
