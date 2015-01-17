package com.walmart.labs.pcs.normalize.ElasticSearch;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by pzhong1 on 1/16/15.
 */
public class ElasticSearchUtil {
    private TransportClient transportClient;

    ElasticSearchUtil(){
        Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "localtestsearch").build();
        transportClient = new TransportClient(settings);
        transportClient = transportClient.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
    }

    public Client getClient(){
        return (Client) transportClient;
    }

    public void addIndex(String indexName){
        CreateIndexRequestBuilder createIndexRequestBuilder = getClient().admin().indices().prepareCreate(indexName);
        createIndexRequestBuilder.execute().actionGet();
    }

    public String getDocuments(String indexName, String documentType, String documentId, String key){
        try {
            GetRequestBuilder getRequestBuilder = getClient().prepareGet(indexName, documentType, documentId);
            getRequestBuilder.setFields(new String[]{key});
            GetResponse response = getRequestBuilder.execute().actionGet();
            String name = response.getField(key).getValue().toString();
            return name;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void addDocuments(Map<String, String> map, String indexName, String documentType, String documentId){
        try {
            if(map == null || map.size() == 0){
                return;
            }

            XContentBuilder builder = jsonBuilder().startObject();
            for(String key : map.keySet()){
                builder.field(key, map.get(key));
            }
            builder.endObject();

            IndexRequestBuilder indexRequestBuilder = getClient().prepareIndex(indexName, documentType, documentId);
            IndexResponse response = indexRequestBuilder.setSource(builder).execute().actionGet();

            // Index name
            String _index = response.getIndex();
            // Type name
            String _type = response.getType();
            // Document ID (generated or not)
            String _id = response.getId();
            // Version (if it's the first time you index this document, you will get: 1)
            long _version = response.getVersion();

            System.out.println("Successfully indexed index: " + _index + " ,type: " + _type + " , id: " + _id + " ,version: " + _version);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addBulkDocuments(Map<String, Map<String, Map<String, Map<String, String>>>> map){
        BulkRequestBuilder bulkRequest = getClient().prepareBulk();

        try {

            if(map != null && map.size() > 0){
                for(String indexName : map.keySet()){
                    for(String docType : map.get(indexName).keySet()){
                        for(String docId : map.get(indexName).get(docType).keySet()){
                            XContentBuilder builder = jsonBuilder().startObject();
                            for(String key : map.get(indexName).get(docType).get(docId).keySet()){
                                builder.field(key, map.get(indexName).get(docType).get(docId).get(key));
                            }
                            builder.endObject();
                            IndexRequestBuilder indexRequestBuilder = getClient().prepareIndex(indexName, docType, docId);
                            indexRequestBuilder.setSource(builder);
                            bulkRequest.add(indexRequestBuilder);
                        }
                    }
                }
            }
            BulkResponse bulkResponse = bulkRequest.execute().actionGet();
            if (bulkResponse.hasFailures()) {
                System.out.println("bulk insertion failed!");
                // process failures by iterating through each bulk response item
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteDocuments(String indexName, String documentType, String documentId){
        try {

            DeleteRequestBuilder deleteRequestBuilder = getClient().prepareDelete(indexName, documentType, documentId);
            DeleteResponse response = deleteRequestBuilder.execute().actionGet();

            // Index name
            String _index = response.getIndex();
            // Type name
            String _type = response.getType();
            // Document ID (generated or not)
            String _id = response.getId();
            // Version (if it's the first time you index this document, you will get: 1)
            long _version = response.getVersion();

            System.out.println("Successfully deleted index: " + _index + " ,type: " + _type + " , id: " + _id + " ,version: " + _version);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateDocuments(String indexName, String documentType, String documentId, String key, String value){
        try {
            UpdateRequest updateRequest = new UpdateRequest();
            updateRequest.index(indexName);
            updateRequest.type(documentType);
            updateRequest.id(documentId);
            updateRequest.doc(jsonBuilder()
                    .startObject()
                    .field(key, value)
                    .endObject());
            getClient().update(updateRequest).get();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void updateDocumentsByMerging(String indexName, String documentType, String documentId, String key, String value){
        try {
            UpdateRequest updateRequest = new UpdateRequest(indexName, documentType, documentId)
                    .doc(jsonBuilder()
                            .startObject()
                            .field(key, value)
                            .endObject());
            getClient().update(updateRequest).get();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void upSertDocuments(String indexName, String documentType, String documentId, String key1, String value1, String key2, String value2){
        try {
            IndexRequest indexRequest = new IndexRequest(indexName, documentType, documentId)
                    .source(jsonBuilder()
                            .startObject()
                            .field(key1, value1)
                            .field(key2, value2)
                            .endObject());
            UpdateRequest updateRequest = new UpdateRequest(indexName, documentType, documentId)
                    .doc(jsonBuilder()
                            .startObject()
                            .field(key2, value2)
                            .endObject())
                    .upsert(indexRequest);
            getClient().update(updateRequest).get();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
