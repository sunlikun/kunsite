package com.kuncms.sysmanagement.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.kuncms.coverphoto.model.Coverphoto;
import com.kuncms.coverphoto.service.CoverphotoService;
import com.kuncms.sysmanagement.model.Sysmanagement;
import com.kuncms.user.model.User;
import com.kuncms.user.service.UserService;
import com.kuncms.util.HttpClientUtils;

import net.sf.json.JSONArray;




@Controller
@RequestMapping("/SysManagementController")
public class SysManagementController {
	@Autowired
	UserService userServiceService;
	/**
	 * @param map
	 * @return
	 * 跳转到网站监控页面
	 */
	@RequestMapping("/toSysManagement")
    public String signup(Map<String,Object> map){ 

       return "SysManagement";
    }
	
	
	@RequestMapping("/getStatistics")
	@ResponseBody
    public Sysmanagement getStatistics(Map<String,Object> map){ 
		
		Sysmanagement sysmanagement=new Sysmanagement();
		
		String signnum=userServiceService.getStatistics();
		
		sysmanagement.setSignnum(signnum);
		
		return sysmanagement;
    }
	
}
