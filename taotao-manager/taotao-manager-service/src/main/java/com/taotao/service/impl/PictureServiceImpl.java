package com.taotao.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.FtpUtil;
import com.taotao.common.utils.IDUtil;
import com.taotao.service.PictureService;

/**
 * 图片服务
 * 
 * @author GongYuHao
 *
 */
@Service
public class PictureServiceImpl implements PictureService {
	@Value("${ftp.host}")
	private String host;
	@Value("${ftp.port}")
	private Integer port;
	@Value("${ftp.username}")
	private String username;
	@Value("${ftp.password}")
	private String password;
	@Value("${ftp.basePath}")
	private String basePath;
	@Value("${image.baseUrl}")
	private String baseUrl;

	/**
	 * 文件上传
	 */
	public Map<String, Object> uploadPicture(String fileName, byte[] bytes) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			InputStream inputStream=new ByteArrayInputStream(bytes);
			String[] filePathAndName = IDUtil.generateFileName(fileName.substring(fileName.lastIndexOf(".")));
			// 返回结果map

			boolean success;
			success = FtpUtil.uploadFile(host, port, username, password, basePath, filePathAndName[0],
					filePathAndName[1],inputStream);
			if (success) {
				resultMap.put("error", 0);
				resultMap.put("url", baseUrl + filePathAndName[0] + "/" + filePathAndName[1]);
			} else {
				resultMap.put("error", 1);
				resultMap.put("message", "文件上传失败");
			}
			return resultMap;
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("error", 1);
			resultMap.put("message", "文件上传失败");
			return resultMap;
		}
	}
}
