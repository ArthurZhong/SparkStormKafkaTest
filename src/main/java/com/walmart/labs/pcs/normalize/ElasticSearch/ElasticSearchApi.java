package com.walmart.labs.pcs.normalize.ElasticSearch;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.FilterBuilders.*;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryBuilders.*;
import org.elasticsearch.index.query.RangeFilterBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.script.ScriptService;

import java.util.List;
import java.util.Map;

/**
 * Created by pzhong1 on 1/16/15.
 */
public class ElasticSearchApi {
    ElasticSearchUtil elasticSearchUtil = new ElasticSearchUtil();
    SearchRequestBuilder searchRequestBuilder;
    public ElasticSearchApi(String ... args){
        searchRequestBuilder = elasticSearchUtil.getClient().prepareSearch();
        elasticSearchUtil.getClient().prepareSearch(args);
    }

    public ElasticSearchApi setSearchDocTypes(String ... types){
        searchRequestBuilder.setTypes(types);
        return this;
    }

    public ElasticSearchApi setSearchType(SearchType searchType){
        searchRequestBuilder.setSearchType(searchType);
        return this;
    }

    public SearchResponse search(TermsQueryBuilder termsQueryBuilder, RangeFilterBuilder rangeFilterBuilder, int size){
        SearchResponse response = searchRequestBuilder.setQuery(termsQueryBuilder).setPostFilter(rangeFilterBuilder)
                .setFrom(0).setSize(size).setExplain(true)
                .execute().actionGet();
        return response;
    }

    public SearchResponse search(Map<String, String> map){
        SearchResponse sr = elasticSearchUtil.getClient().prepareSearch()
                .setTemplateName("template")
                .setTemplateType(ScriptService.ScriptType.FILE)
                .setTemplateParams(map)
                .get();
        return sr;
    }
}
