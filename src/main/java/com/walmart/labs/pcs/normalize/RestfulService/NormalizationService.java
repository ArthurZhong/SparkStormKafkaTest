package com.walmart.labs.pcs.normalize.RestfulService;

import com.google.gson.JsonElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;
import rx.Observable;

import java.io.UnsupportedEncodingException;

/*
 * Created by pzhong1 on 12/8/14.
 */
public class NormalizationService {
    private static String WEB_SERVICE_BASE_URL;
    private static final String WEB_SERVICE_PATH="/attribution/normalization";
    private NormalizationWebService normalizationWebService = null;

    private static final Logger logger = LogManager.getLogger(NormalizationService.class);


    public NormalizationService(String url) {
        WEB_SERVICE_BASE_URL = url;
        try {
            initIt();
        } catch (Exception e) {
            logger.error("init service client failed");
            e.printStackTrace();
        }
    }


    // retrofit observable
    public Observable<JsonElement> normalizeProduct(String productPcf) throws UnsupportedEncodingException {

        TypedInput in = new TypedByteArray("application/json", productPcf.getBytes("UTF-8"));

        return normalizationWebService.normalize(in);

    }


    // retrofit sync
    public JsonElement normalizeProductV2(String productPcf) throws Exception {

        TypedInput in = new TypedByteArray("application/json", productPcf.getBytes("UTF-8"));

        return normalizationWebService.normalizeV2(in);

    }


    private interface NormalizationWebService {
        @POST("/attribution/normalization")
        Observable<JsonElement> normalize(@Body TypedInput productPcf);

        @POST(WEB_SERVICE_PATH)
        JsonElement normalizeV2(@Body TypedInput productPcf);

    }

    class MyErrorHandler implements ErrorHandler {
        @Override public Throwable handleError(RetrofitError cause) {
            Response r = cause.getResponse();
            if (r != null && r.getStatus() == 401) {
                return new RuntimeException(cause);
            }
            return cause;
        }
    }

    public void initIt() throws Exception {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(WEB_SERVICE_BASE_URL)
                .setErrorHandler(new MyErrorHandler())
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .build();

        normalizationWebService = restAdapter.create(NormalizationWebService.class);

    }
}
