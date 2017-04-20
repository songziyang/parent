package com.ydzb.core.utils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * HttpClient工具类
 */
public class HttpClientUtil {

    static Logger logger = Logger.getLogger(HttpClientUtil.class);


    /**
     * 发送POST请求
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String POST(String url, Map<String, String> params) throws Exception {
        URIBuilder builder = new URIBuilder().setPath(url);
        // clear the params with empty value
        Map<String, String> trimmedParams = new HashMap<>();
        for (String key : params.keySet()) {
            if (params.get(key) != null) {
                trimmedParams.put(key, params.get(key));
            }
        }
        try {
            URI uri = builder.build();
            RequestBuilder requestBuilder = RequestBuilder.post();
            requestBuilder.setUri(uri);
            List<NameValuePair> kvs = new ArrayList<>();
            for (String key : trimmedParams.keySet()) {
                kvs.add(new BasicNameValuePair(key, trimmedParams.get(key)));
            }
            requestBuilder.setEntity(new UrlEncodedFormEntity(kvs, "UTF-8"));
            HttpUriRequest request = requestBuilder.build();
            RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(2000).setConnectTimeout(2000).setSocketTimeout(2000).build();
            HttpResponse resp = HttpClients.custom().setDefaultRequestConfig(requestConfig).build().execute(request);
            logger.info("post: " + resp);
            if (resp.getStatusLine().getStatusCode() >= 300) {
                logger.info("Something wrong: " + resp.getStatusLine().toString());
            }
            BufferedReader input = new BufferedReader(new InputStreamReader(resp.getEntity().getContent(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            char[] buf = new char[1000];
            int count;
            while ((count = input.read(buf)) > 0) {
                sb.append(buf, 0, count);
            }
            logger.info("==post result==" + sb.toString());
            return sb.toString();
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}