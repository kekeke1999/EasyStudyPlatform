package com.keke.service;

import com.alibaba.fastjson.JSON;
import com.keke.config.StuElasticSearchConfig;
import com.keke.entities.Files;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
public class StuEsService {

    @Autowired
    private RestHighLevelClient client;

    public List selectKeyword(String word,String username) throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        //指定索引
        searchRequest.indices("files");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        HighlightBuilder highlightBuilder = new HighlightBuilder().field("*");
        highlightBuilder.preTags("<span style=\"color:blue\">");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("username",username));
        boolQueryBuilder.must(QueryBuilders.matchQuery("filename",word));


        searchSourceBuilder.query(boolQueryBuilder);

    //    searchSourceBuilder.query(QueryBuilders.matchQuery("filename",word));
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, StuElasticSearchConfig.COMMON_OPTIONS);
        SearchHits hits = searchResponse.getHits();
        List<Files> filesList = new LinkedList<>();
        for (SearchHit hit :
                hits) {
            HighlightField nameField = hit.getHighlightFields().get("filename");
            Text[] fragments = nameField.fragments();
            String nameTmp ="";
            for(Text text:fragments){
                nameTmp+=text;
            }
            System.out.println("变色了吗："+nameTmp);

            String str = hit.getSourceAsString();
            Files file = JSON.parseObject(str, Files.class);
            file.setFilename(nameTmp);
            System.out.println("file:"+file);
            filesList.add(file);
        }
        System.out.println(searchResponse.toString());
        return filesList;
    }

    public List selectAll() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        //指定索引
        searchRequest.indices("files");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, StuElasticSearchConfig.COMMON_OPTIONS);
        SearchHits hits = searchResponse.getHits();
        List<Files> filesList = new LinkedList<>();
        for (SearchHit hit :
                hits) {
            String str = hit.getSourceAsString();
            Files file = JSON.parseObject(str, Files.class);
            System.out.println("file:"+file);
            filesList.add(file);

        }
        System.out.println(searchResponse.toString());
        return filesList;
    }
}
