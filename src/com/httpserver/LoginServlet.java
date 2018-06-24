package com.httpserver;


/**
 * LoginServlet继承Servlet
 * 主要实现了一个登录界面（web)
 * @author zhihu
 *
 */
public class LoginServlet extends Servlet {

	
	
	
	/**
	 * 写响应Web的内容
	 * 
	 */
	@Override
	public void doPost(Request req, Response rep) {
		// TODO Auto-generated method stub
		String name=req.getParameter("unname");//根据不同的请求参数值不一样
		String pwd=req.getParameter("passs");//如上
		if(login(name,pwd)) {
			rep.println("登录成功Post");
		}else {
			rep.println("登录失败Post");
		}
	}
	
	
	public boolean login(String name,String pwd) {
		return name.equals("zhihu")&&pwd.equals("199111");
	}
	

	@Override
	public void doGet(Request req, Response rep) {
		// TODO Auto-generated method stub
		String name=req.getParameter("unname");//根据不同的请求参数值不一样
		String pwd=req.getParameter("passs");//如上
		if(login(name,pwd)) {
			rep.println("登录成功Get");
		}else {
			rep.println("登录失败Get");
		}
		
	}

	
	
	
	
	
	
	
}
