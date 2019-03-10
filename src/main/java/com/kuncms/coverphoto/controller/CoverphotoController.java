package com.kuncms.coverphoto.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import com.kuncms.coverphoto.dao.CoverphotoDao;
import com.kuncms.coverphoto.model.Coverphoto;
import com.kuncms.coverphoto.service.CoverphotoService;
import com.kuncms.videoinfo.model.Videoinfo;
import com.kuncms.videoinfo.service.VideoInfoService;

import net.sf.json.JSONArray;
@Controller
public class CoverphotoController {
	@Autowired
	private CoverphotoDao coverphotoDao;
	@Autowired
	CoverphotoService coverphotoService;
	
	
	@RequestMapping("/videomanage")
    public String videomanage(Map<String,Object> map,HttpServletRequest request,Model model){
	   ArrayList<Coverphoto> list=coverphotoService.queryCoverPhoto();
	   com.alibaba.fastjson.JSONArray array= com.alibaba.fastjson.JSONArray.parseArray(JSON.toJSONString(list));
	   model.addAttribute("data", array.toJSONString());

       return "VideoManage";
    }
	
	/**
	 * @param map
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping("/videoadd")
    public String videoadd(Map<String,Object> map,HttpServletRequest request){
       String videocode=request.getParameter("videocode");
       //Videoinfo  videoinfo=videoInfoService.serachvideo("videocode");
       System.out.println("视频管理");
       //map.put("hello",videoinfo);
       return "videoadd";
    }
	
	
	@RequestMapping("/queryCoverPhoto")
	
	public void queryCoverPhoto(HttpServletResponse response) throws IOException {
		
		ArrayList<Coverphoto> list=coverphotoService.queryCoverPhoto();
		
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
	 * 首页使用的饭拍封面查询，限定查前9条
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/queryIndexCoverPhoto")
	
	public void queryIndexCoverPhoto(HttpServletResponse response) throws IOException {
		
		ArrayList<Coverphoto> list=coverphotoService.queryIndexCoverPhoto();
		
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
}
