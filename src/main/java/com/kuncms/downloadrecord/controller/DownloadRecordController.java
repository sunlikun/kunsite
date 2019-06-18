package com.kuncms.downloadrecord.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kuncms.coverphoto.model.Coverphoto;
import com.kuncms.coverphoto.service.CoverphotoService;
import com.kuncms.downloadrecord.model.DownloadRecord;
import com.kuncms.downloadrecord.service.DownloadRecordService;

import net.sf.json.JSONArray;
@Controller
@RequestMapping("downloadRecordController")
public class DownloadRecordController {
	@Autowired
	DownloadRecordService downloadRecordService;
	
	@RequestMapping("insert")
	public void insert( DownloadRecord record) throws IOException, ParseException {
		downloadRecordService.insert(record);
	}
}