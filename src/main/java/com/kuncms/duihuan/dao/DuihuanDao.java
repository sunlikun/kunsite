package com.kuncms.duihuan.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.kuncms.coverphoto.model.Coverphoto;
import com.kuncms.coverphoto.model.Label;
import com.kuncms.coverphoto.model.Play_num_record;
import com.kuncms.duihuan.model.Duihuan;
import com.kuncms.videoinfo.model.Videoinfo;
@Mapper
public interface DuihuanDao {

	ArrayList<Duihuan> queryDuihuancode(Duihuan duihuan);

}
