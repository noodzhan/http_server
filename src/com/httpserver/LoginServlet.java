package com.httpserver;


/**
 * LoginServlet�̳�Servlet
 * ��Ҫʵ����һ����¼���棨web)
 * @author zhihu
 *
 */
public class LoginServlet extends Servlet {

	
	
	
	/**
	 * д��ӦWeb������
	 * 
	 */
	@Override
	public void doPost(Request req, Response rep) {
		// TODO Auto-generated method stub
		String name=req.getParameter("unname");//���ݲ�ͬ���������ֵ��һ��
		String pwd=req.getParameter("passs");//����
		if(login(name,pwd)) {
			rep.println("��¼�ɹ�Post");
		}else {
			rep.println("��¼ʧ��Post");
		}
	}
	
	
	public boolean login(String name,String pwd) {
		return name.equals("zhihu")&&pwd.equals("199111");
	}
	

	@Override
	public void doGet(Request req, Response rep) {
		// TODO Auto-generated method stub
		String name=req.getParameter("unname");//���ݲ�ͬ���������ֵ��һ��
		String pwd=req.getParameter("passs");//����
		if(login(name,pwd)) {
			rep.println("��¼�ɹ�Get");
		}else {
			rep.println("��¼ʧ��Get");
		}
		
	}

	
	
	
	
	
	
	
}
