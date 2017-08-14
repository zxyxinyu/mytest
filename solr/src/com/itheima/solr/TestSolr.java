package com.itheima.solr;

import static org.junit.Assert.*;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolr {

	/**
	 * 根据id域来更新Document的内容，如果根据id值搜索不到 id域则会执行添加操作，如果找到则会更新。
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateAndUpdateIndex() throws Exception {
		// 1.创建HttpSolrServer对象
		// 设置solr服务接口，浏览器客户端地址http://127.0.0.1:8080/solr

		String baseURL = "http://127.0.0.1:8080/solr";
		HttpSolrServer httpSolrServer = new HttpSolrServer(baseURL);

		// 2.创建SolrInputDocument对象
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "1");
		document.addField("name", "你好");

		// 3.把SolrInputDocument对象添加到索引库中
		httpSolrServer.add(document);
		httpSolrServer.commit();

	}

	@Test
	public void testDelete() throws Exception {
		String baseURL = "http://127.0.0.1:8080/solr";

		HttpSolrServer httpSolrServer = new HttpSolrServer(baseURL);

		// httpSolrServer.deleteById("1");
		httpSolrServer.deleteByQuery("*:*");
		httpSolrServer.commit();

	}

	public HttpSolrServer getHtttpSolrServer() {
		String baseURL = "http://127.0.0.1:8080/solr";

		HttpSolrServer httpSolrServer = new HttpSolrServer(baseURL);
		
		return httpSolrServer;
	}

	@Test
	public void testSearch() throws Exception {
		// 创建搜索对象
		SolrQuery query = new SolrQuery();
		// 设置搜索条件
		query.setQuery("*:*");

		// 发起搜索请求
		QueryResponse response = getHtttpSolrServer().query(query);

		// 处理搜索结果
		SolrDocumentList results = response.getResults();

		System.out.println("搜索到的结果总数：" + results.getNumFound());

		// 遍历搜索结果
		for (SolrDocument solrDocument : results) {
			System.out.println("id" + solrDocument.get("id"));
			System.out.println("title" + solrDocument.get("title"));
		}

	}

}
