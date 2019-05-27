package com.kuncms.pay.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuncms.coverphoto.dao.CoverphotoDao;
import com.kuncms.coverphoto.model.Coverphoto;
import com.kuncms.pay.dao.AlipayTradeInfoDao;
import com.kuncms.pay.model.AlipayTradeInfo;
import com.kuncms.thumbnail.dao.ThumbnailDao;
import com.kuncms.thumbnail.model.Thumbnail;
import com.kuncms.videoinfo.dao.VideoInfoDao;
import com.kuncms.videoinfo.model.Videoinfo;

@Service
public class AlipayTradeInfoService {

	@Autowired
	AlipayTradeInfoDao alipayTradeInfoDao;
	
	

	public void insert(AlipayTradeInfo alipayTradeInfo) {
		// TODO Auto-generated method stub
		alipayTradeInfoDao.insert(alipayTradeInfo);
	}



	public ArrayList<AlipayTradeInfo> queryRechargeRecord(AlipayTradeInfo alipayTradeInfo) {
		// TODO Auto-generated method stub
		return alipayTradeInfoDao.queryRechargeRecord(alipayTradeInfo);
	}



	
	
	
	

}
