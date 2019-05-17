package com.kuncms.user.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kuncms.user.model.User;
@Mapper
public interface UserDao {

	public void newsignup(User user);

	public List<User> login_operate(User user);

	public ArrayList<User> check_username(User user);

	public void update(User loginuser);

	public ArrayList<User> isRegister(User user);

	public void addUserGoldCoin(String user_name, int gold_coin);

	public void addUserGoldCoin(User user);

	public String getStatistics();

	public ArrayList<User> query_userlis();

	public void deluser(User user);

	void bindEmail(User user);

	void updateAll(User user);


}
