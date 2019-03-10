package com.kuncms.videoinfo.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kuncms.videoinfo.model.Videoinfo;
@Mapper
public interface VideoInfoDao {

	Videoinfo serachvideo(Videoinfo videoInfo);

}
