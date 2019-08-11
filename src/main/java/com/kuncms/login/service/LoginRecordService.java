package com.kuncms.login.service;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuncms.login.dao.LoginRecordDao;
import com.kuncms.login.model.LoginRecord;
import com.kuncms.user.dao.UserDao;
import com.kuncms.user.model.User;
import com.kuncms.util.DateUtil;
@Service
public class LoginRecordService {
	@Autowired
	LoginRecordDao loginRecordDao;
	public void insertNewLogrec(LoginRecord loginRecord) {
		// TODO Auto-generated method stub
		String id=UUID.randomUUID().toString().replace("-", "").toLowerCase();
		loginRecord.setId(id);
		loginRecord.setLogin_time(new DateUtil().getNow());
		loginRecordDao.insertNewLogrec(loginRecord);
	}

}
