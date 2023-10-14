package com.keke;

import com.alibaba.fastjson.JSON;
import com.keke.config.StuElasticSearchConfig;
import com.keke.entities.Files;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StuSearchMain9001Tests {

    @Autowired
    private RestHighLevelClient client;

    /**
     * Copyright 2021 bejson.com
     */
    static class Account {

        private int account_number;
        private int balance;
        private String firstname;
        private String lastname;
        private int age;
        private String gender;
        private String address;
        private String employer;
        private String email;
        private String city;
        private String state;
        public void setAccount_number(int account_number) {
            this.account_number = account_number;
        }
        public int getAccount_number() {
            return account_number;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }
        public int getBalance() {
            return balance;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }
        public String getFirstname() {
            return firstname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }
        public String getLastname() {
            return lastname;
        }

        public void setAge(int age) {
            this.age = age;
        }
        public int getAge() {
            return age;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
        public String getGender() {
            return gender;
        }

        public void setAddress(String address) {
            this.address = address;
        }
        public String getAddress() {
            return address;
        }

        public void setEmployer(String employer) {
            this.employer = employer;
        }
        public String getEmployer() {
            return employer;
        }

        public void setEmail(String email) {
            this.email = email;
        }
        public String getEmail() {
            return email;
        }

        public void setCity(String city) {
            this.city = city;
        }
        public String getCity() {
            return city;
        }

        public void setState(String state) {
            this.state = state;
        }
        public String getState() {
            return state;
        }

        @Override
        public String toString() {
            return "Account{" +
                    "account_number=" + account_number +
                    ", balance=" + balance +
                    ", firstname='" + firstname + '\'' +
                    ", lastname='" + lastname + '\'' +
                    ", age=" + age +
                    ", gender='" + gender + '\'' +
                    ", address='" + address + '\'' +
                    ", employer='" + employer + '\'' +
                    ", email='" + email + '\'' +
                    ", city='" + city + '\'' +
                    ", state='" + state + '\'' +
                    '}';
        }
    }

    @Test
    public void contextLoads(){
        System.out.println(client);
    }

    class User{
        private String username;
        private String gender;
        private Integer age;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public User(String username, String gender, Integer age) {
            this.username = username;
            this.gender = gender;
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "username='" + username + '\'' +
                    ", gender='" + gender + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    @Test
    public void ss() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        //指定索引
        searchRequest.indices("files");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("username","kk"));
        boolQueryBuilder.must(QueryBuilders.matchQuery("filename","可可粉"));

        searchSourceBuilder.query(boolQueryBuilder);

        HighlightBuilder highlightBuilder = new HighlightBuilder().field("*");
        //highlightBuilder.preTags("<span style=\"color:red\">");
        //highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);

        //    searchSourceBuilder.query(QueryBuilders.matchQuery("filename",word));
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, StuElasticSearchConfig.COMMON_OPTIONS);
        SearchHits hits = searchResponse.getHits();
        List<Files> filesList = new LinkedList<>();
        for (SearchHit hit :
                hits) {
            HighlightField nameField = hit.getHighlightFields().get("filename");
            if(nameField!=null){
                Text[] fragments = nameField.fragments();
                String nameTmp ="";
                for(Text text:fragments){
                    nameTmp+=text;
                }
                System.out.println("变色了吗："+nameTmp);
            }

    //        System.out.println("变色了吗："+hit.getHighlightFields().get("filename"));
            String str = hit.getSourceAsString();
            Files file = JSON.parseObject(str, Files.class);
            System.out.println("file:"+file);
            filesList.add(file);
        }
    }


    /*
    存储、更新都可以
     */
    @Test
    public void indexData() throws IOException {
        IndexRequest indexRequest = new IndexRequest("users");
        indexRequest.id("1");
   //     indexRequest.source("username","zhangsan","age",18,"gender","m");
        User user = new User("zhangsans","f",17);
        String jsonString = JSON.toJSONString(user);
        indexRequest.source(jsonString, XContentType.JSON);

        IndexResponse index = client.index(indexRequest, StuElasticSearchConfig.COMMON_OPTIONS);

        System.out.println(index);
    }

    @Test
    public void aggregationData()throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        //指定索引
        searchRequest.indices("bank");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders.matchQuery("address", "mill"));

        searchSourceBuilder.aggregation(AggregationBuilders.terms("ageAgg").field("age").size(10));

        searchSourceBuilder.aggregation(AggregationBuilders.avg("balanceAvg").field("balance"));

        System.out.println("检索条件：" + searchSourceBuilder.toString());

        searchRequest.source(searchSourceBuilder);


        SearchResponse searchResponse = client.search(searchRequest, StuElasticSearchConfig.COMMON_OPTIONS);

        Aggregations aggregations1 = searchResponse.getAggregations();
        Terms ageAgg1 = aggregations1.get("ageAgg");
        for (Terms.Bucket bucket :
                ageAgg1.getBuckets()) {
            String keyAsString = bucket.getKeyAsString();
            System.out.println("年龄：" + keyAsString);
        }

        Avg balanceAvg = aggregations1.get("balanceAvg");
        System.out.println("平均薪资：" + balanceAvg.getValue());


        SearchHit[] searchHits = searchResponse.getHits().getHits();
        for (SearchHit hit :
                searchHits) {
            String str = hit.getSourceAsString();
            Account account = JSON.parseObject(str, Account.class);
            System.out.println("account:" + account);

            System.out.println(searchResponse.toString());
        }
    }

    @Test
    public void selectAll() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        //指定索引
        searchRequest.indices("files");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, StuElasticSearchConfig.COMMON_OPTIONS);
        SearchHits hits = searchResponse.getHits();

        for (SearchHit hit :
                hits) {
            String str = hit.getSourceAsString();
            Files file = JSON.parseObject(str, Files.class);
            System.out.println("file:"+file);
        }
        System.out.println(searchResponse.toString());
    }

    @Test
    public void searchData() throws IOException {

        SearchRequest searchRequest = new SearchRequest();
        //指定索引
        searchRequest.indices("bank");
        //指定DSL，检索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders.matchQuery("address","mill"));
        System.out.println(searchSourceBuilder.toString());
        //  searchSourceBuilder.aggregation();
      //  searchRequest.source(searchSourceBuilder);

        //执行检索
        SearchResponse searchResponse = client.search(searchRequest, StuElasticSearchConfig.COMMON_OPTIONS);

        System.out.println(searchResponse.toString());
        //分析结果

    }
}
