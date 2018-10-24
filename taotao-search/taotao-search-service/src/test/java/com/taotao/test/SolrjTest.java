package com.taotao.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient.Builder;
import org.apache.solr.client.solrj.response.ClusteringResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.common.pojo.SearchItem;

public class SolrjTest {
	@Test
	public void solrjTest() {
		/*Builder builder = new HttpSolrClient.Builder("http://192.168.37.145:8080/solr/core-taotao");
		builder.build();*/
		ApplicationContext context=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-solr.xml");
		context.getBean("solrClient");
	/*	Builder buider =(Builder) context.getBean("clientBuilder");
		SolrClient client = buider.build();
		SolrClient c =(SolrClient) context.getBean("solrClient");*/
	}
	
	@Test
	public void solrjQueryTest() throws SolrServerException, IOException {
		//ApplicationContext context=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-solr.xml");
		/*Builder b =new HttpSolrClient.Builder("http://192.168.37.145:8080/solr/core-taotao");
		SolrClient c=b.build();
		SolrQuery query=new SolrQuery();
		query.setQuery("阿尔卡特");
		query.add("df","item_title");
		query.add("wt","json");
		QueryResponse query2 = c.query(query);
		SolrDocumentList results = query2.getResults();
		for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument.get("item_title"));
		}*/
		//System.out.println();
		Long a=6L;
		Long b=4L;
		System.out.println(a%b);
	}
	
	@Test
	public void hightTest() throws SolrServerException, IOException {
		Builder b =new HttpSolrClient.Builder("http://192.168.37.145:8080/solr/core-taotao");
		SolrClient c=b.build();
		
		
		SolrQuery query = new SolrQuery();

		query.setQuery("手机");
		query.set("df", "item_keywords");
		//设置高亮
		query.setHighlight(true);
		query.setHighlightSimplePre("<em syle=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		query.addHighlightField("item_title");
		
		QueryResponse query2 = c.query(query);
		Map<String, Map<String, List<String>>> highlighting = query2.getHighlighting();
		//System.out.println(highlighting);
		for(String id1:highlighting.keySet()) {
			String string = highlighting.get(id1).get("item_title").get(0);
			System.out.println(string);
			//System.out.println(id1);
		}
	}
	
	@Test
	public void testSoleCloud() throws IOException, SolrServerException {
		
		
		
		/*List<String> strs=new ArrayList<String>();
		strs.add("http://yhoo.fun:8180/solr");
		strs.add("http://yhoo.fun:8280/solr");
		strs.add("http://yhoo.fun:8380/solr");
		strs.add("http://yhoo.fun:8480/solr");*/
		ApplicationContext context=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-solr.xml");
		SolrClient client =(SolrClient) context.getBean("solrClient");
		//SolrClient client=new CloudSolrClient.Builder(strs).build();
		//SearchItem item=new SearchItem();
		/*item.setId(123L);
		item.setCategory_name("中国");
		item.setImage("asd");
		item.setTitle("中国");
		item.setPrice(123L);
		client.addBean("collection",item);
		client.commit("collection");
		client.close();*/
		SolrQuery query=new SolrQuery();
		query.setQuery("中国");
		query.set("df", "item_title");
		QueryResponse query2 = client.query("collection",query);

		List<SearchItem> beans = query2.getBeans(SearchItem.class);
		System.out.println(beans.get(0).getId());
	}
}
