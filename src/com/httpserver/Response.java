package com.httpserver;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Response {
	private StringBuilder HeaderInfo;
	
	public StringBuilder Content;
	
	private BufferedWriter bw;
	public int len;
	
	private final String CRLF="\r\n";
	private final String BLANK=" ";
	
	public Response() {
		HeaderInfo=new StringBuilder();
		Content=new StringBuilder();
		len=0;
	}
	
	public Response(Socket client) {
		this();//不同构造函数的调用
		try {
		 bw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 创建表头信息
	 * @param code
	 */
	private void createHeaderInfo(int code) {
		HeaderInfo.append("HTTP/1.1").append(BLANK);
		switch(code) {
		case 200:
			HeaderInfo.append("200").append(BLANK).append("OK");
			break;
		case 404:
			HeaderInfo.append("404").append(BLANK).append("NOFOUND");
			break;
		case 500:
			HeaderInfo.append("500").append(BLANK).append("SERVER ERROR");
			break;
		}
		HeaderInfo.append(CRLF);
		HeaderInfo.append("Content-type:text/html;chatset=GBK").append(CRLF);
		HeaderInfo.append("Content-Length:").append(len).append(CRLF);
		HeaderInfo.append(CRLF);
		
		
	}
	/**
	 * 写响应的内容函数
	 * @param content
	 */
	public void print(String content) {
		Content.append(content);
		len+=content.getBytes().length;
	}
	
	/**
	 * 写响应内容函数
	 * @param content
	 */
	public void println(String content) {
		Content.append(content).append(CRLF);
		len+=(content.getBytes().length+CRLF.getBytes().length);
	}
	
	/**
	 * 为什么把IOException抛出
	 * 因为Response处理不了异常
	 * 只能抛到使用的时候再处理
	 * @param code
	 * @throws IOException
	 */
	public void pushClient(int code) throws IOException{
		createHeaderInfo(code);
		bw.write(HeaderInfo.toString());
		bw.write(Content.toString());
		bw.flush();

		bw.close();
		
		
	}
}
