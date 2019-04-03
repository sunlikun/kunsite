package com.kuncms.thumbnail.service;

import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuncms.coverphoto.dao.CoverphotoDao;
import com.kuncms.coverphoto.model.Coverphoto;
import com.kuncms.thumbnail.dao.ThumbnailDao;
import com.kuncms.thumbnail.model.Thumbnail;
import com.kuncms.util.DateUtil;
import com.kuncms.videoinfo.dao.VideoInfoDao;
import com.kuncms.videoinfo.model.Videoinfo;

@Service
public class ThumbnailService {

	@Autowired
	ThumbnailDao thumbnailDao;
	
	

	/**
	 * 插入详情图片
	 * @param filepath
	 * @param fileName
	 */
	public void insert(String filepath, String fileName,Thumbnail thumbnail,HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		
		String id=UUID.randomUUID().toString().replace("-", "").toLowerCase();
		thumbnail.setId(id);
		
		thumbnail.setFlag("1");
		thumbnail.setThumbnailid(filepath);
		DateUtil dateUtil=new DateUtil();
		thumbnail.setCreate_time(dateUtil.getNow());
		thumbnail.setUpdate_time(dateUtil.getNow());
		HttpSession session = request.getSession();
		String userName=(String) session.getAttribute("loginName");
		thumbnail.setCreateby(userName);
		thumbnail.setUpdateby(userName);
		thumbnailDao.insert(thumbnail);
	}



	public ArrayList<Coverphoto> queryThumbnail(String id) {
		// TODO Auto-generated method stub
		Thumbnail thumbnail=new Thumbnail();
		thumbnail.setCoverid(id);
		return thumbnailDao.queryThumbnail(thumbnail);
	}



	
	
	
	

}
