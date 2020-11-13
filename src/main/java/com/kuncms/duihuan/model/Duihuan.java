package com.kuncms.duihuan.model;

import org.springframework.stereotype.Component;

@Component
public class Duihuan {
private String id;
private String duihuan_code;
private String creat_time;
private String creat_by;
private String update_time;
private String update_by;
private char flag;
public String getDuihuan_code() {
	return duihuan_code;
}
public void setDuihuan_code(String duihuan_code) {
	this.duihuan_code = duihuan_code;
}
public String getCreat_time() {
	return creat_time;
}
public void setCreat_time(String creat_time) {
	this.creat_time = creat_time;
}
public String getCreat_by() {
	return creat_by;
}
public void setCreat_by(String creat_by) {
	this.creat_by = creat_by;
}
public String getUpdate_time() {
	return update_time;
}
public void setUpdate_time(String update_time) {
	this.update_time = update_time;
}
public String getUpdate_by() {
	return update_by;
}
public void setUpdate_by(String update_by) {
	this.update_by = update_by;
}
public char getFlag() {
	return flag;
}
public void setFlag(char flag) {
	this.flag = flag;
}


public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}





}
