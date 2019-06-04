package com.kuncms.wxgzh.model;
/** 
 * 按钮的基类 
 *  
 * @author liufeng 
 * @date 2013-08-08 
 */
public class Button {
	
	 private String name;  
	 private String type;  
	 private String key; 
	 private String url; 
	 
	 public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
			return type;
	 }

	 public void setType(String type) {
		this.type = type;
	 }

	 public String getKey() {
		return key;
	 }

	 public void setKey(String key) {
		this.key = key;
	 }

	 public String getName() {  
         return name;  
     }  
  
     public void setName(String name) {  
        this.name = name;  
     }  
}
