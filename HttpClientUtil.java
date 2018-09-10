package com.mhc.gwsti.biz.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;

/**
 * httpclient post 传json返回json
 **/
@Slf4j
public class HttpClientUtil {
    /**
     * 远程调用接口，并返回json数据
     *
     * @param url
     * @param json
     * @return
     * @throws Exception
     */
    public static String httpPostWithJson(String url, String json) throws Exception {
        log.info("url:{},\njson{}", url, json);
        url = url.trim();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient client = buildHttpsClient();

        StringEntity entity = new StringEntity(json, "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);

        HttpResponse resp = client.execute(httpPost);
        if (resp.getStatusLine().getStatusCode() == 200) {
            HttpEntity he = resp.getEntity();
            String respContent = EntityUtils.toString(he, "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(respContent);
            if (isPostFail(jsonObject)) {
                throw new RuntimeException(jsonObject.toJSONString());
            }

            return jsonObject.toJSONString();
        }

        throw new RuntimeException("code: " + resp.getStatusLine().getStatusCode() + "\nurl: " + url);
    }

    private static boolean isPostFail(JSONObject jsonObject) {
        if (jsonObject.containsKey("code")) {
            return jsonObject.getInteger("code") != 0;
        } else if (jsonObject.containsKey("status")) {
            return !"00".equals(jsonObject.getString("status"));
        }
        return true;
    }

    public static HttpEntity httpGet(String url) {
        try {
            if (!url.startsWith("http:") && !url.startsWith("https:")) {
                url = "https:" + url;
            }
            url = url.trim();
            log.info("连接下载: " + url);
            CloseableHttpClient client;
            if (url != null && url.toLowerCase().startsWith("https")) {
                client = buildHttpsClient();
            } else {
                client = HttpClients.createDefault();
            }
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = client.execute(httpGet);
            return response.getEntity();
        } catch (IOException ioException) {
            log.error("IO获取文件异常", ioException);
        } catch (Exception exception) {
            log.error("获取文件异常", exception);
        }
        return null;
    }

    public static HttpResponse httpGetRes(String url) {
        try {
            if (!url.startsWith("http:") && !url.startsWith("https:")) {
                url = "https:" + url;
            }
            url = url.trim();
            log.info("连接下载: " + url);
            CloseableHttpClient client;
            if (url != null && url.toLowerCase().startsWith("https")) {
                client = buildHttpsClient();
            } else {
                client = HttpClients.createDefault();
            }
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = client.execute(httpGet);
            return response;
        } catch (IOException ioException) {
            log.error("IO获取文件异常", ioException);
        } catch (Exception exception) {
            log.error("获取文件异常", exception);
        }
        return null;
    }

    /**
     * httpGet with headers
     *
     * @param url
     * @param headers
     * @return
     */
    public static String httpGet(String url, Map<String, String> headers) {
        try {
            log.info("httpGet start, url = {}, headers = {} ", url, headers);
            CloseableHttpClient client = buildHttpsClient();
            HttpGet httpGet = new HttpGet(url);
            Set<String> keySet = headers.keySet();
            for (String key : keySet) {
                httpGet.addHeader(key, headers.get(key));
            }
            HttpResponse response = client.execute(httpGet);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            log.error("httpGet exception", e);
        }
        return null;
    }


    /**
     * @param url
     * @param data
     * @return
     */
    public static String httpPostWithoutHeaders(String url, String data) {
        try {
            log.info("httpPost start, url = {}, data = {} ", url, data);
            CloseableHttpClient client = buildHttpsClient();
            HttpPost httpPost = new HttpPost(url);

            StringEntity entity = new StringEntity(data, "UTF-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);

            HttpResponse response = client.execute(httpPost);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            log.error("httpPost exception", e);
        }
        return null;
    }


    /**
     * httpPost with headers
     *
     * @param url
     * @param data
     * @param headers
     * @return
     */
    public static String httpPostWithHeaders(String url, String data, Map<String, String> headers) {
        try {
            log.info("httpPost start, url = {}, data = {}, headers = {} ", url, data, headers);
            CloseableHttpClient client = buildHttpsClient();
            HttpPost httpPost = new HttpPost(url);
            Set<String> keySet = headers.keySet();
            for (String key : keySet) {
                httpPost.addHeader(key, headers.get(key));
            }

            StringEntity entity = new StringEntity(data, "UTF-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);

            HttpResponse response = client.execute(httpPost);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            log.error("httpPost exception", e);
        }
        return null;
    }


    private static CloseableHttpClient buildHttpsClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            public boolean isTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {
                return true;
            }
        }).build();
        httpClientBuilder.setSSLContext(sslContext);

        return httpClientBuilder.build();
    }


    private static CloseableHttpClient buildHttpClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        return HttpClients.createDefault();
    }
}
