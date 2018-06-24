package com.httpserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * ͨ����������Է���http://DESKTOP-1EI477A:8888
 * ���԰ѷ��ʵ���Ϣ��ӡ����
 * ��ӡ�����ľ���httpЭ��
 * 
 * 
 * 
 * ����
 * ��Ӧ
 * 
 * @author zhihu
 *
 */
public class Server {
	
	private ServerSocket server;
	private final String CRLF="\r\n";
	private final String BLANK=" ";
	public static void main(String[] args) {
		new Server().start();
		
	}
	public void start() {
		try {
			server=new ServerSocket(8888);
			this.receive();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void receive() {
		try {
			Socket client=server.accept();
			
			new Thread(new Dispatcher(client)).start();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void stop() {
		
	}
	public void response() {
		
	}
}
