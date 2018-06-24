package com.httpserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlHandler extends DefaultHandler{
	private List<Entity>servlet;
	private List<Mapping>mapping;
	private String tag;
	private Entity entity;
	private Mapping url;
	
	
	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		servlet=new ArrayList<Entity>();
		mapping=new ArrayList<Mapping>();
	}

	

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		tag=qName;
		//System.out.println("开始");
		if(tag.equals("servlet")) {
			entity=new Entity();
		}else if(tag.equals("mapping")) {
			url=new Mapping();
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		String temp=new String(ch,start,length);
		//System.out.println(tag);
		//System.out.println(temp);
		if(tag.equals("servlet-name")) {
			entity.setServletname(temp);
		}else if(tag.equals("servlet-class")) {
			entity.setServletclass(temp);
		}else if(tag.equals("mapping-name")) {
			url.setMappingname(temp);
		}else if(tag.equals("servlet-url")) {
			url.getUrl().add(temp);
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		//System.out.println(tag);
		//System.out.println("结束");
		if(qName.equals("servlet")) {
			servlet.add(entity);
		}else if(qName.equals("mapping")) {
			mapping.add(url);
		}
		tag="";
	}
	
	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
		//System.out.println("结束文档");
	}
	
	
	



	public List<Entity> getServlet() {
		return servlet;
	}



	public void setServlet(List<Entity> servlet) {
		this.servlet = servlet;
	}



	public List<Mapping> getMapping() {
		return mapping;
	}



	public void setMapping(List<Mapping> mapping) {
		this.mapping = mapping;
	}



	public Entity getEntity() {
		return entity;
	}



	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	/*public static void main(String[] args) {
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
			paser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("com/httpServer/demo3/servlet.xml"), 
					handler);
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Entity e:handler.getServlet()) {
			System.out.println(e.getServletname());
			System.out.println(e.getServletclass());
			
			
		}
		for(Mapping m:handler.getMapping()) {
			System.out.println(m.getMappingname());
			for(String e:m.getUrl()) {
				System.out.println(e);
			}
		}
	}
	*/

	
	
}
