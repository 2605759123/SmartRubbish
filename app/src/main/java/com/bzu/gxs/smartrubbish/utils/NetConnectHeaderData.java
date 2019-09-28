package com.bzu.gxs.smartrubbish.utils;

/**
 * 网络连接类/GET/POST请求加解密
 * 
 */

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

public class NetConnectHeaderData {
	public static final String REQUEST_TYPE_GET = "GET";
	public static final String REQUEST_TYPE_POST = "POST";

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
                                 List<NameValuePair> param) throws Exception {
		HttpResponse response;
		String result_https="";
		String test="123";
		if (method.equals(REQUEST_TYPE_GET)){
			String ps ="";
			Log.i("a1:",test);//
			if (param != null) {
				Log.i("poi:",test);//
				List<NameValuePair> param2 = new ArrayList<NameValuePair>();
				for (int i = 0; i < param.size(); i++) {
					Log.i("m1:",test);
					if (i>0) {
						ps+="&";
						Log.i("m2:",test);
					}
					Log.i("m3:",test);
					String key = param.get(i).getName();
					String value =param.get(i)
							.getValue();
					ps+=key+"="+value;
				}

				 ps = URLEncodedUtils.format(param2, HTTP.UTF_8);
				// 通过url创建对象
				if (url.indexOf("?") > 0) {
					url += "&" + ps;
					Log.i("m4:",url);//
				} else {
					url += "?" + ps;
					Log.i("m5:",url);
				}
			}
			SSLSocketFactory ssl=DataManager.GETSSLinitHttpClientBook(context);
			Log.i("a2=:",test);//
			HttpClient httpClient = new DefaultHttpClient();
			if (ssl != null) {
				Log.i("m6:",test);//
				Scheme sch = new Scheme("https", ssl, 443);
				httpClient.getConnectionManager().getSchemeRegistry().register(sch);
			}

			Log.i("url111:",url);
			HttpGet request = new HttpGet(url);
			request.setHeader("app_key",app_id);
			Log.i("t_id:",app_id);
			request.setHeader("Authorization","Bearer "+token);
			Log.i("t_token:",token);
			request.setHeader("Content-Type","application/json");
			Log.i("a5:",test);

			//request.setEntity(new UrlEncodedFormEntity(param));
			// 发起请求，获取回应，自封装接口，详见附录
			response = httpClient.execute(request);
			 Log.i("a9:",test);
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
			Log.i("b1:",test);

			SSLSocketFactory ssl=DataManager.GETSSLinitHttpClientBook(context);

			HttpClient httpClient = new DefaultHttpClient();
			if (ssl != null) {
				Scheme sch = new Scheme("http", ssl, 443);
				httpClient.getConnectionManager().getSchemeRegistry().register(sch);
			}
			HttpPost request = new HttpPost(url);
			if (param != null) {
				List<NameValuePair> param2 = new ArrayList<NameValuePair>();
				for (int i = 0; i < param.size(); i++) {
					String key = param.get(i).getName();
					String value = param.get(i).getValue();
					param2.add(new BasicNameValuePair(key, value));
				}

				request.setEntity(new UrlEncodedFormEntity(param2,
						HTTP.UTF_8));
			}
//			request.setEntity(new UrlEncodedFormEntity(param));
			// tou
			request.setHeader("app_key",app_id);
			request.setHeader("Authorization","Bearer "+token);
			request.setHeader("Content-Type","application/json");

			response = httpClient.execute(request);
			HttpEntity httpEntity = response.getEntity();
//			Header[] headers=response.getAllHeaders();
//			for(int i=0;i<headers.length;i++) {;
//				Log.i("headers",headers[i].getName() +"=="+ headers[i].getValue());
//			}
//			// 得到一些数据
//			StatusLine statusLinesss=response.getStatusLine();
//			String statusCodess =statusLinesss.getReasonPhrase();
//			Log.i("statusCodess",statusCodess);
//			ProtocolVersion protocolVersion=statusLinesss.getProtocolVersion();
//			Log.i("protocolVersion",protocolVersion+"");
//			Log.i("protocolVersion",	protocolVersion.getMajor()+"");
//			Log.i("protocolVersion",	protocolVersion.getMinor()+"");
//			Log.i("protocolVersion",	protocolVersion.getProtocol()+"");
//			Header de=httpEntity.getContentType();
			// 通过EntityUtils并指定编码方式取到返回的数据
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				result_https = (EntityUtils.toString(httpEntity, "utf-8"));
			}else{
				result_https=""+statusCode;
			}
		}else{
			Log.i("c1:",test);
			Log.i("method==", ".....");
		}



		return result_https;

	}

}
