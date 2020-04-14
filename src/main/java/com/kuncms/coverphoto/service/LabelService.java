package com.kuncms.coverphoto.service;

import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuncms.coverphoto.dao.CoverphotoDao;
import com.kuncms.coverphoto.dao.LabelDao;
import com.kuncms.coverphoto.model.Coverphoto;
import com.kuncms.coverphoto.model.Label;
import com.kuncms.coverphoto.model.Play_num_record;
import com.kuncms.downloadrecord.model.DownloadRecord;
import com.kuncms.util.DateUtil;
import com.kuncms.videoinfo.dao.VideoInfoDao;
import com.kuncms.videoinfo.model.Videoinfo;

@Service
public class LabelService {

	@Autowired
	LabelDao labelDao;
	
	
	public void save(Label label) {
		// TODO Auto-generated method stub
		String id=UUID.randomUUID().toString().replace("-", "").toLowerCase();
		label.setId(id);
		labelDao.save(label);
	}


	public ArrayList<Label> query(Label label) {
		// TODO Auto-generated method stub
		return labelDao.query(label);
	}

}
