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
import com.kuncms.pay.service.WechatpayTradeinfoService;
import com.kuncms.user.model.User;
import com.kuncms.user.service.UserService;
import com.kuncms.util.HttpClientUtils;

import net.sf.json.JSONArray;




@Controller
@RequestMapping("/CmsController")
public class CmsController {
	
	@Autowired
	UserService userService;
	@Autowired
	WechatpayTradeinfoService wechatpayTradeinfoService;
	
	@RequestMapping("queryRechargeRecord")
	public void queryRechargeRecord(HttpServletResponse response,User user) throws IOException {
		
			//ArrayList<WechatPay> list=wechatpayTradeinfoService.queryRechargeRecord(user);
		
//		 	response.setContentType("application/json");
//	        response.setHeader("Pragma", "No-cache");
//	        response.setHeader("Cache-Control", "no-cache");
//	        response.setCharacterEncoding("UTF-8");
//	        
//	        JSONArray listArray=JSONArray.fromObject(list);     
//	        PrintWriter out= null;
//	        out = response.getWriter();
//	        out.print(listArray.toString());
//	        System.out.println(listArray.toString());
//	        out.flush();
//	        out.close();
	
		
		
	}
	
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 跳转到用户管理
	 * @param map
	 * @return
	 */
	@RequestMapping("to_UserManage")
    public String to_UserManage(Map<String,Object> map,Model model,HttpServletRequest request){
		
		return "UserManage";
    }
	
	/**
	 * 跳转到用户充值记录
	 * @param map
	 * @return
	 */
	@RequestMapping("RechargeRecord")
    public String to_RechargeRecord(Map<String,Object> map,Model model,HttpServletRequest request){
		
		return "RechargeRecord";
    }
	
	
	
	
}
