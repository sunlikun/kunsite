package com.kuncms.coverphoto.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.kuncms.coverphoto.model.Coverphoto;
import com.kuncms.videoinfo.model.Videoinfo;
@Mapper
public interface CoverphotoDao {

	Videoinfo serachvideo(Videoinfo videoInfo);

	void insert(Coverphoto coverphoto);

	ArrayList<Coverphoto> queryCoverPhoto();

	ArrayList<Coverphoto> queryIndexCoverPhoto();

	ArrayList<Coverphoto> queryCoverPhotoById(Coverphoto coverphoto);

	void updateCoverPhotoById(Coverphoto coverphoto);

}
