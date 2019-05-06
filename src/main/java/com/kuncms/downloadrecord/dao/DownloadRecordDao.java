package com.kuncms.downloadrecord.dao;


import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kuncms.downloadrecord.model.DownloadRecord;
@Mapper
public interface DownloadRecordDao {
  
    int insert(DownloadRecord record);

	ArrayList<DownloadRecord> queryrankingList(String start_time, String end_time);

	ArrayList<DownloadRecord> queryRecordDayBy();

	ArrayList<DownloadRecord> queryRecordIWeek();

	ArrayList<DownloadRecord> queryRecordMonth();

 
}