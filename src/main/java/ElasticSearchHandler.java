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
    String index = "dec_v1.3";
    //类型名称
    String type = "book";
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
                //.setQuery(QueryBuilders.matchPhraseQuery("title", "she"))
                //************************************************************
                // 如果你调用matchPhrasePrefixQuery时，text为中文
                // 那么，很大可能是一种状况：你会发现，matchPhraseQuery和matchPhrasePrefixQuery没有任何差别。
                // 而当text为英文时，差别就显现出来了：matchPhraseQuery的text是一个英文单词,
                // 而matchPhrasePrefixQuery的text则无这一约束，你可以从一个英文单词中抽几个连接在一起的字母进行查询
                //************************************************************
                //.setQuery(QueryBuilders.matchPhrasePrefixQuery("title", "she"))
                //*********************************************************
                //.setQuery(QueryBuilders.disMaxQuery()
                //        .add(QueryBuilders.termQuery("title", "sh"))
                //        .add(QueryBuilders.termQuery("content", "什")))
                //*********************指定type和id进行查询*****************
                //.setQuery(QueryBuilders.idsQuery(type).ids("AVkpRPVGpo__GCi0PPMM", "AVkpRPS9po__GCi0PPML"))
                //***********************模糊查询***************************
                //.setQuery(QueryBuilders.fuzzyQuery("title", "介绍"))
                // **********************前缀查询***************************
                // .setQuery(QueryBuilders.prefixQuery("content", "基本"))
                //*************查询一个文档的范围,判断是否包含上下界***********
                //.setQuery(QueryBuilders.rangeQuery("sum").from(20).to(30).includeLower(true).includeUpper(false))
                //**********************通配符查询**************************
                //.setQuery(QueryBuilders.wildcardQuery("title", "*基本*"))
                //************************全文检索**************************
                //.setQuery(QueryBuilders.queryStringQuery("ssh or shell"))
                //***/must-->and/mustNot-->or/should-->包含should的内容分数会更高 ***
                // .setQuery(QueryBuilders.boolQuery()
                                //.must(QueryBuilders.termQuery("title","介绍"))
                               // .should(QueryBuilders.termQuery("content", "意义"))
                //)
                //**************全文检测 比queryStringQuery更健壮**************
                //.setQuery(QueryBuilders.simpleQueryStringQuery("ssh shell"))
                //****************************
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setFrom(0).setSize(10)//分页
                .addSort("id", SortOrder.DESC)//排序*/
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
