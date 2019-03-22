package com.kuncms.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
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
import com.kuncms.util.HttpClientUtils;




@Controller
public class CoreController {
	
	@Autowired
	UserService userService;
	
	
	
	
	/**
	 * @param request
	 * @param model
	 * @param code
	 * @return
	 * 用户退出登录
	 */
	@RequestMapping("exit")
    public String exit(HttpServletRequest request,Model model,String code){
		
	  request.getSession().removeAttribute("loginName");
	  request.getSession().invalidate();
    
	  return "index";
    }
	
	
	
	/**
	 * @param request
	 * @param model
	 * @param access_token
	 * @param openid
	 * @return
	 * 获取微信登陆信息并完成网站登陆
	 */
	@RequestMapping("get_user_info")
	public String get_user_info(HttpServletRequest request,Model model,String access_token,String openid){
     
	   String str="";
       String url="https://api.weixin.qq.com/sns/userinfo";
       Map<String, Object> map=new HashMap<>();
       map.put("access_token", access_token);
       map.put("openid", openid);
       HttpClientUtils httpAPIService=new HttpClientUtils();
 		try {
			str = httpAPIService.sendGet(url, map);
		
			JSONObject userobj = JSONObject.parseObject(str);
			if(userobj.get("nickname")!=null){
				//返回前台的用户信息
				model.addAttribute("user_name",userobj.get("nickname"));
				
				//将用户信息放入session
				HttpSession session = request.getSession();
		        session.setAttribute("loginName",userobj.get("nickname"));
		    	System.out.println(userobj.get("nickname"));
		        session.setAttribute("sex",userobj.get("sex"));
		        session.setAttribute("gold_coin",0);
		        
		        //判断此微信用户是否是第一次登陆如果是则在用户表中插入一条新的记录
		        User user=new User();
		        user.setOpenid(openid);
		        user.setIs_wechat("1");
		        user.setUser_name((String) userobj.get("nickname"));
		        ArrayList<User> userlis=userService.isRegister(user);
		        if(userlis.size()>0){//已经注册
		        	  session.setAttribute("gold_coin",userlis.get(0).getGold_coin());
		        }else{//尚未注册
		        	userService.newsignup(user);
		        }
		        
		        
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
	  return "index";
    }
	
	
	/**
	 * @param request
	 * @param model
	 * @param code
	 * @return
	 * 获取access_token
	 */
	@RequestMapping("get_access_token")
	@ResponseBody
    public String get_access_token(HttpServletRequest request,Model model,String code){
     
       System.out.println("微信请求code"+code);
       String str="";
       String url="https://api.weixin.qq.com/sns/oauth2/access_token";
       Map<String, Object> map=new HashMap<>();
       map.put("appid", "wx91f183b1c7308950");
       map.put("secret", "9d92e6d3dd3674d70dcfc3f1b0d157af");
       map.put("code", code);
       map.put("grant_type", "authorization_code");
       HttpClientUtils httpAPIService=new HttpClientUtils();
 		try {
			str = httpAPIService.sendGet(url, map);
			System.out.println(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
	  return str;
    }
	
	
	
	/**
	 * @param map
	 * @param request
	 * @param model
	 * @param code
	 * @param state
	 * @return
	 * 获取微信登陆的code
	 */
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
