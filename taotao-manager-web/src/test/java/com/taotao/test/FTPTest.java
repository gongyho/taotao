package com.taotao.test;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

public class FTPTest {
	/*@Test
	public void testFtpClient() throws Exception {
		FTPClient client=new FTPClient();
		client.connect("192.168.37.133", 21);
		client.login("ftpuser","ftpuser");
		FileInputStream inputStream=new FileInputStream(new File("C:/Users/GongYuHao/Desktop/resoueces/素材/1.jpg"));
		client.changeWorkingDirectory("/home/ftpuser/www/");
		//修改上传文件格式 默认为文本格式文件
		client.setFileType(FTP.BINARY_FILE_TYPE);
		client.storeFile("hello.jpg",inputStream);
		client.logout();
	}*/
}
