package com.walmart.labs.pcs.normalize.utils;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by pzhong1 on 12/9/14.
 */
public class JsonUtil {
    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }


    public static <T> JsonNode getJson(T pgdf) {
        JsonNode jsonResponse = null;
        try {

            jsonResponse = getJsonWithException(pgdf);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }



    public static <T> JsonNode getJsonWithException(T obj)
            throws IllegalArgumentException {
        // TODO Auto-generated method stub
        JsonNode jsonResponse = objectMapper.valueToTree(obj);
        return jsonResponse;
    }

    public static void writeObjectToFile(String fileName, Object obj) throws JsonGenerationException, JsonMappingException, IOException {
        objectMapper.writeValue(new File(fileName), obj);

    }


    public static <T> T getObjectFromFile(String fname, Class<T> className) {
        // TODO Auto-generated method stub
        T obj = null;
        try {
            obj = objectMapper.readValue(new File(fname), className);
            // TODO :: log the exceptions.
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static <T> String writeValueAsString(T obj) {
        String str = "";
        try {
            str = objectMapper.writeValueAsString(obj);
            // TODO :: log the exceptions.
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static JsonNode readTree(String str) {
        try {
            return objectMapper.readTree(str);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objectMapper.createObjectNode();
    }

    public static <T> T getObjectFromString(String object, Class<T> className) {
        T obj = null;
        try {
            obj = objectMapper.readValue(object, className);
            // TODO :: log the exceptions.
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static <T> T getObjectFromStream(InputStream name, Class<T> className) {
        // TODO Auto-generated method stub
        T obj = null;
        try {
            obj= objectMapper.readValue(name, className);
            // TODO :: log the exceptions.
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }


    public static <T> void writeOjectToFile(T obj, final String fname) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(fname), obj);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static <T> void writeOjectToFile(T obj, final File fname) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(fname, obj);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> void saveJsonFile(T obj, String path, String dir){
        String fileName = null;
        if(path.indexOf("/") != -1){
            fileName = path.substring(path.lastIndexOf("/") + 1 );
        } else {
            fileName = path;
        }
        File resOutputFile = new File(dir + "/" + fileName);
        JsonUtil.writeOjectToFile(obj, resOutputFile);
    }

    /**
     * @param jsonString
     * @param classType
     * @return
     */
    public static <T> T convertToObject(String jsonString, Class<T> classType) throws IOException {
        return objectMapper.readValue(jsonString, classType);
    }
}
