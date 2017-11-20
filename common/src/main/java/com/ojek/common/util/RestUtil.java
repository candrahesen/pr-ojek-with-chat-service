package com.ojek.common.util;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RestUtil {

    public static String httpPost(String url, Map<String,String> parameters) {
        try {
            HttpPost request = new HttpPost(url);
            CloseableHttpClient httpclient = HttpClients.createDefault();

            List<NameValuePair> nvps = new ArrayList<>();
            for (Map.Entry<String,String> entry : parameters.entrySet())
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            request.setEntity(new UrlEncodedFormEntity(nvps));

            CloseableHttpResponse response = httpclient.execute(request);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = entity.getContent();
                if (inputStream != null)
                    return IOUtils.toString(inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
