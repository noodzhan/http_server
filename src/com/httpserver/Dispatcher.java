package com.httpserver;

import java.io.IOException;
import java.net.Socket;


/**
 * �������󣬾��ǰ�Response���͵��ͻ���
 * �ַ�վ
 * һ����������Ӧ��һ���˶���
 * 
 * @author zhihu
 *
 */
public class Dispatcher implements Runnable {
	private Socket client;
	private Response rep;
	private Request req;
	private int code=200;
	
	public Dispatcher(Socket client) {
		this.client=client;
		rep=new Response(client);
		try {
			req=new Request(client);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			code=500;
			return;
		}
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Servlet servlet=WebApp.getServlet(req.getUrl());
		if(null==servlet) {
			this.code=404;
			System.out.println("servlet is null");
		}else {
			try {
				servlet.service(req, rep);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				this.code=500;
			}
			
			
			//��ResponseContext���͵��ͻ���
			try {
				rep.pushClient(code);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	

}
