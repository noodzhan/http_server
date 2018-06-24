package com.httpserver;

import java.io.IOException;
import java.net.Socket;


/**
 * 处理请求，就是把Response推送到客户端
 * 分发站
 * 一个请求与响应就一个此对象
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
			
			
			//把ResponseContext推送到客户端
			try {
				rep.pushClient(code);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	

}
