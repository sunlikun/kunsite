package com.kuncms.pay.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuncms.coverphoto.dao.CoverphotoDao;
import com.kuncms.coverphoto.model.Coverphoto;
import com.kuncms.thumbnail.dao.ThumbnailDao;
import com.kuncms.thumbnail.model.Thumbnail;
import com.kuncms.videoinfo.dao.VideoInfoDao;
import com.kuncms.videoinfo.model.Videoinfo;

@Service
public class AlipayTradeInfoService {

	@Autowired
	ThumbnailDao thumbnailDao;
	
	

	/**
	 * 插入封面图片
	 * @param filepath
	 * @param fileName
	 */
	public void insert(String filepath, String fileName,Thumbnail thumbnail) {
		// TODO Auto-generated method stub
		
		
		String id=UUID.randomUUID().toString().replace("-", "").toLowerCase();
		thumbnail.setId(id);
		
		thumbnail.setFlag("1");
		thumbnail.setThumbnailid(filepath);
		thumbnailDao.insert(thumbnail);
	}



	public ArrayList<Coverphoto> queryThumbnail(String id) {
		// TODO Auto-generated method stub
		Thumbnail thumbnail=new Thumbnail();
		thumbnail.setCoverid(id);
		return thumbnailDao.queryThumbnail(thumbnail);
	}



	
	
	
	

}
