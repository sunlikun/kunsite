package com.kuncms.coverphoto.model;

import org.springframework.stereotype.Component;

@Component
public class Coverphoto {
private String id;
private String coverphotoid;
private String create_time;
private String createby;
private String update_time;
private String updateby;
private String video_name;
private String flag;
private String baiduyun_address;
private int gold_coin;
private int clicks;
private int downloads;

public int getClicks() {
	return clicks;
}
public void setClicks(int clicks) {
	this.clicks = clicks;
}
public int getDownloads() {
	return downloads;
}
public void setDownloads(int downloads) {
	this.downloads = downloads;
}

public int getGold_coin() {
	return gold_coin;
}
public void setGold_coin(int gold_coin) {
	this.gold_coin = gold_coin;
}
private String baiduyun_pass;


public String getBaiduyun_address() {
	return baiduyun_address;
}
public void setBaiduyun_address(String baiduyun_address) {
	this.baiduyun_address = baiduyun_address;
}
public String getBaiduyun_pass() {
	return baiduyun_pass;
}
public void setBaiduyun_pass(String baiduyun_pass) {
	this.baiduyun_pass = baiduyun_pass;
}


public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getCoverphotoid() {
	return coverphotoid;
}
public void setCoverphotoid(String coverphotoid) {
	this.coverphotoid = coverphotoid;
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
public String getVideo_name() {
	return video_name;
}
public void setVideo_name(String video_name) {
	this.video_name = video_name;
}
public String getFlag() {
	return flag;
}
public void setFlag(String flag) {
	this.flag = flag;
}







}
