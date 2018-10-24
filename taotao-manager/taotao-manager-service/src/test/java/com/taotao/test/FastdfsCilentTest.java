package com.taotao.test;

import java.io.File;
import java.io.FileInputStream;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

public class FastdfsCilentTest {
	@Test
	public void testUpload() throws Exception {
			
			ClientGlobal.initByTrackers("39.106.55.253:22122");
			
			TrackerClient trackerClient=new TrackerClient();
			TrackerServer trackerServer = trackerClient.getConnection();
			StorageServer storageServer=null;
			StorageClient1 storageClient1 = new StorageClient1(trackerServer, storageServer);
			
			File file=new File("C:\\Users\\GongYuHao\\Desktop\\1.jpg");
			FileInputStream in=new FileInputStream(file);
			byte[] bytes=new byte[(int) file.length()];
			in.read(bytes);
			String lo=storageClient1.upload_file1(null, bytes, "jpg",null);
			System.out.println(lo);
	}
}
