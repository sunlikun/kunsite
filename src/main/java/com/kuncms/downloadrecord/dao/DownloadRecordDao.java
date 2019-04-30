package com.kuncms.downloadrecord.dao;


import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.kuncms.downloadrecord.model.DownloadRecord;

public interface DownloadRecordDao {
  
    int insert(DownloadRecord record);

 
}