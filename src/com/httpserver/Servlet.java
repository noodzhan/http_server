
package com.httpserver;

import java.net.Socket;

/**
 * 封装服务器推送数据(内容)
 * @author zhihu
 *
 */
public abstract class Servlet {
	
	public void service(Request req,Response rep) {
		
		if(req.getMethod().equalsIgnoreCase("post")) {
			doPost(req,rep);
		}else if(req.getMethod().equalsIgnoreCase("get")){
			doGet(req,rep);
		}//根据不同的请求响应的方式就不一样
	
	}
	public abstract void doPost(Request req,Response rep);
	public abstract void doGet(Request req,Response rep);
	
}
