package com.kuncms.duihuan.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuncms.coverphoto.dao.CoverphotoDao;
import com.kuncms.coverphoto.model.Coverphoto;
import com.kuncms.coverphoto.model.Play_num_record;
import com.kuncms.downloadrecord.model.DownloadRecord;
import com.kuncms.duihuan.dao.DuihuanDao;
import com.kuncms.duihuan.dao.DuihuanRecordDao;
import com.kuncms.duihuan.model.Duihuan;
import com.kuncms.duihuan.model.DuihuanRecord;
import com.kuncms.user.model.User;
import com.kuncms.util.DateUtil;
import com.kuncms.videoinfo.dao.VideoInfoDao;
import com.kuncms.videoinfo.model.Videoinfo;

@Service
public class DuihuanRecordService {

	@Autowired
	DuihuanDao duihuanDao;
	@Autowired
	DuihuanRecordDao duihuanRecordDao;
	
	public ArrayList<Duihuan> queryDuihuancode(Duihuan duihuan) {
		// TODO Auto-generated method stub
		
		return duihuanDao.queryDuihuancode(duihuan);
	}

	public void save(DuihuanRecord duihuanRecord,HttpServletRequest request) {
		// TODO Auto-generated method stub
		String id=UUID.randomUUID().toString().replace("-", "").toLowerCase();
		HttpSession session = request.getSession();
		User user=(User) session.getAttribute("user");
		duihuanRecord.setId(id);
		duihuanRecord.setUser_id(user.getId());

		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());
		System.out.println(formatter.format(date));
		duihuanRecord.setCreat_time(formatter.format(date));
		duihuanRecordDao.save(duihuanRecord);
		
	}

	public ArrayList<DuihuanRecord> query(DuihuanRecord duihuanRecord1) {
		// TODO Auto-generated method stub
		return duihuanRecordDao.query(duihuanRecord1);
	}

}
