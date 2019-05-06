package com.kuncms.downloadrecord.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuncms.downloadrecord.dao.DownloadRecordDao;
import com.kuncms.downloadrecord.model.DownloadRecord;
import com.kuncms.util.DateUtil;


@Service
public class DownloadRecordService {

	@Autowired
	DownloadRecordDao downloadRecordDao;
	
	public void insert(DownloadRecord record,HttpServletRequest request) throws ParseException {
		// TODO Auto-generated method stub
		
		String id=UUID.randomUUID().toString().replace("-", "").toLowerCase();
		record.setId(id);
		DateUtil dateUtil=new DateUtil();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		record.setDownload_time(format.parse(dateUtil.getNow()));
		HttpSession session = request.getSession();
		String userName=(String) session.getAttribute("loginName");
		record.setUser_name(userName);
		
		downloadRecordDao.insert(record);
	}

	public ArrayList<DownloadRecord> queryrankingList(String start_time, String end_time) {
		// TODO Auto-generated method stub
		return downloadRecordDao.queryrankingList(start_time,end_time);
	}

	public ArrayList<DownloadRecord> queryRecordDayBy() {
		// TODO Auto-generated method stub
		return downloadRecordDao.queryRecordDayBy();
	}

	public ArrayList<DownloadRecord> queryRecordIWeek() {
		// TODO Auto-generated method stub
		return  downloadRecordDao.queryRecordIWeek();
	}

	public ArrayList<DownloadRecord> queryRecordMonth() {
		// TODO Auto-generated method stub
		return  downloadRecordDao.queryRecordMonth();
	}



	
	
	
	

}
