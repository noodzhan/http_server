package com.httpserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Map;
import java.util.StringTokenizer;

public class Request {
	
	//请求方式
	private String method;
	
	//访问资源
	private String url;
	//参数列表
	private Map<String,List<String>> parameterMapValues;
	
	
	public final String CRLF="\r\n";
	private InputStream is;
	private String requestInfo;
	
	public Request() {
		requestInfo="";
		method="";
		url="";
		parameterMapValues=new HashMap<String,List<String>>();
	}
	
	public Request(Socket client) throws IOException {
		this();
		is=client.getInputStream();
		byte buf[]=new byte[10240];
		int len=is.read(buf);
		requestInfo=new String(buf,0,len);
		parseRequestInfo();
	}

	private void parseRequestInfo() {
		/**
		 *  方法1
		 *  最多人使用的一个方法, 直观, 方便, 但效率很低
		 *  if(s == null || s.equals(“”));
		 *  方法2
		 *   比较字符串长度, 效率高, 是我知道的最好一个方法
		 *   if(s == null || s.length() == 0);
		 *  方法3
		 *   Java SE 6.0 才开始提供的方法, 效率和方法二几乎相等, 但出于兼容性考虑, 推荐使用方法二.
		 *   if(s == null || s.isEmpty());
		 *  方法4
		 *   这是一种比较直观,简便的方法,而且效率也非常的高,与方法二、三的效率差不多.
		 *   if (s == null || s == “”);
		 */
		if(null==requestInfo ||(requestInfo=requestInfo.trim()).length()==0){
			return ;
		}
		
		String paramString =""; //接收请求参数 
		
		//第一行
		String firstline=requestInfo.substring(0,requestInfo.indexOf(CRLF));
		int idx=firstline.indexOf("/");
		method=firstline.substring(0,idx).trim();
		String urlString=firstline.substring(idx,firstline.indexOf("HTTP/")).trim();
		if(method.equalsIgnoreCase("GET")) {
			if(urlString.contains("?")) {
				String[] urlArray=urlString.split("\\?");
				this.url=urlArray[0];
				paramString=urlArray[1];//接收请求参数 
			}else {
				url=urlString;
			}
		}else if(method.equalsIgnoreCase("POST")) {
			url=urlString;
			paramString=requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim();
		}
		
		//不存在请求参数
		if(paramString.equals("")){
			return ;
		}
		//2、将请求参数封装到Map中	
		parseParams(paramString);

	}
	
	
	//处理类似这样的字符串：uname=123&pwd=213&fav=0&fav=1&fav=2
	//将请求参数封装到Map中
	private void parseParams(String paramString) {
		//分割 将字符串转成数组
		StringTokenizer token=new StringTokenizer(paramString,"&");
		while(token.hasMoreTokens()){
			String keyValue =token.nextToken();
			String[] keyValues=keyValue.split("=");
			if(keyValues.length==1){
				keyValues =Arrays.copyOf(keyValues, 2);
				keyValues[1] =null;
			}
			
			String key = keyValues[0].trim();
			String value = null==keyValues[1]?null:decode(keyValues[1].trim(),"gbk");
			//转换成Map 分拣
			if(!parameterMapValues.containsKey(key)){
				parameterMapValues.put(key,new ArrayList<String>());
			}
			
			List<String> values =parameterMapValues.get(key);
			values.add(value);			
		}		
		
	}
	/**
	 * 解决显示中文问题
	 * @param value
	 * @param code
	 * @return
	 */
	private String decode(String value, String code) {
		try {
			return java.net.URLDecoder.decode(value, code);
		} catch (UnsupportedEncodingException e) {
			//e.printStackTrace();
		}
		return null;
	}
	/**
	 * 根据页面的name 获取对应的多个值
	 * @param args
	 */
	public String[] getParameterValues(String name){
		List<String> values=null;
		if((values=parameterMapValues.get(name))==null){
			return null;
		}else{
			return values.toArray(new String[0]);
		}
	}
	/**
	 * 根据页面的name 获取对应的单个值
	 * @param args
	 */
	public String getParameter(String name){
		String[] values =getParameterValues(name);
		if(null==values){
			return null;
		}
		return values[0];
	}
	public String getUrl() {
		return url;
	}
	
	
	
	
	
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	
	
	
}
