package com.kuncms.user.model;

public class User {
private String id;
private String user_name;
private String password;
private String create_time;
private String update_time;
private String integral;
private String gold_coin;        //金币值
private String empirical_value;  //经验值
private String email;            //邮箱地址
private String user_class;       //用户类型
private String user_status;      //用户状态
private String flag;
private String openid;       //普通用户的标识(wecaht)
private String headimgurl;      //用户头像
private String is_wechat;	 //是否是微信用户



public String getOpenid() {
	return openid;
}
public void setOpenid(String openid) {
	this.openid = openid;
}
public String getHeadimgurl() {
	return headimgurl;
}
public void setHeadimgurl(String headimgurl) {
	this.headimgurl = headimgurl;
}
public String getIs_wechat() {
	return is_wechat;
}
public void setIs_wechat(String is_wechat) {
	this.is_wechat = is_wechat;
}
public String getIntegral() {
	return integral;
}
public void setIntegral(String integral) {
	this.integral = integral;
}
public String getGold_coin() {
	return gold_coin;
}
public void setGold_coin(String gold_coin) {
	this.gold_coin = gold_coin;
}
public String getEmpirical_value() {
	return empirical_value;
}
public void setEmpirical_value(String empirical_value) {
	this.empirical_value = empirical_value;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getUser_class() {
	return user_class;
}
public void setUser_class(String user_class) {
	this.user_class = user_class;
}
public String getUser_status() {
	return user_status;
}
public void setUser_status(String user_status) {
	this.user_status = user_status;
}


public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getUser_name() {
	return user_name;
}
public void setUser_name(String user_name) {
	this.user_name = user_name;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getCreate_time() {
	return create_time;
}
public void setCreate_time(String create_time) {
	this.create_time = create_time;
}
public String getUpdate_time() {
	return update_time;
}
public void setUpdate_time(String update_time) {
	this.update_time = update_time;
}
public String getFlag() {
	return flag;
}
public void setFlag(String flag) {
	this.flag = flag;
}
}
