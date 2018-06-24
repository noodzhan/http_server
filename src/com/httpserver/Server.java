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
 * 通过浏览器可以访问http://DESKTOP-1EI477A:8888
 * 可以把访问的信息打印出来
 * 打印出来的就是http协议
 * 
 * 
 * 
 * 访问
 * 响应
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
