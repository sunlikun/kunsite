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
import com.kuncms.login.model.LoginRecord;
import com.kuncms.login.service.LoginRecordService;
import com.kuncms.pay.controller.WechatPay;
import com.kuncms.pay.model.AlipayTradeInfo;
import com.kuncms.pay.model.WechatpayTradeinfo;
import com.kuncms.pay.service.AlipayTradeInfoService;
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
	@Autowired
	AlipayTradeInfoService alipayTradeInfoService;
	@Autowired
	LoginRecordService loginRecordService;
	
	
	
	
	/**
	 * 用户登录
	 * @param map
	 * @param user
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/login_operate")
    public String login_operate(Map<String,Object> map,User user,Model model,HttpServletRequest request){
		//日志级别从低到高分为TRACE < DEBUG < INFO < WARN < ERROR < FATAL，如果设置为WARN，则低于WARN的信息都不会输出。
       
        String result="";
		boolean flag;
		ArrayList<User> userl=(ArrayList<User>) userService.login_operate(user);
		
		if(userl.size()>0){
			User loginuser=userl.get(0);
			flag=true;
			model.addAttribute("user_name",loginuser.getUser_name());
			HttpSession session = request.getSession();
	        session.setAttribute("loginName",loginuser.getUser_name());
	        session.setAttribute("gold_coin",loginuser.getGold_coin());
	        session.setAttribute("user",loginuser);
	        
	        //保存登录信息
	        LoginRecord loginRecord=new LoginRecord();
	        loginRecord.setUser_id(loginuser.getId());
	        loginRecord.setUser_name(loginuser.getUser_name());
	        loginRecordService.insertNewLogrec(loginRecord);
	        
	    }else{
			flag=false;
		}
		if(flag==true){
			result="frame";
		}else{
			model.addAttribute("flag","登陆失败,用户名或密码错误!");
			result="/cms/login";
		}
        return result;
    }
	
	
	
	
	/**
	 * 跳转到cms管理系统登录
	 * @param map
	 * @return
	 */
	@RequestMapping("to_cms_login")
    public String to_cms_login(Map<String,Object> map,Model model,HttpServletRequest request){
		
		return "/cms/login";
    }
	
	
	/**
	 * @param response
	 * @param request
	 * @throws IOException
	 * 支付记录查询
	 */
	@RequestMapping("/queryRechargeRecord")
	public void queryRechargeRecord(HttpServletResponse response,HttpServletRequest request) throws IOException {
			
			User loginuser=(User) request.getSession().getAttribute("user");
			
			//微信支付记录
			WechatpayTradeinfo wechatpayTradeinfo=new  WechatpayTradeinfo();
			wechatpayTradeinfo.setCreateBy(loginuser.getUser_name());
			ArrayList<WechatPay> list=wechatpayTradeinfoService.queryRechargeRecord(wechatpayTradeinfo);
			
			AlipayTradeInfo alipayTradeInfo=new AlipayTradeInfo();
			alipayTradeInfo.setUser_name(loginuser.getUser_name());
			ArrayList<AlipayTradeInfo> list1=alipayTradeInfoService.queryRechargeRecord(alipayTradeInfo);
			
			response.setContentType("application/json");
	        response.setHeader("Pragma", "No-cache");
	        response.setHeader("Cache-Control", "no-cache");
	        response.setCharacterEncoding("UTF-8");
	        JSONArray listArray=JSONArray.fromObject(list);
	        JSONArray listArray1=JSONArray.fromObject(list1);
	        JSONObject result=new JSONObject();
	        result.put("wechat", listArray);
	        result.put("alipay", listArray1);
	        PrintWriter out= null;
	        out = response.getWriter();
	        out.print(result.toString());
	        out.flush();
	        out.close();
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
