package com.kuncms.coverphoto.service;

import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuncms.coverphoto.dao.CoverphotoDao;
import com.kuncms.coverphoto.model.Coverphoto;
import com.kuncms.downloadrecord.model.DownloadRecord;
import com.kuncms.util.DateUtil;
import com.kuncms.videoinfo.dao.VideoInfoDao;
import com.kuncms.videoinfo.model.Videoinfo;

@Service
public class CoverphotoService {

	@Autowired
	CoverphotoDao coverphotoDao;
	
	

	/**
	 * 插入封面图片
	 * @param filepath
	 * @param fileName
	 * @param coverphoto2 
	 */
	public void insert(String filepath, String fileName, Coverphoto coverphoto,HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String id=UUID.randomUUID().toString().replace("-", "").toLowerCase();
		coverphoto.setCoverphotoid(filepath);
		coverphoto.setId(id);
		DateUtil dateUtil=new DateUtil();
		coverphoto.setCreate_time(dateUtil.getNow());
		coverphoto.setUpdate_time(dateUtil.getNow());
		HttpSession session = request.getSession();
		String userName=(String) session.getAttribute("loginName");
		coverphoto.setCreateby(userName);
		coverphoto.setUpdateby(userName);
		coverphoto.setFlag("1");
		coverphoto.setClicks(0);
		coverphoto.setDownloads(0);
		coverphotoDao.insert(coverphoto);
	}



	public ArrayList<Coverphoto> queryCoverPhoto() {
		// TODO Auto-generated method stub
		return coverphotoDao.queryCoverPhoto();
	}



	public ArrayList<Coverphoto> queryIndexCoverPhoto() {
		// TODO Auto-generated method stub
		return coverphotoDao.queryIndexCoverPhoto();
	}



	public ArrayList<Coverphoto> queryCoverPhotoById(Coverphoto coverphoto) {
		// TODO Auto-generated method stub
		return coverphotoDao.queryCoverPhotoById(coverphoto);
	}
	
	
	public void updateCoverPhotoById(Coverphoto coverphoto) {
		// TODO Auto-generated method stub
		coverphotoDao.updateCoverPhotoById(coverphoto);
	}



	public ArrayList<Coverphoto> queryVideo(String[] idArr) {
		// TODO Auto-generated method stub
		return coverphotoDao.queryVideo(idArr);
	}



	
	
	
	

}
