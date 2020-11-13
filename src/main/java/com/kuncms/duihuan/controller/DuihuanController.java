package com.kuncms.duihuan.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kuncms.coverphoto.model.Coverphoto;
import com.kuncms.duihuan.model.Duihuan;
import com.kuncms.duihuan.model.DuihuanRecord;
import com.kuncms.duihuan.service.DuihuanRecordService;
import com.kuncms.duihuan.service.DuihuanService;
import com.kuncms.user.model.User;
import com.kuncms.user.service.UserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Controller
@RequestMapping("/DuihuanController")
public class DuihuanController {
	@Autowired
	UserService userService;
	@Autowired
	DuihuanService  duihuanService;
	@Autowired
	DuihuanRecordService duihuanRecordService;
	
	@RequestMapping("to_Duihuanma_management")
    public String to_Duihuanma_management(Map<String,Object> map,Model model,HttpServletRequest request){
		
		return "Duihuanma_management";
    }
	@RequestMapping("to_duihuan")
    public String to_duihuan(Map<String,Object> map,Model model,HttpServletRequest request){
		
		return "activity/duihuan";
    }
	@RequestMapping("duihuancode_add")
    public String duihuancode_add(Map<String,Object> map,Model model,HttpServletRequest request){
		
		return "duihuan";
    }
	
	@RequestMapping("duihuan_operate")
	@ResponseBody
    public String duihuan_operate(Map<String,Object> map,Model model,HttpServletRequest request,HttpServletResponse response,Duihuan duihuan) throws IOException{
		
		ArrayList<Duihuan> list=duihuanService.queryDuihuancode(duihuan);
		String result="";
		HttpSession session = request.getSession();
		User user=(User) session.getAttribute("user");
		if(list.size()>0){//兑换码有效
		
			DuihuanRecord duihuanRecord1=new DuihuanRecord();
			duihuanRecord1.setUser_id(user.getId());
			duihuanRecord1.setDuihuan_code(duihuan.getDuihuan_code());
			ArrayList<DuihuanRecord> duihuanRecordArr=duihuanRecordService.query(duihuanRecord1);
			//如果当前用户没有兑换过
			if(duihuanRecordArr.size()==0){
				//增加用户金币数
				ArrayList<User> userArr=userService.check_username(user);
				User user1=userArr.get(0);
				int now_gold_coin=user1.getGold_coin()+10;
				userService.addUserGoldCoin(user.getId(), now_gold_coin);
				DuihuanRecord duihuanRecord=new DuihuanRecord();
				duihuanRecord.setDuihuan_code(duihuan.getDuihuan_code());
				duihuanRecordService.save(duihuanRecord, request);
				result="兑换成功";
			}else{
				result="您已经进行了金币的兑换";
			}
			
		}else{//兑换码无效
			result="兑换失败";
		};
		
//		 response.setContentType("application/json");
//	        response.setHeader("Pragma", "No-cache");
//	        response.setHeader("Cache-Control", "no-cache");
//	        response.setCharacterEncoding("UTF-8");
	        
	        //JSONArray listArray=JSONArray.fromObject(list);    
	        JSONObject json=new JSONObject();
	        json.put("result",result);
//	        PrintWriter out= null;
//	        out = response.getWriter();
//	        out.print(json.toString());
//	        System.out.println(json.toString());
//	        out.flush();
//	        out.close();
	        return json.toString();
    }
	

}
