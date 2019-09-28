package com.bzu.gxs.smartrubbish.utils;

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2018/5/4.
 */

public class NETCommned {
    public static final String REQUEST_TYPE_GET = "GET";
    public static final String REQUEST_TYPE_POST = "POST";
    public static final String REQUEST_TYPE_PUT = "PUT";

    /**
     * 请求URL并返回内容
     *
     * @param method
     *            方式get post
     * @param url
     *            地址
     * @param param
     *            参数
     * @return json
     */
    public static String request(Context context, String method, String url, String app_id, String token,
                                 List<NameValuePair> param,String ziduan) throws Exception {
        HttpResponse response;
        String result_https="";
        Log.i("nxsaina",method+" "+url+" "+app_id+" "+token+" "+ziduan);
        if (method.equals(REQUEST_TYPE_GET)){
            String ps ="";
            if (param != null) {
                List<NameValuePair> param2 = new ArrayList<NameValuePair>();
                for (int i = 0; i < param.size(); i++) {
                    if (i>0) ps+="&";
                    String key = param.get(i).getName();
                    String value =param.get(i)
                            .getValue();
                    ps+=key+"="+value;
                }

                ps = URLEncodedUtils.format(param2, HTTP.UTF_8);
                // 通过url创建对象
                if (url.indexOf("?") > 0) {
                    url += "&" + ps;
                } else {
                    url += "?" + ps;
                }
            }
            SSLSocketFactory ssl= DataManager.GETSSLinitHttpClientBook(context);

            HttpClient httpClient = new DefaultHttpClient();
            if (ssl != null) {
                Scheme sch = new Scheme("https", ssl, 443);
                httpClient.getConnectionManager().getSchemeRegistry().register(sch);
            }
            HttpGet request = new HttpGet(url);
            request.setHeader("app_key",app_id);
            request.setHeader("Authorization","Bearer "+token);
            request.setHeader("Content-Type","application/json");
//			request.setEntity(new UrlEncodedFormEntity(param));
            // 发起请求，获取回应，自封装接口，详见附录
            response = httpClient.execute(request);
            HttpEntity httpEntity = response.getEntity();

            // 得到一些数据

            // 通过EntityUtils并指定编码方式取到返回的数据

            StatusLine statusLine = response.getStatusLine();

            statusLine.getProtocolVersion();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                result_https = (EntityUtils.toString(httpEntity, "utf-8"));
            }else{
                result_https=""+statusCode;
            }

        }else if(method.equals(REQUEST_TYPE_POST)){

            SSLSocketFactory ssl= DataManager.GETSSLinitHttpClientBook(context);

            HttpClient httpClient = new DefaultHttpClient();
            if (ssl != null) {
                Scheme sch = new Scheme("https", ssl, 443);
                httpClient.getConnectionManager().getSchemeRegistry().register(sch);
            }
            HttpPost request = new HttpPost(url);
            if (param != null) {
                String JsonData="";
                String startdata="{";
                String enddata="}";
                String deviceId = "";
//              String expireTime="";
                String serviceId="";
                String methodId="";
                String Switch="";
                for (int i = 0; i < param.size(); i++) {
                    String key = param.get(i).getName();
                    String values = param.get(i).getValue();
                    if(key.equals("deviceId"))
                    {
                         deviceId="\""+key+"\":"+"\""+values+"\""+",";
                    }
//                    else if(key.equals("expireTime")){
//                        expireTime="\""+key+"\":"+"\""+values+"\""+",";//这是下发时间 这地方改了 DataManager也要改
//                    }
                    else if(key.equals("serviceId"))
                    {
                         serviceId="\""+key+"\":"+"\""+values+"\""+",";
                    }
                    else if(key.equals("method"))
                    {
                        methodId="\""+key+"\":"+"\""+values+"\""+",";
                    }
                    else if (key.equals(ziduan))
                    {
                        Switch="\""+key+"\":"+"\""+values+"\"";
                    }
                }
//                JsonData=startdata+deviceId+expireTime+"\"command\": {"+serviceId+methodId+"\"paras\": {"+Switch+enddata+enddata+enddata;
                JsonData=startdata+deviceId+"\"command\": {"+serviceId+methodId+"\"paras\": {"+Switch+enddata+enddata+enddata;
                Log.i("JsonData3",JsonData);
                request.setEntity(new StringEntity(JsonData, HTTP.UTF_8));

            }
            request.setHeader("app_key",app_id);
            request.setHeader("Authorization","Bearer "+token);
            request.setHeader("Content-Type","application/json");

            response = httpClient.execute(request);
            HttpEntity httpEntity = response.getEntity();
            // 通过EntityUtils并指定编码方式取到返回的数据
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                result_https = (EntityUtils.toString(httpEntity, "utf-8"));
            }else{
                result_https=""+statusCode;
            }
        }else if(method.equals(REQUEST_TYPE_PUT)){

            SSLSocketFactory ssl= DataManager.GETSSLinitHttpClientBook(context);

            HttpClient httpClient = new DefaultHttpClient();
            if (ssl != null) {
                Scheme sch = new Scheme("https", ssl, 443);
                httpClient.getConnectionManager().getSchemeRegistry().register(sch);
            }
            HttpPut request = new HttpPut(url);
            if (param != null) {
                String JsonData="";
                String startdata="{";
                String enddata="}";
//				List<NameValuePair> param2 = new ArrayList<NameValuePair>();
                for (int i = 0; i < param.size(); i++) {
                    String key = param.get(i).getName();
                    String values = param.get(i).getValue();
//					param2.add(new BasicNameValuePair(key, values));
                    if (i>0)
                        JsonData+=",";
                    if (key.equals("timeout"))
                        JsonData+="\""+key+"\":"+values;
                    else
                        JsonData+="\""+key+"\":\""+values+"\"";
                }
                //传入的是json格式的数据
                JsonData=startdata+JsonData+enddata;
                request.setEntity(new StringEntity(JsonData, HTTP.UTF_8));
//				request.setEntity(new UrlEncodedFormEntity(param2,
//						HTTP.UTF_8));
            }
            //
            request.setHeader("app_key",app_id);
            request.setHeader("Authorization","Bearer "+token);
            request.setHeader("Content-Type","application/json");

            response = httpClient.execute(request);
            HttpEntity httpEntity = response.getEntity();
            // 通过EntityUtils并指定编码方式取到返回的数据
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                result_https = (EntityUtils.toString(httpEntity, "utf-8"));
            }else{
                result_https=""+statusCode;
            }
        }
        else{
            Log.i("method==", ".....");
        }



        return result_https;

    }

}
