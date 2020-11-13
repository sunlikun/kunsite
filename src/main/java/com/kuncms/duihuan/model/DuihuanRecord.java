package com.kuncms.duihuan.model;

import org.springframework.stereotype.Component;

@Component
public class DuihuanRecord {
private String id;
private String user_id;

private String creat_time;
private String duihuan_code;


public String getUser_id() {
	return user_id;
}
public void setUser_id(String user_id) {
	this.user_id = user_id;
}
public String getCreat_time() {
	return creat_time;
}
public void setCreat_time(String creat_time) {
	this.creat_time = creat_time;
}


public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}

public String getDuihuan_code() {
	return duihuan_code;
}
public void setDuihuan_code(String duihuan_code) {
	this.duihuan_code = duihuan_code;
}



}
