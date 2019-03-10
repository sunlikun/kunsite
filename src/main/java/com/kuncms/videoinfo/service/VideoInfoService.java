package com.kuncms.videoinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuncms.videoinfo.dao.VideoInfoDao;
import com.kuncms.videoinfo.model.Videoinfo;

@Service
public class VideoInfoService {

	@Autowired
	VideoInfoDao videoInfoDao;
	
	public Videoinfo serachvideo(String videocode) {
		
		Videoinfo  videoinfo=new Videoinfo();
		videoinfo.setVideocode(videocode);
		
		return videoInfoDao.serachvideo(videoinfo);
		
		
	}

}
