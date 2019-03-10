package com.kuncms.videoinfo.controller;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kuncms.videoinfo.model.Videoinfo;
import com.kuncms.videoinfo.service.VideoInfoService;
@Controller
public class VideoInfoController {
	@Autowired
	private VideoInfoService videoInfoService;
	
	
	//根据视频编码查询相应的视频
    @RequestMapping("/serachvideo")
    public String serachvideo(Map<String,Object> map,HttpServletRequest request){
       String videocode=request.getParameter("videocode");
       //Videoinfo  videoinfo=videoInfoService.serachvideo("videocode");
       System.out.println("查询视频");
       //map.put("hello",videoinfo);
       return "playvideo";
    }
    
    
    @RequestMapping("/index")
    public String index(Map<String,Object> map,HttpServletRequest request){
       String videocode=request.getParameter("videocode");
       //Videoinfo  videoinfo=videoInfoService.serachvideo("videocode");
       System.out.println("首页");
       //map.put("hello",videoinfo);
       return "index";
    }
}
