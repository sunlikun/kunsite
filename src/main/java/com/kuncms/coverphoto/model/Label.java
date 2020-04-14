package com.kuncms.coverphoto.model;

import org.springframework.stereotype.Component;

@Component
public class Label {
private String id;
private String lable_name;
private String class_flag;

public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getLable_name() {
	return lable_name;
}
public void setLable_name(String lable_name) {
	this.lable_name = lable_name;
}
public String getClass_flag() {
	return class_flag;
}
public void setClass_flag(String class_flag) {
	this.class_flag = class_flag;
}





}
