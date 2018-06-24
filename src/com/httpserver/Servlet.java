
package com.httpserver;

import java.net.Socket;

/**
 * ��װ��������������(����)
 * @author zhihu
 *
 */
public abstract class Servlet {
	
	public void service(Request req,Response rep) {
		
		if(req.getMethod().equalsIgnoreCase("post")) {
			doPost(req,rep);
		}else if(req.getMethod().equalsIgnoreCase("get")){
			doGet(req,rep);
		}//���ݲ�ͬ��������Ӧ�ķ�ʽ�Ͳ�һ��
	
	}
	public abstract void doPost(Request req,Response rep);
	public abstract void doGet(Request req,Response rep);
	
}
