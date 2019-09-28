package com.bzu.gxs.smartrubbish.utils;

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;

import static com.bzu.gxs.smartrubbish.utils.Config.SELFCERTPATH;
import static com.bzu.gxs.smartrubbish.utils.Config.SELFCERTPWD;
import static com.bzu.gxs.smartrubbish.utils.Config.TRUSTCAPATH;
import static com.bzu.gxs.smartrubbish.utils.Config.TRUSTCAPWD;

/**
 * Created by mcz on 2018/1/8.
 */

public class DataManager {
    /**
     *
     *
     * HTTPS登录验证
     * @param mContext
     * @param serverurl
     * @return
     * @throws CertificateException
     * @throws IOException
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static String Login_Https(Context mContext, String serverurl, String userID, String tokenpwd)throws CertificateException, IOException, KeyStoreException,
            NoSuchAlgorithmException, KeyManagementException {
        // 服务器端需要验证的客户端证书
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        // 客户端信任的服务器端证书
        KeyStore trustStore = KeyStore.getInstance("BK" +
                "S");
        // 读取证书输入流
        Log.i("qwe:","123");
        InputStream ksIn = mContext.getAssets().open(SELFCERTPATH);
        InputStream tsIn = mContext.getAssets().open(TRUSTCAPATH);
        try {
            keyStore.load(ksIn, SELFCERTPWD.toCharArray());
            trustStore.load(tsIn, TRUSTCAPWD.toCharArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                ksIn.close();
            } catch (Exception ignore) {
            }
            try {
                tsIn.close();
            } catch (Exception ignore) {
            }
        }
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("X509");
        SSLSocketFactory ssl=null;
        try {
            keyManagerFactory.init(keyStore, SELFCERTPWD.toCharArray());
            ssl = new SSLSocketFactory(keyStore, SELFCERTPWD, trustStore);
        } catch ( UnrecoverableKeyException e ) {
            e.printStackTrace();
        }
        ssl.setHostnameVerifier(new AllowAllHostnameVerifier());
        HttpClient httpClient = new DefaultHttpClient();
        if (ssl != null) {
            Scheme sch = new Scheme("https", ssl, 443);
            httpClient.getConnectionManager().getSchemeRegistry().register(sch);
        }
        Map<String, String> param = new HashMap<>();
        param.put("appId", userID);
        param.put("secret", tokenpwd);

        List<NameValuePair> nvps = new LinkedList<NameValuePair>();
        Set<Map.Entry<String, String>> paramsSet = param.entrySet();
        for (Map.Entry<String, String> paramEntry : paramsSet) {
            nvps.add(new BasicNameValuePair(paramEntry.getKey(), paramEntry
                    .getValue()));
        }

        HttpPost request = new HttpPost(serverurl);
        request.setEntity(new UrlEncodedFormEntity(nvps));
//        httpResponse = httpClient.execute(httpGet, ct);
// 发起请求，获取回应，自封装接口，详见附录
        HttpResponse response = httpClient.execute(request);
        HttpEntity httpEntity = response.getEntity();

        // 得到一些数据

        // 通过EntityUtils并指定编码方式取到返回的数据

        StatusLine statusLine = response.getStatusLine();

        statusLine.getProtocolVersion();
        String result_https="";
        int statusCode = statusLine.getStatusCode();
        if (statusCode == 200) {
            result_https = (EntityUtils.toString(httpEntity, "utf-8"));
        }else{
            result_https=""+statusCode;
        }
        return result_https;
    }

    /**
     *
     * 登录鉴权
     * @param mContext
     * @param serverurl
     * @param userID
     * @param tokenpwd
     * @return
     * @throws Exception
     */
    public static String Login_Request(Context mContext, String serverurl, String userID, String tokenpwd)
            throws Exception {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("appId", userID));
        params.add(new BasicNameValuePair("secret", tokenpwd));
        String json = NetConnect.request(mContext, NetConnect.REQUEST_TYPE_POST,
                serverurl, params);
        return json;
    }
    public static String Txt_REQUSET(Context mContext, String serverurl, String app_key, String accessToken)
            throws Exception {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //params.add(new BasicNameValuePair("app_key", app_key));
       // params.add(new BasicNameValuePair("accessToken", accessToken));
        Log.i("x_key:",app_key);
        Log.i("net:",NetConnectHeaderData.REQUEST_TYPE_GET);
        Log.i("ser:",serverurl);
        Log.i("app_k:",app_key);
        Log.i("access:",accessToken);
        String test="123";
        String json = NetConnectHeaderData.request(mContext, NetConnectHeaderData.REQUEST_TYPE_GET,
                serverurl,app_key,accessToken, params);
        Log.i("jsonzzz:",test);
        return json;
    }
    public static String Register_DEVICEID(Context mContext, String serverurl, String app_key, String accessToken, String nodeID)
            throws Exception {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("verifyCode", nodeID));
        params.add(new BasicNameValuePair("nodeId", nodeID));
        params.add(new BasicNameValuePair("timeout", "600"));
        String json = NetConnectHeaderDataJSON.request(mContext, NetConnectHeaderDataJSON.REQUEST_TYPE_POST,
                serverurl,app_key,accessToken, params);
        return json;
    }
