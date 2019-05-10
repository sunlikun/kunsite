package com.kuncms.cms.controller;

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
import com.kuncms.user.model.User;
import com.kuncms.user.service.UserService;
import com.kuncms.util.HttpClientUtils;

import net.sf.json.JSONArray;




@Controller
@RequestMapping("/CmsController")
public class CmsController {
	
	@Autowired
	UserService userService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 跳转到微信登陆
	 * @param map
	 * @return
	 */
	@RequestMapping("to_UserManage")
    public String wechat_login(Map<String,Object> map,Model model,HttpServletRequest request){
		
		return "UserManage";
    }
	
}
