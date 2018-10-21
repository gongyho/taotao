package com.taotao.test;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.junit.Test;

public class SolrjTest {
	@Test
	public void solrjTest() {
		SolrClient client =new HttpSolrClient.Builder("http://192.168.37.145:8080/solr/core-taotao").build();
	}
}
