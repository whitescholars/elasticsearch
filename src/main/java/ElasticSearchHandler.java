import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by Mark on 2016/12/20.
 */
public class ElasticSearchHandler {

    //索引库名
    String index = "blog";
    //类型名称
    String type = "article";
    TransportClient client ;
    /**
     * 初始化客户端
     */
    @Before
    public void before(){
        client= MyTransportClient.getInstance().getTransportClient();
    }

    /**
     * 插入数据
     */
    @Test
    public void inSert() {
        //创建
        List<String> jsonData = DataFactory.getInitJsonData();
        for (int i = 0; i < jsonData.size(); i++) {
            IndexResponse response = client.prepareIndex(index, type).setSource(jsonData.get(i)).get();
            if (response.isCreated()) {
                System.out.println("创建成功!"+i);
            }
        }
    }
    /**
     * 根据条件查询
     */
    @Test
    public void searchWithCondtion(){
        SearchResponse searchResponse = client.prepareSearch(index)
                .setTypes(type)
                //***********************查询所有******************************
                //.setQuery(QueryBuilders.matchAllQuery())
                //***********查询某个字段中是否包含自己所想要的信息***************
                //.setQuery(QueryBuilders.matchQuery("title","简介"))
                //***************查询多个字段中是否包含对应的信息****************
                //.setQuery(QueryBuilders.multiMatchQuery("基本","content","title"))
                //*********************邻近匹配********************************
                //.setQuery(QueryBuilders.matchPhraseQuery("title","简介"))
                //****************************
                //****************************
                //****************************
                //****************************
                // ****************************
                //****************************
                //****************************
                //****************************
                //****************************
                //****************************
                //****************************
                //****************************
                //****************************




                //
               // .setQuery(QueryBuilders.matchAllQuery())
               //.setQuery(QueryBuilders.matchQuery("name", "tom").operator(Operator.AND)) //根据tom分词查询name,默认or
               // .setQuery(QueryBuilders.multiMatchQuery("tom", "name", "age")) //指定查询的字段
                //.setQuery(QueryBuilders.queryString("name:to* AND age:[0 TO 19]")) //根据条件查询,支持通配符大于等于0小于等于19
                //.setQuery(QueryBuilders.termQuery("name", "tom"))//查询时不分词
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setFrom(0).setSize(10)//分页
                .addSort("id", SortOrder.DESC)//排序
                .get();
        SearchHits hits = searchResponse.getHits();
        long total = hits.getTotalHits();
        System.out.println(total);
        SearchHit[] searchHits = hits.hits();
        for(SearchHit s : searchHits)
        {
            System.out.println(s.getSourceAsString());
        }
    }
    /**
     * 释放客户端
     */
    @After
    public void After(){
       if(client!=null){
           client.close();
       }
    }
}
