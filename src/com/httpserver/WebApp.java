package com.httpserver;

import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class WebApp {
	private static ServletContext context;
	static {
		context=new ServletContext();
		
		Map<String,String> mapping=context.getMapping();
		//mapping.put("/login", "login");
		//mapping.put("/log", "login");
		//mapping.put("/reg", "register");
		Map<String,String> servlet=context.getServlet();
		
		
		
		//建立两个响应方式：login register
		//servlet.put("login","com.httpServer.demo3.LoginServlet" );
		//servlet.put("register","com.httpServer.demo3.RegisterServlet");
		SAXParserFactory factory=SAXParserFactory.newInstance();
		SAXParser paser=null;
		XmlHandler handler=new XmlHandler();
		try {
			paser=factory.newSAXParser();
		} catch (ParserConfigurationException|SAXException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			paser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("com/httpserver/servlet.xml"), 
					handler);
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Entity e:handler.getServlet()) {
			System.out.println(e.getServletname());
			System.out.println(e.getServletclass());
			servlet.put(e.getServletname(),e.getServletclass() );
			
		}
		for(Mapping m:handler.getMapping()) {
			System.out.println(m.getMappingname());
			for(String e:m.getUrl()) {
				System.out.println(e);
				mapping.put(e,m.getMappingname());
			}
		}
		
		
		
		
		
		
	}
	
	public static Servlet getServlet(String url) {
		if((null==url)||(url.trim().equals(""))) {
			return null;
		}
		String ser= context.getServlet().get(context.getMapping().get(url));
		if(ser==null) {
			return null;
		}
		Servlet temp=null;
		try {
			Class ServletClass=Class.forName(ser);
			temp= (Servlet)ServletClass.newInstance();
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}
}
