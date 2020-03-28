package com.kuncms.coverphoto.service;

import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuncms.coverphoto.dao.CoverphotoDao;
import com.kuncms.coverphoto.model.Coverphoto;
import com.kuncms.coverphoto.model.Play_num_record;
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



	public ArrayList<Coverphoto> queryCoverPhoto(Coverphoto coverphoto) {
		// TODO Auto-generated method stub
		return coverphotoDao.queryCoverPhoto(coverphoto);
	}

	public ArrayList<Coverphoto> queryCoverPhoto() {
		// TODO Auto-generated method stub
		return coverphotoDao.queryCoverPhoto();
	}

	public ArrayList<Coverphoto> queryIndexCoverPhoto(Coverphoto coverphoto) {
		// TODO Auto-generated method stub
		return coverphotoDao.queryIndexCoverPhoto(coverphoto);
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



	public void upVideo(Coverphoto coverphoto) {
		// TODO Auto-generated method stub
		coverphotoDao.upVideo(coverphoto);
	}



	public ArrayList<Coverphoto> queryVideoWithnoMediaId() {
		// TODO Auto-generated method stub
		return coverphotoDao.queryVideoWithnoMediaId();
	}



	public ArrayList<Coverphoto> queryCovPhoBySer_num(Coverphoto coverphoto) {
		// TODO Auto-generated method stub
		return coverphotoDao.queryCovPhoBySer_num(coverphoto);
	}



	public Play_num_record queryCurrenPlayNum() {
		// TODO Auto-generated method stub
		return coverphotoDao.queryCurrenPlayNum();
	}



	public void upCurrenPlayNum(int now_num) {
		// TODO Auto-generated method stub
		Play_num_record play_num_record=new Play_num_record();
		play_num_record.setPlay_num_cureent(now_num);
		coverphotoDao.upCurrenPlayNum(play_num_record);
	}



	public ArrayList<Coverphoto> queryPlayNumInfo(Coverphoto coverphoto) {
		// TODO Auto-generated method stub
		return coverphotoDao.queryPlayNumInfo();
	}



	public ArrayList<Coverphoto> queryVipPlayNumInfo(Coverphoto coverphoto) {
		// TODO Auto-generated method stub
		return coverphotoDao.queryVipPlayNumInfo();
	}



	public ArrayList<Coverphoto> queryPlayNumInfoCount(Coverphoto coverphoto) {
		// TODO Auto-generated method stub
		return coverphotoDao.queryPlayNumInfoCount(coverphoto);
	}



	public ArrayList<Coverphoto> queryVipPlayNumInfoCount(Coverphoto coverphoto) {
		// TODO Auto-generated method stub
		return coverphotoDao.queryVipPlayNumInfoCount(coverphoto);
	}



	public ArrayList<Coverphoto> queryOnlineData(Coverphoto coverphoto) {
		// TODO Auto-generated method stub
		return coverphotoDao.queryOnlineData(coverphoto);
	}



	public ArrayList<Coverphoto> queryAllOnlineVideo(Coverphoto coverphoto) {
		// TODO Auto-generated method stub
		return coverphotoDao.queryAllOnlineVideo(coverphoto);
	}

}
