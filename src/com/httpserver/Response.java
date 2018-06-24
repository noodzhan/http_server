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
		this();//��ͬ���캯���ĵ���
		try {
		 bw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * ������ͷ��Ϣ
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
	 * д��Ӧ�����ݺ���
	 * @param content
	 */
	public void print(String content) {
		Content.append(content);
		len+=content.getBytes().length;
	}
	
	/**
	 * д��Ӧ���ݺ���
	 * @param content
	 */
	public void println(String content) {
		Content.append(content).append(CRLF);
		len+=(content.getBytes().length+CRLF.getBytes().length);
	}
	
	/**
	 * Ϊʲô��IOException�׳�
	 * ��ΪResponse�������쳣
	 * ֻ���׵�ʹ�õ�ʱ���ٴ���
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
