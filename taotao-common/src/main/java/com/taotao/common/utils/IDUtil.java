package com.taotao.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 生成id的工具类
 * 
 * @author GongYuHao
 *
 */
public class IDUtil {
	/**
	 * 	生成文件的名称
	 * 
	 * @return String[] 长度为2 第一个为路径 第二个为文件名
	 */
	public static String[] generateFileName(String subfix) {
		String[] filePathAndName = { "", "" };
		Date date = new Date();
		// 分目录
		SimpleDateFormat stf = new SimpleDateFormat("/yyyy/MM/dd");
		filePathAndName[0] = stf.format(date);
		// 随机文件名
		filePathAndName[1] = date.getTime() + String.format("%03d", new Random().nextInt(999)) + subfix;
		return filePathAndName;
	}
	/**
	 * 	生成item id
	 * @return
	 */
	public static Long generateItemId() { 
		  return new Date().getTime() + new Random().nextInt(99);
	}

}
