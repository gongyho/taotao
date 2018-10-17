package com.taotao.common.utils;

import java.util.UUID;

/**
 * 	生成id的工具类
 * @author GongYuHao
 *
 */
public class IDUtil {
	/**
	 * 生成文件的名称
	 * @return String[] 长度为2 第一个为路径 第二个为文件名
	 */
	public static String[] generateFileName(String subfix) {
		String[] paths=UUID.randomUUID().toString().split("-");
		String[] filePathAndName={"",""};
		
		for(int i=0;i<=3;i++) {
			filePathAndName[0] += "/"+paths[i];
		}
		filePathAndName[1]=paths[4]+subfix;
		return filePathAndName;
	}
}
