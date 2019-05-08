package com.mytest.demo;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.*;

//个人对ES的测试类
@RunWith(SpringJUnit4ClassRunner.class) /*添加SpringJUnit支持，引入Spring-Test框架*/
@SpringBootTest(classes = MyTest.class) /*指定Springboot启动类启动*/
public class ES_Test
{
	RestHighLevelClient client = new RestHighLevelClient(
			RestClient.builder(
					new HttpHost("192.168.1.22",9206,"http")
			)
	);

	@Test
	public void ES_put() throws IOException{

		Map<String,Object> jsonMap = new HashMap<>();
		jsonMap.put("user","wangjiangqz");
		jsonMap.put("postDate",new Date());
		jsonMap.put("mark","to be a better man!");

		IndexRequest request = new IndexRequest("test","doc","1").source(jsonMap);

		try{
			IndexResponse response = client.index(request, RequestOptions.DEFAULT);
			System.out.println(response.getResult());

		}catch (ElasticsearchException e){
			if(e.status() == RestStatus.CONFLICT){
				//todo
			}
		}

		client.close();

	}

	@Test
	public void ES_get() throws IOException{
		GetRequest request = new GetRequest("test","doc","1");
		try
		{
			GetResponse response = client.get(request,RequestOptions.DEFAULT);
			System.out.println(response.getSourceAsString());

		}catch (ElasticsearchException e){
			if(e.status() == RestStatus.CONFLICT){
				//todo
			}
		}
	}

	@Test
	public void ES_exists() throws IOException{
		GetRequest request = new GetRequest("test","doc","1");
		request.fetchSourceContext(new FetchSourceContext(false));
		request.storedFields("_none_");

		try
		{
			Boolean exists = client.exists(request,RequestOptions.DEFAULT);
			System.out.println(exists);

		}catch (ElasticsearchException e){
			if(e.status() == RestStatus.CONFLICT){
				//todo
			}
		}
	}

	@Test
	public void ES_delete() throws IOException{
		DeleteRequest request = new DeleteRequest("test","doc","1");

			DeleteResponse response = client.delete(request,RequestOptions.DEFAULT);
			System.out.println(response.getResult());

			if(response.getResult() == DocWriteResponse.Result.NOT_FOUND){
				System.out.println("id不存在");
		}
	}

	@Test
	public void ES_update() throws IOException{

		Map<String,Object> jsonMap = new HashMap<>();
		jsonMap.put("user","wangjiangqz");
		jsonMap.put("postDate",new Date());
		jsonMap.put("mark","to be a rich man!");
		jsonMap.put("wish","to learn more about java");

		UpdateRequest request = new UpdateRequest("test","doc","1").doc(jsonMap);
		UpdateResponse response = client.update(request,RequestOptions.DEFAULT);

		System.out.println(response.getResult());
	}

	@Test
	public void ES_bulk() throws IOException{

		Map<String,Object> jsonMap = new HashMap<>();
		jsonMap.put("user","wangjiangqz10086");
		jsonMap.put("postDate",new Date());
		jsonMap.put("mark","china yidong");
		jsonMap.put("wish","to learn more about java");

		BulkRequest request = new BulkRequest();

		request.add(new UpdateRequest("test","doc","1").doc(jsonMap));
		request.add(new IndexRequest("test","doc","2")
				.source("user","wangjiangqz10010","postDate",new Date(),"mark","china liantong"));
		request.add(new IndexRequest("test","doc","2")
				.source("user","wangjiangqz10000","postDate",new Date(),"mark","china dianxin"));

		BulkResponse response = client.bulk(request,RequestOptions.DEFAULT);

		for (BulkItemResponse res : response){
			DocWriteResponse itenResponse = res.getResponse();

			if(res.getOpType() == DocWriteRequest.OpType.INDEX || res.getOpType() == DocWriteRequest.OpType.CREATE){
				IndexResponse indexResponse = (IndexResponse) itenResponse;
				System.out.println(indexResponse.getResult());

			}else if (res.getOpType() == DocWriteRequest.OpType.UPDATE){
				UpdateResponse updateResponse = (UpdateResponse) itenResponse;
				System.out.println(updateResponse.getResult());

			}else if (res.getOpType() == DocWriteRequest.OpType.DELETE){
				DeleteResponse deleteResponse = (DeleteResponse) itenResponse;
				System.out.println(deleteResponse.getResult());

			}

		}
	}

	@Test
	public void ES_muilti_get() throws IOException{

		MultiGetRequest request = new MultiGetRequest();
		request.add(new MultiGetRequest.Item("test","doc","1"));
		request.add(new MultiGetRequest.Item("test","doc","2"));
		MultiGetResponse response = client.mget(request,RequestOptions.DEFAULT);
		try
		{
			Arrays.stream(response.getResponses()).map(p -> p.getResponse().getSourceAsString()).forEach(System.out::println);
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	@Test
	public void ES_search_all() throws IOException{

		SearchRequest request = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		request.source(searchSourceBuilder);

		SearchResponse response = client.search(request,RequestOptions.DEFAULT);
		SearchHits hits = response.getHits();
		Arrays.stream(hits.getHits()).map(p->p.getSourceAsString()).forEach(System.out::println);
	}

	@Test
	public void ES_search() throws IOException{

		SearchRequest request = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		//多条件组合查询
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		//分词查询
		WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("activity_id","A0719031113332301226");
		//完整匹配
		TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("act_item_id","A0219031113484701518");
		//嵌套查询
		BoolQueryBuilder boolQueryBuilderIn = QueryBuilders.boolQuery();
		String[] strings = {"6","7"};
		List<String> list = Arrays.asList(strings);
		TermsQueryBuilder termsQueryBuilderIn = QueryBuilders.termsQuery("status",list);
		boolQueryBuilderIn.should(termsQueryBuilderIn);
		BoolQueryBuilder boolQueryBuilderInIn = QueryBuilders.boolQuery();
		PrefixQueryBuilder prefixQueryBuilder = QueryBuilders.prefixQuery("realname","王");
		boolQueryBuilderInIn.mustNot(termsQueryBuilderIn);
		boolQueryBuilderInIn.must(prefixQueryBuilder);
		boolQueryBuilderIn.should(boolQueryBuilderInIn);
		//should表示或
		boolQueryBuilder.should(wildcardQueryBuilder);
		boolQueryBuilder.should(termQueryBuilder);
		boolQueryBuilder.must(boolQueryBuilderIn);
		//添加查询个数限制
		searchSourceBuilder.from(0);
		searchSourceBuilder.size(50);
		//添加列的限制
		String[] includs = {"act_group_id","act_item_id","activity_id","adult_cnt","realname","status"};
		String[] excluds = {"child_cnt"};
		searchSourceBuilder.fetchSource(includs,excluds);
		//添加排序
		SortBuilder sortBuilder = SortBuilders.fieldSort("create_date").order(SortOrder.DESC);
		searchSourceBuilder.sort(sortBuilder);
		//把组合条件添加为查询条件
		searchSourceBuilder.query(boolQueryBuilder);
		request.indices("maitao_orders");
		request.source(searchSourceBuilder);


		SearchResponse response = client.search(request,RequestOptions.DEFAULT);
		SearchHits hits = response.getHits();
		Arrays.stream(hits.getHits()).map(p->p.getSourceAsString()).forEach(System.out::println);
	}

}

