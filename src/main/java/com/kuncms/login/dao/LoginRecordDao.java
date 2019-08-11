package com.kuncms.login.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kuncms.login.model.LoginRecord;
import com.kuncms.user.model.User;
@Mapper
public interface LoginRecordDao {


	public void insertNewLogrec(LoginRecord loginRecord);


}
