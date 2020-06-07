package com.kuncms.coverphoto.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kuncms.coverphoto.dao.CoverphotoDao;
import com.kuncms.coverphoto.dao.LabelDao;
import com.kuncms.coverphoto.model.Coverphoto;
import com.kuncms.coverphoto.model.Label;
import com.kuncms.coverphoto.service.CoverphotoService;
import com.kuncms.coverphoto.service.LabelService;
import com.kuncms.downloadrecord.model.DownloadRecord;
import com.kuncms.downloadrecord.service.DownloadRecordService;
import com.kuncms.user.model.User;
import com.kuncms.user.service.UserService;
import com.kuncms.videoinfo.model.Videoinfo;
import com.kuncms.videoinfo.service.VideoInfoService;

import net.sf.json.JSONArray;
import sun.rmi.runtime.Log;
@Controller
@RequestMapping("/LabelController")
public class LabelController {
	@Autowired
	private LabelService labelService;
	
	/**
	 * 标签查询
	 * @param response
	 * @param label
	 * @throws IOException
	 */
	@RequestMapping("/query")
	public void query(HttpServletResponse response,Label label) throws IOException {
		
		ArrayList<Label> list=labelService.query(label);
		
		 response.setContentType("application/json");
	        response.setHeader("Pragma", "No-cache");
	        response.setHeader("Cache-Control", "no-cache");
	        response.setCharacterEncoding("UTF-8");
	        
	        JSONArray listArray=JSONArray.fromObject(list);     
	        PrintWriter out= null;
	        out = response.getWriter();
	        out.print(listArray.toString());
	        System.out.println(listArray.toString());
	        out.flush();
	        out.close();
		
		
	}
	
	/**
	 * @param response
	 * @param coverphoto
	 * @throws IOException
	 * 保存信息
	 */
	@RequestMapping("/save")
	@ResponseBody
	public void save(HttpServletResponse response,Label label) throws IOException {
			labelService.save(label);
			JSONObject result=new JSONObject();
			result.put("result", "保存成功");
			 PrintWriter out= null;
		        out = response.getWriter();
		        out.print(result.toString());
		        System.out.println(result.toString());
		        out.flush();
		        out.close();
	}
	
	/**
	 * @param map
	 * @param request
	 * @return
	 * 跳转到标签管理页
	 */
	@RequestMapping("/toLabelManage")
    public String toPlayNumInfo(Map<String,Object> map,HttpServletRequest request){
   
    
		return "LabelManage";
    }
	
	
	
	
	
	
	
	
	
	

	
	
}