//命令下发()
    public static String Comened_DEVICEID(Context mContext, String serverurl, String app_key, String accessToken, String deviceId, String Switch,String method,String ziduan)
            throws Exception {
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("deviceId", deviceId));
//        params.add(new BasicNameValuePair("expireTime","0"));
        params.add(new BasicNameValuePair("serviceId", "Home"));
        params.add(new BasicNameValuePair("method", method));
        params.add(new BasicNameValuePair(ziduan, Switch));
        String json = NETCommned.request(mContext, NETCommned.REQUEST_TYPE_POST,
                serverurl,app_key,accessToken, params,ziduan);
        return json;
    }
//删除直连设备
    public static String Delete_DEVICEID(Context mContext, String serverurl, String app_key, String accessToken)
            throws Exception {

        String json = DeletingConnect.request(mContext, DeletingConnect.DELETE,
                serverurl,app_key,accessToken);
        return json;
    }
    /**
     *
     * 修改设备信息
     */
    public static String UPDATE_DEVICEID(Context mContext, String serverurl, String app_key, String accessToken, String name)
            throws Exception {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("deviceType", "Vehicle"));//根据实际情况修改
        params.add(new BasicNameValuePair("model", "Vehicle"));
        params.add(new BasicNameValuePair("manufacturerId", "tianshida "));
        params.add(new BasicNameValuePair("manufacturerName", "tianshida"));
        params.add(new BasicNameValuePair("protocolType", "CoAP"));
        params.add(new BasicNameValuePair("location", "Shenzhen"));
        params.add(new BasicNameValuePair("mute", "FALSE"));


        String json = NetConnectHeaderDataJSON.request(mContext, NetConnectHeaderDataJSON.REQUEST_TYPE_PUT,
                serverurl,app_key,accessToken, params);
        return json;
    }


    public static String Phone_http(Context mContext, String serverurl)
            throws Exception {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("verifyCode", ""));
//        params.add(new BasicNameValuePair("nodeId", nodeID));
//        params.add(new BasicNameValuePair("timeout", "300"));
        String json = NetConnectHttp.request(mContext, NetConnectHttp.REQUEST_TYPE_GET,
                serverurl, params);
        return json;
    }








    /**
     *
     * https连接认证
     * @param mContext
     * @return
     * @throws IOException
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static SSLSocketFactory GETSSLinitHttpClientBook(Context mContext) throws IOException,KeyStoreException,NoSuchAlgorithmException,KeyManagementException {
        // 服务器端需要验证的客户端证书
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        // 客户端信任的服务器端证书
        KeyStore trustStore = KeyStore.getInstance("BKS");
        String test="123";
        Log.i("iii1:",test);
        // 读取证书输入流
        InputStream ksIn = mContext.getAssets().open(SELFCERTPATH);
        InputStream tsIn = mContext.getAssets().open(TRUSTCAPATH);
        try {
            Log.i("iii2:",test);//
            keyStore.load(ksIn, SELFCERTPWD.toCharArray());
            trustStore.load(tsIn, TRUSTCAPWD.toCharArray());
        } catch (Exception e) {
            Log.i("iii3:",test);
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                Log.i("iii4:",test);//
                ksIn.close();
            } catch (Exception ignore) {
                Log.i("iii5:",test);
            }
            try {
                tsIn.close();
                Log.i("iii6:",test);//
            } catch (Exception ignore) {
                Log.i("iii7:",test);
            }
        }

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("X509");
        SSLSocketFactory ssl=null;
        try {
            Log.i("iii8:",test);//
            keyManagerFactory.init(keyStore, SELFCERTPWD.toCharArray());
            ssl = new SSLSocketFactory(keyStore, SELFCERTPWD, trustStore);
        } catch ( UnrecoverableKeyException e ) {
            Log.i("iii9:",test);
            e.printStackTrace();
        }
        ssl.setHostnameVerifier(new AllowAllHostnameVerifier());

        return ssl;
    }


}
