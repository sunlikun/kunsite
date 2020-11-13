package com.kuncms.duihuan.service;

import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuncms.coverphoto.dao.CoverphotoDao;
import com.kuncms.coverphoto.model.Coverphoto;
import com.kuncms.coverphoto.model.Play_num_record;
import com.kuncms.downloadrecord.model.DownloadRecord;
import com.kuncms.duihuan.dao.DuihuanDao;
import com.kuncms.duihuan.model.Duihuan;
import com.kuncms.util.DateUtil;
import com.kuncms.videoinfo.dao.VideoInfoDao;
import com.kuncms.videoinfo.model.Videoinfo;

@Service
public class DuihuanService {

	@Autowired
	DuihuanDao duihuanDao;
	
	
	
	



	public ArrayList<Duihuan> queryDuihuancode(Duihuan duihuan) {
		// TODO Auto-generated method stub
		
		return duihuanDao.queryDuihuancode(duihuan);
	}

}
