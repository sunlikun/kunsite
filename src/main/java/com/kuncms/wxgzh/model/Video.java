package com.kuncms.wxgzh.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class Video extends BaseMessage {
    
    /** 
     * 消息名称 
     */  
    private String Title;  
    /** 
     * 消息描述 
     */  
    private String MediaId;
    private String Description;
    
    private String title;
    
    public String getMediaId() {
		return MediaId;
	}


	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}


	
  
   

    
    public String getTitle() {
        return Title;
    }

    
    public void setTitle(String title) {
        Title = title;
    }

    
    public String getDescription() {
        return Description;
    }

    
    public void setDescription(String description) {
        Description = description;
    }

    
   

}

