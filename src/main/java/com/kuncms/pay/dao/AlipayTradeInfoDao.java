package com.kuncms.pay.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.kuncms.coverphoto.model.Coverphoto;
import com.kuncms.pay.model.AlipayTradeInfo;
import com.kuncms.thumbnail.model.Thumbnail;

@Mapper
public interface AlipayTradeInfoDao {

	
	void insert(AlipayTradeInfo alipayTradeInfo);

	ArrayList<Coverphoto> queryCoverPhoto();

	ArrayList<Coverphoto> queryThumbnail(Thumbnail thumbnail);

}
