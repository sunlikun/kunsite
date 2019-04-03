package com.kuncms.videoinfo.controller;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String index(Map<String,Object> map,HttpServletRequest request,Model model,String code){
       String videocode=request.getParameter("videocode");
       //Videoinfo  videoinfo=videoInfoService.serachvideo("videocode");
       System.out.println("首页");
       HttpSession session=request.getSession();
       String user_name=(String) session.getAttribute("loginName");
	   model.addAttribute("user_name",user_name);
	   model.addAttribute("code",code);
       //map.put("hello",videoinfo);
       return "index";
    }
}
