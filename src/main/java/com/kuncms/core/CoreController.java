package com.kuncms.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.kuncms.user.model.User;
import com.kuncms.user.service.UserService;

@Controller
public class CoreController {
	
	@Autowired
	UserService userService;
	
	
	
	
	
	@RequestMapping("/getcode")
    public String getcode(Map<String,Object> map,HttpServletRequest request,Model model,String code,String state){
     
       System.out.println(code+"  "+state);
      
	   model.addAttribute("state",state);
	   model.addAttribute("code",code);
       
       return "index";
    }
	
	
	
	
	
	
	
	@RequestMapping("/bootstrap")
    public String bootstrap(Map<String,Object> map){
	   //ArrayList<Coverphoto> list=thumbnailService.queryCoverPhoto();
	   //com.alibaba.fastjson.JSONArray array= com.alibaba.fastjson.JSONArray.parseArray(JSON.toJSONString(list));
	   //model.addAttribute("data", array.toJSONString());

       return "bootstrap";
    }
	
	/**
	 * 跳转到微信登陆
	 * @param map
	 * @return
	 */
	@RequestMapping("wechat_login")
    public String wechat_login(Map<String,Object> map,Model model,HttpServletRequest request){
	   //ArrayList<Coverphoto> list=thumbnailService.queryCoverPhoto();
	   //com.alibaba.fastjson.JSONArray array= com.alibaba.fastjson.JSONArray.parseArray(JSON.toJSONString(list));
	   //model.addAttribute("data", array.toJSONString());
		//HttpSession session=request.getSession();
		//String user_name=(String) session.getAttribute("loginName");
		//model.addAttribute("user_name",user_name);
		
       return "wechat_login";
    }
	
	
	
	/**
	 * 跳转到充值页面
	 * @param map
	 * @return
	 */
	@RequestMapping("/recharge")
    public String recharge(Map<String,Object> map,Model model,HttpServletRequest request){
	   //ArrayList<Coverphoto> list=thumbnailService.queryCoverPhoto();
	   //com.alibaba.fastjson.JSONArray array= com.alibaba.fastjson.JSONArray.parseArray(JSON.toJSONString(list));
	   //model.addAttribute("data", array.toJSONString());
		HttpSession session=request.getSession();
		String user_name=(String) session.getAttribute("loginName");
		model.addAttribute("user_name",user_name);
		
       return "recharge";
    }
	
	
	
	
	/**
	 * 跳转到会员中心
	 * @param map
	 * @return
	 */
	@RequestMapping("/membership")
    public String membership(Map<String,Object> map,Model model,HttpServletRequest request){
	   //ArrayList<Coverphoto> list=thumbnailService.queryCoverPhoto();
	   //com.alibaba.fastjson.JSONArray array= com.alibaba.fastjson.JSONArray.parseArray(JSON.toJSONString(list));
	   //model.addAttribute("data", array.toJSONString());
		HttpSession session=request.getSession();
		String user_name=(String) session.getAttribute("loginName");
		model.addAttribute("user_name",user_name);
		
       return "membership";
    }
	
	
	
	/**
	 * 跳转到更多饭拍
	 * @param map
	 * @return
	 */
	@RequestMapping("/more")
    public String more(Map<String,Object> map){
	   //ArrayList<Coverphoto> list=thumbnailService.queryCoverPhoto();
	   //com.alibaba.fastjson.JSONArray array= com.alibaba.fastjson.JSONArray.parseArray(JSON.toJSONString(list));
	   //model.addAttribute("data", array.toJSONString());

       return "more";
    }
	
	
	
	/**
	 * 跳转到cms的框架的首页
	 * @param map
	 * @return
	 */
	@RequestMapping("/frame")
    public String thumbnailManage(Map<String,Object> map){
	   //ArrayList<Coverphoto> list=thumbnailService.queryCoverPhoto();
	   //com.alibaba.fastjson.JSONArray array= com.alibaba.fastjson.JSONArray.parseArray(JSON.toJSONString(list));
	   //model.addAttribute("data", array.toJSONString());

       return "frame";
    }
	
