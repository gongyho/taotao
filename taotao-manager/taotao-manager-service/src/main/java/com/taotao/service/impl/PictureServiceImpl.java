package com.taotao.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.service.PictureService;

/**
 * 图片服务
 * 
 * @author GongYuHao
 *
 */
@Service
public class PictureServiceImpl implements PictureService {
	/*
	 * @Value("${ftp.host}") private String host;
	 * 
	 * @Value("${ftp.port}") private Integer port;
	 * 
	 * @Value("${ftp.username}") private String username;
	 * 
	 * @Value("${ftp.password}") private String password;
	 * 
	 * @Value("${ftp.basePath}") private String basePath;
	 * 
	 * @Value("${image.baseUrl}") private String baseUrl;
	 */
	@Value("${fastdfs.tracker_servers}")
	private String TRACKER_SERVER;
	@Value("${fastdfs.image_server}")
	private String IMAGE_SERVER;

	/**
	 * 文件上传
	 */
	public Map<String, Object> uploadPicture(String fileName, byte[] bytes) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			/*
			 * InputStream inputStream=new ByteArrayInputStream(bytes); String[]
			 * filePathAndName =
			 * IDUtil.generateFileName(fileName.substring(fileName.lastIndexOf("."))); //
			 * 返回结果map
			 * 
			 * boolean success; success = FtpUtil.uploadFile(host, port, username, password,
			 * basePath, filePathAndName[0], filePathAndName[1],inputStream); if (success) {
			 * resultMap.put("error", 0); resultMap.put("url", baseUrl + filePathAndName[0]
			 * + "/" + filePathAndName[1]); } else { resultMap.put("error", 1);
			 * resultMap.put("message", "文件上传失败"); }
			 * 
			 * return resultMap;
			 */
			ClientGlobal.initByTrackers(TRACKER_SERVER);
			TrackerClient trackerClient = new TrackerClient();
			TrackerServer trackerServer = trackerClient.getConnection();
			StorageServer storageServer = null;
			StorageClient1 storageClient1 = new StorageClient1(trackerServer, storageServer);
			
			String extName=fileName.substring(fileName.lastIndexOf(".")+1);
			String url=storageClient1.upload_file1(null, bytes,extName,null);
			url = "http://"+IMAGE_SERVER+"/"+url;
			resultMap.put("error",0);
			resultMap.put("url", url);
			return resultMap;
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("error", 1);
			resultMap.put("message", "文件上传失败");
			return resultMap;
		}
	}
}
