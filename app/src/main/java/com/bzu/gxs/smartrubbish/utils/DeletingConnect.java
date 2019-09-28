package com.bzu.gxs.smartrubbish.utils;

import android.content.Context;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by pc on 2018/5/13.
 */

public class DeletingConnect {

    public static final String DELETE = "DELETE";
    /**
     * 请求URL并返回内容
     *
     * @param method
     *            方式get post
     * @param url
     *            地址

     *            参数
     * @return json
     */
    public static String request(Context context, String method, String url, String app_id, String token
                                ) throws Exception {
        HttpResponse response;
        String result_https="";


         if(method.equals(DELETE)){

            SSLSocketFactory ssl= DataManager.GETSSLinitHttpClientBook(context);

            HttpClient httpClient = new DefaultHttpClient();
            if (ssl != null) {
                Scheme sch = new Scheme("https", ssl, 443);
                httpClient.getConnectionManager().getSchemeRegistry().register(sch);
            }
           HttpDeleteWithBody httpDelete = new HttpDeleteWithBody(url);
            httpDelete.setHeader("app_key",app_id);
            httpDelete.setHeader("Authorization","Bearer "+token);
            httpDelete.setHeader("Content-Type","application/json");

            response = httpClient.execute(httpDelete);

            HttpEntity httpEntity = response.getEntity();
            // 通过EntityUtils并指定编码方式取到返回的数据
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                result_https = (EntityUtils.toString(httpEntity, "utf-8")); //进行转码
            }else{
                result_https=""+statusCode;
            }
        }
        return result_https;

    }

}
