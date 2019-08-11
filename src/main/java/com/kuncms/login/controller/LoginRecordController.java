package com.kuncms.login.controller;

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
import com.kuncms.login.model.LoginRecord;
import com.kuncms.login.service.LoginRecordService;
import com.kuncms.user.model.User;
import com.kuncms.user.service.UserService;
@Controller
@RequestMapping("/LoginRecordController")
public class LoginRecordController {
	@Autowired
	LoginRecordService loginRecordService;
	/**
	 * 保存登录记录
	 * @param map
	 * @param loginRecord
	 * @param model
	 * @throws ParseException
	 */
	@RequestMapping("/insertNewLogrec")
    public void insertNewLogrec(Map<String,Object> map,LoginRecord loginRecord,Model model) throws ParseException{
		loginRecordService.insertNewLogrec(loginRecord);
    }
}
