package com.taotao.search.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.taotao.common.pojo.SearchItem;
import com.taotao.search.mapper.SearchMapper;

public class ItemChangedListener implements MessageListener{
	@Autowired
	private SearchMapper searchMapper;
	@Autowired
	private SolrClient solrClient;
	@Value("${DEFAULT_COLLECTION}")
	private String DEFAULT_COLLECTION;
	
	@Override
	public void onMessage(Message message) {
		try {
			Long id= Long.valueOf(((TextMessage)message).getText());
			SearchItem item =searchMapper.selectById(id);
			solrClient.deleteById(DEFAULT_COLLECTION,id.toString());
			solrClient.addBean(DEFAULT_COLLECTION, item);
			solrClient.commit(DEFAULT_COLLECTION);
			System.out.println("更新索引-------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
