package com.kuncms.videoinfo.model;

import org.springframework.stereotype.Component;

@Component
public class Videoinfo {
private String id;
private String video_name;
private String video_url;
private String create_time;

private String createby;
private String update_time;
private String updateby;
private String videocode;
private String flag;






public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getVideo_name() {
	return video_name;
}
public void setVideo_name(String video_name) {
	this.video_name = video_name;
}
public String getVideo_url() {
	return video_url;
}
public void setVideo_url(String video_url) {
	this.video_url = video_url;
}
public String getCreate_time() {
	return create_time;
}
public void setCreate_time(String create_time) {
	this.create_time = create_time;
}
public String getCreateby() {
	return createby;
}
public void setCreateby(String createby) {
	this.createby = createby;
}
public String getUpdate_time() {
	return update_time;
}
public void setUpdate_time(String update_time) {
	this.update_time = update_time;
}
public String getUpdateby() {
	return updateby;
}
public void setUpdateby(String updateby) {
	this.updateby = updateby;
}
public String getVideocode() {
	return videocode;
}
public void setVideocode(String videocode) {
	this.videocode = videocode;
}
public String getFlag() {
	return flag;
}
public void setFlag(String flag) {
	this.flag = flag;
}
}
