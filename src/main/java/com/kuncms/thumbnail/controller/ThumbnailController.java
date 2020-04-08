package com.kuncms.thumbnail.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kuncms.coverphoto.dao.CoverphotoDao;
import com.kuncms.coverphoto.model.Coverphoto;
import com.kuncms.coverphoto.service.CoverphotoService;
import com.kuncms.downloadrecord.model.DownloadRecord;
import com.kuncms.downloadrecord.service.DownloadRecordService;
import com.kuncms.thumbnail.service.ThumbnailService;
import com.kuncms.user.model.User;
import com.kuncms.user.service.UserService;
import com.kuncms.videoinfo.model.Videoinfo;
import com.kuncms.videoinfo.service.VideoInfoService;

import net.sf.json.JSONArray;

@Controller
public class ThumbnailController {
	
	@Autowired
	ThumbnailService thumbnailService;
	@Autowired
	CoverphotoService coverphotoService;
	@Autowired
	UserService userService;
	@Autowired
	DownloadRecordService downloadRecordService;
	
	/**
	 * @param map
	 * @param id
	 * @param user
	 * @param model
	 * @param request
	 * @param t_gold_coin
	 * @return
	 * 跳转到下载页
	 * @throws ParseException 
	 */
	@RequestMapping("/todown")
    public String todown(Map<String,Object> map,String id,User user,Model model,HttpServletRequest request,int t_gold_coin) throws ParseException{
	   
		Coverphoto coverphoto=new Coverphoto();
		DownloadRecord downloadRecord=new DownloadRecord();
	    coverphoto.setId(id);
	    ArrayList<Coverphoto> list=coverphotoService.queryCoverPhotoById(coverphoto);
	    
	   
 	    HttpSession session=request.getSession();
 	    User loginuser=(User) session.getAttribute("user");
 	    ArrayList<User> userl=(ArrayList<User>) userService.check_username(loginuser);
	    if(list.size()>0){
	       Coverphoto coverphoto1=list.get(0);
 		   String url=list.get(0).getBaiduyun_address();
 		   model.addAttribute("url",url);
 		   //修改当前作品的下载量
 		   coverphoto1.setDownloads(coverphoto1.getDownloads()+1);
 		   coverphotoService.updateCoverPhotoById(coverphoto1);
 		   //增加下载记录
 		   String videoId=list.get(0).getId();
 		   downloadRecord.setVideo_id(videoId); 
 		   downloadRecord.setUser_name(userl.get(0).getUser_name());
 		   downloadRecord.setUser_id(userl.get(0).getId());
 		   downloadRecordService.insert(downloadRecord);
 	   };
 	   
 	  //扣除用户对应的金币
		String returnPage="";
		if(userl.size()>0){
			 User loginuser1=userl.get(0);
			 int  goldCoin=loginuser1.getGold_coin();
			 if(goldCoin<0){//当账户金币小于0时为异常情况时，跳转到异常页面
				 returnPage="/error/error";
			 }
			 int  rgoldCoin=goldCoin-t_gold_coin;
			 int  empirical_value=loginuser1.getEmpirical_value();
			 empirical_value=empirical_value+1;
			 loginuser1.setGold_coin(rgoldCoin);
			 loginuser1.setEmpirical_value(empirical_value);
			 userService.update(loginuser1);
			 returnPage="download";
		}
		
		
		return returnPage;
	
	
    }
	
	
	
	
	@ResponseBody
	@RequestMapping("/download")
    public String details(Map<String,Object> map,String id,Model model,HttpServletRequest request){
		
	  //判断用户是否登陆，如果没有则禁止下载
		HttpSession session=request.getSession();
		String user_name=(String) session.getAttribute("loginName");
		String result="";
		JSONObject resultj = new JSONObject();
		if(user_name!=null&&!user_name.equals("")){
			 //根据id查询出对应的视频信息
			   Coverphoto coverphoto=new Coverphoto();
			 
			   coverphoto.setId(id);
			   ArrayList<Coverphoto> list=coverphotoService.queryCoverPhotoById(coverphoto);
			   
		      System.out.println(id+"id");
		       try {
		    	   if(list.size()>0){
		    		   String url=list.get(0).getBaiduyun_address();
		    		  
		    		   //browse(url);
		    		   resultj.put("url",url);
		    		 
		    	   };
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    result="login";
		}else{
			result="nologin";
		}
		resultj.put("result", result);
	 
		return resultj.toJSONString();
    }
	
	private static void browse(String url) throws Exception {
        //获取操作系统的名字
//        String osName = System.getProperty("os.name", "");
//        if (osName.startsWith("Mac OS")) {
//            //苹果的打开方式
//            Class fileMgr = Class.forName("com.apple.eio.FileManager");
//            Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] { String.class });
//            openURL.invoke(null, new Object[] { url });
        //} else if (osName.startsWith("Windows")) {
            //windows的打开方式。
            System.out.println("111");
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
//        } else {
//            // Unix or Linux的打开方式
//            String[] browsers = { "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape","chrome" };
//            String browser = null;
//            for (int count = 0; count < browsers.length && browser == null; count++)
//                //执行代码，在brower有值后跳出，
//                //这里是如果进程创建成功了，==0是表示正常结束。
//                if (Runtime.getRuntime().exec(new String[] { "which", browsers[count] }).waitFor() == 0)
//                    browser = browsers[count];
//            if (browser == null)
//                throw new Exception("Could not find web browser");
//            else
//                //这个值在上面已经成功的得到了一个进程。
//                Runtime.getRuntime().exec(new String[] { browser, url });
//        }
    }

	
	
	/**
	 * 跳转到缩略图管理页面
	 * @param map
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/thumbnailManage")
    public String thumbnailManage(Map<String,Object> map,@RequestParam("id")String id,Model model){
	   //ArrayList<Coverphoto> list=thumbnailService.queryCoverPhoto();
	   //com.alibaba.fastjson.JSONArray array= com.alibaba.fastjson.JSONArray.parseArray(JSON.toJSONString(list));
	   //model.addAttribute("data", array.toJSONString());
		System.out.println(id);
		model.addAttribute("id",id);
       return "ThumbnailManage";
    }
	
	
	
	/**
	 * @param map
	 * @param id
	 * @param baiduyun_pass
	 * @param baiduyun_address
	 * @param t_gold_coin
	 * @param model
	 * @param request
	 * @return
	 * 跳转到详情页
	 */
	@RequestMapping("/details")
    public String details(Map<String,Object> map,String id,String baiduyun_pass,String baiduyun_address,String t_gold_coin,String online_play_coin,Model model,HttpServletRequest request){
	  
		//取出当前作品的点击率并加1
		Coverphoto coverphoto=new Coverphoto();
		coverphoto.setId(id);
		ArrayList<Coverphoto> list=coverphotoService.queryCoverPhotoById(coverphoto);
		Coverphoto coverphoto1=list.get(0);
		coverphoto1.setClicks(coverphoto1.getClicks()+1);
		coverphotoService.updateCoverPhotoById(coverphoto1);
	   
		
		model.addAttribute("id",id);
		model.addAttribute("t_gold_coin",t_gold_coin);
		model.addAttribute("clicks",coverphoto1.getClicks());

		HttpSession session=request.getSession();
		if(session.getAttribute("loginName")!=null){
			String user_name=(String) session.getAttribute("loginName");
			User user_login=(User) session.getAttribute("user");
			String user_id=user_login.getId();
			int gold_coin=(int) session.getAttribute("gold_coin");
			model.addAttribute("user_name",user_name);
			model.addAttribute("user_id",user_id);
			model.addAttribute("gold_coin",gold_coin);
		}
		
		
		System.out.println("t_gold_coin"+t_gold_coin);
		model.addAttribute("baiduyun_pass",baiduyun_pass);
		model.addAttribute("baiduyun_address",baiduyun_address);
		model.addAttribute("baiduyun_address",online_play_coin);
       return "details";
    }
	
	
	
	
	@RequestMapping("/queryThumbnail")
	
	public void queryThumbnail(HttpServletResponse response,String id) throws IOException {
		
		ArrayList<Coverphoto> list=thumbnailService.queryThumbnail(id);
		System.out.println(id);
		
		 response.setContentType("application/json");
	        response.setHeader("Pragma", "No-cache");
	        response.setHeader("Cache-Control", "no-cache");
	        response.setCharacterEncoding("UTF-8");
	        
	        JSONArray listArray=JSONArray.fromObject(list);     
	        PrintWriter out= null;
	        out = response.getWriter();
	        out.print(listArray.toString());
	        System.out.println(listArray.toString());
	        out.flush();
	        out.close();
		
		
	}
}
