package com.kuncms.downloadrecord.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Component;
@Component
public class DownloadRecord implements Serializable {
   
    private String id;
    private String video_id;
	private Date download_time;
    private String user_name;
    private String user_id;
    
    
    public String getUser_id() {
		return user_id;
	}


	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}


	public String getVideo_id() {
		return video_id;
	}


	public void setVideo_id(String video_id) {
		this.video_id = video_id;
	}


	public Date getDownload_time() {
		return download_time;
	}


	public void setDownload_time(Date download_time) {
		this.download_time = download_time;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


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

    
   
}