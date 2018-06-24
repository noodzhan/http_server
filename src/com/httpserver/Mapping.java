package com.httpserver;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhihu
 *
 */
public class Mapping {
	private String mappingname;
	private List<String>url;
	
	
	public Mapping() {
		url=new ArrayList<String>();
	}
	
	
	
	public List<String> getUrl() {
		return url;
	}



	public void setUrl(List<String> url) {
		this.url = url;
	}



	public String getMappingname() {
		return mappingname;
	}
	public void setMappingname(String mappingname) {
		this.mappingname = mappingname;
	}
	
	
}
