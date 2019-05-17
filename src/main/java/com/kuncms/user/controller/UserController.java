package com.kuncms.user.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.kuncms.user.model.User;
import com.kuncms.user.service.UserService;
@Controller
public class UserController {
	@Autowired
	UserService userService;
	@RequestMapping("/hello")
    public String hello() {
        return "hello!";
    }
	
	
	@RequestMapping("bindEmail")
    @ResponseBody
    public String bindEmail(User user,HttpServletRequest request,HttpServletResponse response){
    	User loginuser=(User) request.getSession().getAttribute("user");
    	user.setId(loginuser.getId());
    	userService.bindEmail(user);
    	JSONObject result=new JSONObject();
    	result.put("result", "绑定成功");
		return result.toJSONString();
    	
		
    }
	
	
	
	/**
	 * @param map
	 * @param user
	 * @param model
	 * @param request
	 * @param response
	 * 删除用户
	 * @throws IOException 
	 */
	@RequestMapping("/deluser")
    public void deluser(Map<String,Object> map,User user,Model model,HttpServletRequest request,HttpServletResponse response) throws IOException{ 
		 user.setFlag("0");
		 userService.deluser(user);
		 response.setContentType("application/json");
	     response.setHeader("Pragma", "No-cache");
	     response.setHeader("Cache-Control", "no-cache");
         response.setCharacterEncoding("UTF-8");
         JSONObject obj=new JSONObject();
         obj.put("result", "删除成功!");
         PrintWriter out= null;
         out = response.getWriter();
         out.print(obj.toJSONString());
         out.flush();
         out.close();
		 
	}
	
	
	
	/**
	 * @param map
	 * @param user
	 * @param model
	 * @param request
	 * @return
	 * 网站管理-用户查询
	 */
	@ResponseBody
	@RequestMapping("/query_userlis")
    public ArrayList<User> query_userlis(Map<String,Object> map,User user,Model model,HttpServletRequest request){
	  
		
		ArrayList<User> userlis=(ArrayList<User>) userService.query_userlis();
		
		return userlis;
	}
	
	
	@ResponseBody
	@RequestMapping("/get_user")
    public User get_user(Map<String,Object> map,User user,Model model,HttpServletRequest request){
	   //ArrayList<Coverphoto> list=thumbnailService.queryCoverPhoto();
	   //com.alibaba.fastjson.JSONArray array= com.alibaba.fastjson.JSONArray.parseArray(JSON.toJSONString(list));
	   //model.addAttribute("data", array.toJSONString());
		
		ArrayList<User> userl=(ArrayList<User>) userService.check_username(user);
		User loginuser=null;
		if(userl.size()>0){
			 loginuser=userl.get(0);
		}
		
		return loginuser;
	
	
    }
	
	
	@RequestMapping("/insertuserinfo")
    public String insertuserinfo() {
        return "hello!";
    }
	
	//查出用户数据，在页面展示
    @RequestMapping("/success")
    public String success(Map<String,Object> map){
        map.put("hello","<h1>你好</h1>");
        map.put("users",Arrays.asList("zhangsan","lisi","wangwu"));
        return "success";
    }
    
  //新用户注册
    
    @RequestMapping("/newsignup")
    public String newsignup(Map<String,Object> map,User user,Model model) throws ParseException{
    	userService.newsignup(user);
    	model.addAttribute("username",user.getUser_name());
    	model.addAttribute("password",user.getPassword());
    	return "success";
    }
}
