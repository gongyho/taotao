package com.taotao.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.SearchItem;
import com.taotao.search.mapper.SearchMapper;
import com.taotao.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {
	@Autowired
	private SearchMapper searchMapper;
	@Value("${SOLR_BASE_URL}")
	private String SOLR_BASE_URL;
	
	
	public void importAllItem() throws Exception {
		HttpSolrClient client=new HttpSolrClient.Builder(SOLR_BASE_URL).build();
		//创建文档
		SolrInputDocument document=null;
		List<SearchItem> list = searchMapper.searchAllItem();
		for (SearchItem item : list) {
			document =new SolrInputDocument();
			//id域不能少
			document.addField("id",item.getId());
			document.addField("item_title",item.getTitle());
			document.addField("item_sell_point",item.getSell_point());
			document.addField("item_image", item.getImage());
			document.addField("item_price",item.getPrice());
			document.addField("item_category_name",item.getCategory_name());
			document.addField("item_desc",item.getItem_desc());
			client.add(document);
		}
		//提交
		client.commit();
		//关闭连接
		client.close();
	}
}
