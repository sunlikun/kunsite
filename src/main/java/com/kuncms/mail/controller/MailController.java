package com.kuncms.mail.controller;

import java.util.ArrayList;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kuncms.mail.service.MailService;
import com.kuncms.user.model.User;
import com.kuncms.user.service.UserService;

@Controller
@RequestMapping("MailController")
public class MailController {
    @Autowired
    private MailService mailService;
    @Autowired
	UserService userService;
    
    
    /**
     * @param user
     * @param request
     * @param response
     * @return
     * 绑定邮箱
     */
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
     * @param email
     * @return
     * 发送邮件并返回邮箱验证码
     */
    @RequestMapping("getCheckCode")
    @ResponseBody
    public String getCheckCode(String email){
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        String message = "您的普格娱乐邮箱绑定验证码为："+checkCode;
        try {
            mailService.sendSimpleMail(email, "注册验证码", message);
        }catch (Exception e){
            return "";
        }
        return checkCode;
    }
    
    
    /**
     * @param email
     * @param user
     * @return
     * 校验邮箱是否已经绑定
     */
    @RequestMapping("checkEmail")
    @ResponseBody
    public User checkEmail(String email,User user){
    	ArrayList<User> userl=(ArrayList<User>) userService.check_username(user);
		User loginuser=null;
		if(userl.size()>0){
			 loginuser=userl.get(0);
		}else{
			loginuser=new User();
			loginuser.setEmail("0");
		}
		
		return loginuser;
    }
}