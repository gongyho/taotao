package com.taotao.service;

import java.util.Map;


/**
 * 	图片服务接口
 * @author GongYuHao
 *
 */
public interface PictureService {
	/**
	 * 	上传文件
	 * @param fileName 文件名称
	 * @param inputStream 文件输入流
	 * @return
	 */
	Map<String, Object> uploadPicture(String fileName, byte[] bytes);
}
