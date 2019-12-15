package com.kuncms.user.service;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuncms.user.dao.UserDao;
import com.kuncms.user.model.User;
@Service
public class UserService {
	@Autowired
	UserDao userDao;
	public void newsignup(User user) {
		// TODO Auto-generated method stub
		String id=UUID.randomUUID().toString().replace("-", "").toLowerCase();
		user.setId(id);
		user.setFlag("1");
		user.setGold_coin(20);
		user.setUser_status("1");
		user.setUser_class("1");
		user.setEmpirical_value(0);
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date  date=new Date();
		String time=format.format(date);
		user.setCreate_time(time);
		user.setUpdate_time(time);
		userDao.newsignup(user);
	}
	public List<User> login_operate(User user) {
		// TODO Auto-generated method stub
		return userDao.login_operate(user);
	}
	public ArrayList<User> check_username(User user) {
		// TODO Auto-generated method stub
		return userDao.check_username(user);
	}
	public void update(User loginuser) {
		// TODO Auto-generated method stub
		userDao.update(loginuser);
	}
	
	public ArrayList<User> isRegister(User user) {
		// TODO Auto-generated method stub
		return userDao.isRegister(user);
	}
	public void addUserGoldCoin(String id, int gold_coin) {
		// TODO Auto-generated method stub
		
		User user=new User();
		user.setId(id);
		user.setGold_coin(gold_coin);
		userDao.addUserGoldCoin(user);
	}
	public String getStatistics() {
		// TODO Auto-generated method stub
		return userDao.getStatistics();
	}
	public ArrayList<User> query_userlis() {
		// TODO Auto-generated method stub
		return userDao.query_userlis();
	}
	public void deluser(User user) {
		// TODO Auto-generated method stub
		userDao.deluser(user);
	}
	public void bindEmail(User user) {
		// TODO Auto-generated method stub
		userDao.bindEmail(user);
	}
	public void upPass(User user) {
		// TODO Auto-generated method stub
		userDao.updateAll(user);
	}
	public void update_offi_acc(User user) {
		// TODO Auto-generated method stub
		userDao.update_offi_acc(user);
	}

}
