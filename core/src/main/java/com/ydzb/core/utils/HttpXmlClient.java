package com.ydzb.core.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

public class HttpXmlClient {
	 
	/**
	 * @param url 网址路径
	 * @return 请求返回的字符串数据
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String post(String url) throws ParseException, IOException {
		HttpPost post = new HttpPost(url);
		HttpClient http = new DefaultHttpClient();
		HttpResponse response = http.execute(post);
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity);
		}else{
			return null;
		}
	}
	 
	/**
	 * 以post方式获取数据
	 * @param url http地址
	 * @param listParams 参数列表
	 * @param charset 编码
	 * @return 请求返回的字符串数据
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String post(String url, List<BasicNameValuePair> listParams, String charset) throws ParseException, IOException {
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(url);
		request.setEntity(new UrlEncodedFormEntity(listParams, charset));
		HttpResponse response = client.execute(request);
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity, charset);
		}else{
			return null;
		}
	}
	
	 
	/**
	 * 以get方法获取数据的使用
	 * @param url 网址路径
	 * @return 请求返回的字符串数据
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String get(String url) throws ClientProtocolException, IOException {
		HttpGet get = new HttpGet(url);
		HttpClient http = new DefaultHttpClient();
		HttpResponse response = new DefaultHttpClient().execute(get);
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity);
		}
		return null;

	} 
	          
	 
}
