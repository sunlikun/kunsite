package com.kuncms.thumbnail.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.kuncms.coverphoto.model.Coverphoto;
import com.kuncms.thumbnail.model.Thumbnail;
import com.kuncms.videoinfo.model.Videoinfo;
@Mapper
public interface ThumbnailDao {

	Videoinfo serachvideo(Videoinfo videoInfo);

	void insert(Thumbnail thumbnail);

	ArrayList<Coverphoto> queryCoverPhoto();

	ArrayList<Coverphoto> queryThumbnail(Thumbnail thumbnail);

}
