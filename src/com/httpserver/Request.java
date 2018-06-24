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
	
	//����ʽ
	private String method;
	
	//������Դ
	private String url;
	//�����б�
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
		 *  ����1
		 *  �����ʹ�õ�һ������, ֱ��, ����, ��Ч�ʺܵ�
		 *  if(s == null || s.equals(����));
		 *  ����2
		 *   �Ƚ��ַ�������, Ч�ʸ�, ����֪�������һ������
		 *   if(s == null || s.length() == 0);
		 *  ����3
		 *   Java SE 6.0 �ſ�ʼ�ṩ�ķ���, Ч�ʺͷ������������, �����ڼ����Կ���, �Ƽ�ʹ�÷�����.
		 *   if(s == null || s.isEmpty());
		 *  ����4
		 *   ����һ�ֱȽ�ֱ��,���ķ���,����Ч��Ҳ�ǳ��ĸ�,�뷽����������Ч�ʲ��.
		 *   if (s == null || s == ����);
		 */
		if(null==requestInfo ||(requestInfo=requestInfo.trim()).length()==0){
			return ;
		}
		
		String paramString =""; //����������� 
		
		//��һ��
		String firstline=requestInfo.substring(0,requestInfo.indexOf(CRLF));
		int idx=firstline.indexOf("/");
		method=firstline.substring(0,idx).trim();
		String urlString=firstline.substring(idx,firstline.indexOf("HTTP/")).trim();
		if(method.equalsIgnoreCase("GET")) {
			if(urlString.contains("?")) {
				String[] urlArray=urlString.split("\\?");
				this.url=urlArray[0];
				paramString=urlArray[1];//����������� 
			}else {
				url=urlString;
			}
		}else if(method.equalsIgnoreCase("POST")) {
			url=urlString;
			paramString=requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim();
		}
		
		//�������������
		if(paramString.equals("")){
			return ;
		}
		//2�������������װ��Map��	
		parseParams(paramString);

	}
	
	
	//���������������ַ�����uname=123&pwd=213&fav=0&fav=1&fav=2
	//�����������װ��Map��
	private void parseParams(String paramString) {
		//�ָ� ���ַ���ת������
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
			//ת����Map �ּ�
			if(!parameterMapValues.containsKey(key)){
				parameterMapValues.put(key,new ArrayList<String>());
			}
			
			List<String> values =parameterMapValues.get(key);
			values.add(value);			
		}		
		
	}
	/**
	 * �����ʾ��������
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
	 * ����ҳ���name ��ȡ��Ӧ�Ķ��ֵ
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
	 * ����ҳ���name ��ȡ��Ӧ�ĵ���ֵ
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
