package com.kuncms.core;

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
import org.dom4j.DocumentException;
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
import com.kuncms.user.model.User;
import com.kuncms.user.service.UserService;
import com.kuncms.util.HttpClientUtils;
import com.kuncms.wxgzh.model.MessageUtil;
import com.kuncms.wxgzh.model.WeChatConfig;
import com.kuncms.wxgzh.model.WeiXinUserInfo;
import com.kuncms.wxgzh.model.WeixinUtil;

import net.sf.json.JSONArray;




@Controller
@RequestMapping("/CommonController")
public class CommonController {
	
	/**
	 * 跳转到错误页
	 * @param map
	 * @return
	 */
	@RequestMapping("/error")
    public String contact(Map<String,Object> map,Model model,HttpServletRequest request){
	
		
       return "/error/error";
    }
	
}
