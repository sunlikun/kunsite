package com.kuncms.downloadrecord.model;

import java.io.Serializable;
import java.util.Date;

public class DownloadRecord implements Serializable {
   
    private String id;
    private String videoId;
	private Date downloadTime;
    private String user_name;
    private static final long serialVersionUID = 1L;
    public String getUser_name() {
  		return user_name;
  	}


  	public void setUser_name(String user_name) {
  		this.user_name = user_name;
  	}

    
    public String getId() {
        return id;
    }

   
    public void setId(String id) {
        this.id = id;
    }

    
    public String getVideoId() {
        return videoId;
    }

   
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

   
    public Date getDownloadTime() {
        return downloadTime;
    }

   
    public void setDownloadTime(Date downloadTime) {
        this.downloadTime = downloadTime;
    }
}