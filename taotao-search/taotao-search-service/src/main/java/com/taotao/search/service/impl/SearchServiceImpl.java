package com.taotao.search.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;
import com.taotao.search.mapper.SearchMapper;
import com.taotao.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {
	@Autowired
	private SearchMapper searchMapper;
	@Autowired
	private SolrClient solrClient;
	@Value("${DEFAULT_COLLECTION}")
	private String DEFAULT_COLLECTION;

	public void importAllItem() throws Exception {
		// 创建文档
		List<SearchItem> list = searchMapper.searchAllItem();
		solrClient.addBeans(DEFAULT_COLLECTION,list);
		// 提交
		solrClient.commit(DEFAULT_COLLECTION);
		// 关闭连接
		solrClient.close();
	}
	/**
	 * 搜索
	 */
	public SearchResult searchItem(String q, Integer page, Integer rows) throws Exception {
		SolrQuery query = new SolrQuery();

		query.setQuery(q);
		query.setStart((page - 1) * rows);
		query.setRows(rows);
		query.set("df", "item_keywords");
		//设置高亮
		query.setHighlight(true);
		query.setHighlightSimplePre("<em syle=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		query.addHighlightField("item_title");
		//搜索
		QueryResponse queryResponse = solrClient.query(DEFAULT_COLLECTION,query);
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		List<SearchItem> list = queryResponse.getBeans(SearchItem.class);
		//设置高亮
		for (SearchItem item : list) {
			List<String> titles = highlighting.get(item.getId()).get("item_title");
			if(titles!=null && titles.size()>0) {
				item.setTitle(titles.get(0));
			}
		}
		
		//设置
		SearchResult result=new SearchResult();
		result.setItemList(list);
		result.setRecordCount(solrDocumentList.getNumFound());
		Long pageCount = result.getRecordCount()/rows;
		result.setPageCount(result.getRecordCount()%rows ==0 ? pageCount : pageCount+1);
		
		return result;
	}
	/*
	private SearchResult search(SolrQuery query) throws Exception {
		SearchResult searchResult = new SearchResult();
		QueryResponse queryResponse = solrClient.query(query);
		SolrDocumentList results = queryResponse.getResults();

		// 设置总记录数
		searchResult.setRecordCount(results.getNumFound());
		List<SearchItem> itemList = new ArrayList<SearchItem>();
		SearchItem item = null;

		// 取高亮
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		for (SolrDocument doc : results) {
			item = new SearchItem();
			item.setId(Long.valueOf(doc.get("id").toString()));
			item.setImage(doc.get("item_image").toString());
			item.setPrice(Long.valueOf(doc.get("item_price").toString()));
			item.setSell_point(doc.get("item_sell_point").toString());
			item.setCategory_name(doc.get("item_category_name").toString());

			// 判断高亮
			List<String> strs = highlighting.get(doc.get("id").toString()).get("item_title");
			if (strs != null && strs.size() > 0) {
				item.setTitle(strs.get(0));
			} else {
				item.setTitle(doc.get("item_title").toString());
			}

			itemList.add(item);
		}
		searchResult.setItemList(itemList);
		solrClient.close();
		return searchResult;
	}
	*/
}
