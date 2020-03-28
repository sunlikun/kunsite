package com.kuncms.coverphoto.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.kuncms.coverphoto.model.Coverphoto;
import com.kuncms.coverphoto.model.Play_num_record;
import com.kuncms.videoinfo.model.Videoinfo;
@Mapper
public interface CoverphotoDao {

	Videoinfo serachvideo(Videoinfo videoInfo);

	void insert(Coverphoto coverphoto);

	ArrayList<Coverphoto> queryCoverPhoto(Coverphoto coverphoto);
	
	ArrayList<Coverphoto> queryCoverPhoto();

	ArrayList<Coverphoto> queryIndexCoverPhoto(Coverphoto coverphoto);

	ArrayList<Coverphoto> queryCoverPhotoById(Coverphoto coverphoto);

	void updateCoverPhotoById(Coverphoto coverphoto);

	ArrayList<Coverphoto> queryVideo(String[] idArr);

	void upVideo(Coverphoto coverphoto);

	ArrayList<Coverphoto> queryVideoWithnoMediaId();

	ArrayList<Coverphoto> queryCovPhoBySer_num(Coverphoto coverphoto);

	Play_num_record queryCurrenPlayNum();

	void upCurrenPlayNum(Play_num_record play_num_record);

	ArrayList<Coverphoto> queryPlayNumInfo();

	ArrayList<Coverphoto> queryVipPlayNumInfo();

	ArrayList<Coverphoto> queryPlayNumInfoCount(Coverphoto coverphoto);

	ArrayList<Coverphoto> queryVipPlayNumInfoCount(Coverphoto coverphoto);

	ArrayList<Coverphoto> queryOnlineData(Coverphoto coverphoto);

	ArrayList<Coverphoto> queryAllOnlineVideo(Coverphoto coverphoto);

}
