package com.kuncms.thumbnail.model;

import org.springframework.stereotype.Component;

@Component
public class Thumbnail {
private String id;
private String thumbnailid;
private String create_time;
private String createby;
private String update_time;
private String updateby;
private String coverid;
private String flag;


public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getThumbnailid() {
	return thumbnailid;
}
public void setThumbnailid(String thumbnailid) {
	this.thumbnailid = thumbnailid;
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
public String getCoverid() {
	return coverid;
}
public void setCoverid(String coverid) {
	this.coverid = coverid;
}
public String getFlag() {
	return flag;
}
public void setFlag(String flag) {
	this.flag = flag;
}






}
