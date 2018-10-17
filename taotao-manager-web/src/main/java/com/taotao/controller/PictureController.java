package com.taotao.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.service.PictureService;

@Controller
@RequestMapping("/pic")
public class PictureController {
	@Autowired
	private PictureService pictureService;

	@RequestMapping("/upload")
	@ResponseBody
	public Map<String, Object> uplodPicture(MultipartFile uploadFile) {
		try {
			/**
			 * dubbo 不支持流传输 必须转化为字节流
			 */
			byte[] bytes = uploadFile.getBytes();
			
			//增强兼容性 最好返回String 类型的json
			return pictureService.uploadPicture(uploadFile.getOriginalFilename(), bytes);
		} catch (IOException e) {
			e.printStackTrace();
			Map<String, Object> resultMap=new HashMap<String, Object>();
			resultMap.put("error",1);
			resultMap.put("message","上传失败");
			return resultMap;
		}

	}
}
