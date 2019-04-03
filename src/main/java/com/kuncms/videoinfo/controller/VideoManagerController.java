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
@RequestMapping("/videoManagerController")
public class VideoManagerController {
	@Autowired
	private VideoInfoService videoInfoService;
	
	
    /**
     * @param map
     * @param request
     * @return
     * 跳转到视频相关文件上传的页面
     */
    @RequestMapping("/videoRelevantUpload")
    public String videoRelevantUpload(Map<String,Object> map,HttpServletRequest request){
     
       return "videoRelevantUpload";
    }
	
	
}
