package com.bzu.gxs.smartrubbish.utils;

import android.content.Context;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcz on 2017/10/10.
 */

public class NetConnectHttp {

    public static final String REQUEST_TYPE_GET = "GET";
    public static final String REQUEST_TYPE_POST = "POST";
    //     Http
    public static Cookie appCookie;

    public static String request(Context context, String method, String url, List<NameValuePair> param
    ) throws Exception {
        HttpContext ct = new BasicHttpContext();

        HttpResponse httpResponse = null;
        String result = new String();

        try {

            if (method.equals(REQUEST_TYPE_GET)) {

                if (param != null) {
                    List<NameValuePair> param2 = new ArrayList<NameValuePair>();
                    for (int i = 0; i < param.size(); i++) {
                        String key = param.get(i).getName();
                        String value = "";
                        if (!param.get(i).getValue().equals("")) {
                            value = param.get(i)
                                    .getValue();//这里转换  如果出现中文  会有识别乱码的问题
                            //value = param.get(i).getValue();
                        } else {
                            value = "";
                        }
                        param2.add(new BasicNameValuePair(key, value));
                    }

                    String ps = URLEncodedUtils.format(param2, "UTF-8");
                    if (url.indexOf("?") > 0) {
                        url += "&" + ps;
                    } else {
                        url += "?" + ps;
                    }
                }
                HttpGet httpGet = new HttpGet(url);

                CookieStore cookieStore = new BasicCookieStore();
                ct.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
//                if (sessionid != null) {
//                    httpGet.setHeader("Cookie", "JSESSIONID=" + sessionid);
//                }
                httpGet.setHeader("USER-AGENT", "mobile");
                DefaultHttpClient httpClient = new DefaultHttpClient();
//                httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
//                         10000);
//                httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
//                         10000 );
                HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),8*1000);
                HttpConnectionParams.setSoTimeout(httpClient.getParams(), 30*1000);
                httpResponse = httpClient.execute(httpGet, ct);
                // httpEntiy
                HttpEntity httpEntity = httpResponse.getEntity();
                StatusLine statusLine = httpResponse.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if (statusCode == 200) {
                    result = (EntityUtils.toString(httpEntity, "utf-8"));
                    result = result.trim();
                    result = result.replace("\t", "");
                    result = result.replace("\n", "");
                    result = result.replace("\r", "");
                    result = result.replaceAll("\\\"", "\\\\\\\"");
                    // result=replaceBlank(result);
                    List<Cookie> cookies = cookieStore.getCookies();
                    if (!cookies.isEmpty()) {
                        for (int i = cookies.size(); i > 0; i--) {
                            Cookie cookie = cookies.get(i - 1);
                            if (cookie.getName().equalsIgnoreCase("jsessionid")) {
                                appCookie = cookie;
                            }
                        }
                    }
                } else {
                    throw new Exception("网络错误");
                }

            } else if (method.equals(REQUEST_TYPE_POST)) {
                HttpPost httpPost = new HttpPost(url);
                HttpClient httpClient = new DefaultHttpClient();
                if (param != null) {
                    List<NameValuePair> param2 = new ArrayList<NameValuePair>();
                    for (int i = 0; i < param.size(); i++) {
                        String key = param.get(i).getName();
                        String value = param.get(i).getValue();
                        param2.add(new BasicNameValuePair(key, value));
                    }

                    httpPost.setEntity(new UrlEncodedFormEntity(param2,
                            "UTF-8"));
                }
                httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                StatusLine statusLine = httpResponse.getStatusLine();

                statusLine.getProtocolVersion();
                int statusCode = statusLine.getStatusCode();

                if (statusCode == 200) {
                     result = (EntityUtils.toString(httpEntity, "utf-8"));
                    result = result.trim();
                    result = result.replace("\t", "");
                    result = result.replace("\n", "");
                    result = result.replace("\n\n", "");
                    result = result.replace("\r", "");
                    result = result.replaceAll("\\\"", "\\\\\\\"");
                } else {
                    throw new Exception("网络错误");
                }

            }

        } catch (Exception e) {
            throw new Exception("网络错误");
        }
//        java.net.SocketException: recvfrom failed: ETIMEDOUT (Connection timed out)
        return result;

    }
}