	/**
	 * 跳转到登陆页面
	 * @param map
	 * @return
	 */
	@RequestMapping("/login")
    public String login(Map<String,Object> map){
	   //ArrayList<Coverphoto> list=thumbnailService.queryCoverPhoto();
	   //com.alibaba.fastjson.JSONArray array= com.alibaba.fastjson.JSONArray.parseArray(JSON.toJSONString(list));
	   //model.addAttribute("data", array.toJSONString());

       return "login";
    }
	
	/**
	 * 跳转到注册页面
	 * @param map
	 * @return
	 */
	@RequestMapping("/signup")
    public String signup(Map<String,Object> map){
	   //ArrayList<Coverphoto> list=thumbnailService.queryCoverPhoto();
	   //com.alibaba.fastjson.JSONArray array= com.alibaba.fastjson.JSONArray.parseArray(JSON.toJSONString(list));
	   //model.addAttribute("data", array.toJSONString());

       return "signup";
    }
	
	
	@RequestMapping("/login_operate")
    public String login_operate(Map<String,Object> map,User user,Model model,HttpServletRequest request){
	   //ArrayList<Coverphoto> list=thumbnailService.queryCoverPhoto();
	   //com.alibaba.fastjson.JSONArray array= com.alibaba.fastjson.JSONArray.parseArray(JSON.toJSONString(list));
	   //model.addAttribute("data", array.toJSONString());
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
		}else{
			flag=false;
		}
		if(flag==true){
			result="index";
		}else{
			model.addAttribute("flag","登陆失败,用户名或密码错误!");
			result="login";
		}
        return result;
    }
	
	@ResponseBody
	@RequestMapping("/login_check")
    public String login_check(Map<String,Object> map,User user,Model model,HttpServletRequest request){
	   //ArrayList<Coverphoto> list=thumbnailService.queryCoverPhoto();
	   //com.alibaba.fastjson.JSONArray array= com.alibaba.fastjson.JSONArray.parseArray(JSON.toJSONString(list));
	   //model.addAttribute("data", array.toJSONString());
		
		String result="";
		boolean flag;
		ArrayList<User> userl=(ArrayList<User>) userService.login_operate(user);
		JSONObject resultj = new JSONObject();
		if(userl.size()>0){
			User loginuser=userl.get(0);
			flag=true;
			model.addAttribute("user_name",loginuser.getUser_name());
			HttpSession session = request.getSession();
	        session.setAttribute("loginName",loginuser.getUser_name());
		}else{
			flag=false;
		}
		if(flag==true){
		    resultj.put("msg", "ok");
			resultj.put("method", "@ResponseBody");
			result="登陆成功";
		}else{
			 resultj.put("msg", "fail");
			 resultj.put("method", "@ResponseBody");
			 result="登陆失败";
		}
		// 将获取的json数据封装一层，然后在给返回
	   
	  
	    resultj.put("result", result);

	    return resultj.toJSONString();
    }
	
	@ResponseBody
	@RequestMapping("/check_username")
    public String check_username(Map<String,Object> map,User user,Model model,HttpServletRequest request){
	   //ArrayList<Coverphoto> list=thumbnailService.queryCoverPhoto();
	   //com.alibaba.fastjson.JSONArray array= com.alibaba.fastjson.JSONArray.parseArray(JSON.toJSONString(list));
	   //model.addAttribute("data", array.toJSONString());
		
		String result="";
		
		ArrayList<User> userl=(ArrayList<User>) userService.check_username(user);
		
		if(userl.size()>0){
			User loginuser=userl.get(0);
			result="用户名重复";
			
		}else{
			result="可以注册";
		}
		
		// 将获取的json数据封装一层，然后在给返回
	    JSONObject resultj = new JSONObject();
	    resultj.put("msg", "ok");
	    resultj.put("method", "@ResponseBody");
	    resultj.put("result", result);

	    return resultj.toJSONString();
	
	
    }
	
	
	
	
	
	
}
