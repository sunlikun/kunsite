package com.kuncms.coverphoto.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kuncms.coverphoto.dao.CoverphotoDao;
import com.kuncms.coverphoto.model.Coverphoto;
import com.kuncms.coverphoto.service.CoverphotoService;
import com.kuncms.downloadrecord.model.DownloadRecord;
import com.kuncms.downloadrecord.service.DownloadRecordService;
import com.kuncms.videoinfo.model.Videoinfo;
import com.kuncms.videoinfo.service.VideoInfoService;

import net.sf.json.JSONArray;
import sun.rmi.runtime.Log;
@Controller
public class CoverphotoController {
	@Autowired
	private CoverphotoDao coverphotoDao;
	@Autowired
	CoverphotoService coverphotoService;
	@Autowired
	DownloadRecordService downloadRecordService;
	
	
	/**
	 * @param map
	 * @param request
	 * @return
	 * 跳转到vip点播码查看页（微信使用）
	 */
	@RequestMapping("/toVipPlayNumInfo")
    public String toVipPlayNumInfo(Map<String,Object> map,HttpServletRequest request){
   
    
		return "VipPlay";
    }
	
	
	
	
	/**
	 * @param map
	 * @param request
	 * @return
	 * 跳转到点播码查看页（微信使用）
	 */
	@RequestMapping("/toPlayNumInfo")
    public String toPlayNumInfo(Map<String,Object> map,HttpServletRequest request){
   
    
		return "PlayNum";
    }
	
	@RequestMapping("queryVipPlayNumInfoCount")
	public void queryVipPlayNumInfoCount(HttpServletResponse response,Coverphoto coverphoto) throws IOException {
		    //查询微信支付记录
			ArrayList<Coverphoto> list=coverphotoService.queryVipPlayNumInfoCount(coverphoto);
		
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
	
	@RequestMapping("queryPlayNumInfoCount")
	public void queryPlayNumInfoCount(HttpServletResponse response,Coverphoto coverphoto) throws IOException {
		    //查询微信支付记录
			ArrayList<Coverphoto> list=coverphotoService.queryPlayNumInfoCount(coverphoto);
		
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
	
	/**
	 * @param response
	 * @param currentPage
	 * @param pageSize
	 * @throws IOException
	 * 点播信息获取页信息查询
	 */
	@RequestMapping("queryPlayNumInfo")
	
	public void queryPlayNumInfo(HttpServletResponse response,int currentPage, int pageSize,Coverphoto coverphoto) throws IOException {
		//System.out.println(currentPage+"   "+pageSize);
			PageHelper.startPage(currentPage , pageSize);
		 	ArrayList<Coverphoto> list=coverphotoService.queryPlayNumInfo(coverphoto);
	        //得到分页的结果对象
	        PageInfo<Coverphoto> personPageInfo = new PageInfo<>(list);
	        //得到分页中的person条目对象
	        List<Coverphoto> pageList = personPageInfo.getList();
	       
	        JSONArray listArray=JSONArray.fromObject(pageList);     
	        response.setContentType("application/json");
	        response.setHeader("Pragma", "No-cache");
	        response.setHeader("Cache-Control", "no-cache");
	        response.setCharacterEncoding("UTF-8");
	        PrintWriter out= null;
	        out = response.getWriter();
	        out.print(listArray.toString());
	        System.out.println(listArray.toString());
	        out.flush();
	        out.close();
	}
	
	
	
	
	/**
	 * @param response
	 * @param coverphoto
	 * @return
	 * @throws IOException
	 * 视频信息更新
	 */
	@RequestMapping("/upVideo")
	@ResponseBody
	public Object upVideo(HttpServletResponse response,Coverphoto coverphoto) throws IOException {
			coverphotoService.upVideo(coverphoto);
			JSONObject result=new JSONObject();
			result.put("result", "更新成功");
			return result.toString();
	}
	
	/**
	 * @param response
	 * @param coverphoto
	 * @return 
	 * @throws IOException
	 * 视频删除
	 */
	@RequestMapping("/delVideo")
	@ResponseBody
	public Object delVideo(HttpServletResponse response,Coverphoto coverphoto) throws IOException {
			coverphoto.setFlag("0");
			coverphotoService.upVideo(coverphoto);
			JSONObject result=new JSONObject();
			result.put("result", "视频删除成功");
			return result.toString();
	}
	
	
	@RequestMapping("/queryrankingList")
	public void queryrankingList(HttpServletResponse response,String flag) throws IOException {
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		Calendar c = Calendar.getInstance();
//		//根据flag的值查出视频的id
//		String start_time="";
//		String end_time="";
//		c.setTime(new Date());
//	    Date d1 = c.getTime();
//        String day1 = format.format(d1);
//        end_time=day1;
		ArrayList<DownloadRecord> recordlist=new ArrayList<DownloadRecord>();
		if(flag.equals("1")){//当天
			
			 recordlist=downloadRecordService.queryRecordDayBy();
	         
	      
		}
		if(flag.equals("2")){//当周
			 recordlist=downloadRecordService.queryRecordIWeek();
	       
		}
		if(flag.equals("3")){//当月
			
			 recordlist=downloadRecordService.queryRecordMonth();
		}
		ArrayList<Coverphoto> list=new ArrayList<>();
		if(recordlist.size()>0){
			String[] idArr=new String[recordlist.size()];
			for(int i=0;i<recordlist.size();i++){
				idArr[i]=recordlist.get(i).getVideo_id();
			}
		    list=coverphotoService.queryVideo(idArr);
		}
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
	
	
	@RequestMapping("queryAllCoverPhoto")
	public void queryAllCoverPhoto(HttpServletResponse response,Coverphoto coverphoto) throws IOException {
		    //查询微信支付记录
			ArrayList<Coverphoto> list=coverphotoService.queryCoverPhoto(coverphoto);
		
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
	
	
	
	/**
	 * @param response
	 * @param currentPage
	 * @param pageSize
	 * @throws IOException
	 * 更多页面进行分页
	 */
	@RequestMapping("/queryMoreCoverPhoto")
	
	public void queryMoreCoverPhoto(HttpServletResponse response,int currentPage, int pageSize,Coverphoto coverphoto) throws IOException {
		//System.out.println(currentPage+"   "+pageSize);
			PageHelper.startPage(currentPage , pageSize);
		 	ArrayList<Coverphoto> list=coverphotoService.queryCoverPhoto(coverphoto);
	        //得到分页的结果对象
	        PageInfo<Coverphoto> personPageInfo = new PageInfo<>(list);
	        //得到分页中的person条目对象
	        List<Coverphoto> pageList = personPageInfo.getList();
	       
	        JSONArray listArray=JSONArray.fromObject(pageList);     
	        response.setContentType("application/json");
	        response.setHeader("Pragma", "No-cache");
	        response.setHeader("Cache-Control", "no-cache");
	        response.setCharacterEncoding("UTF-8");
	        PrintWriter out= null;
	        out = response.getWriter();
	        out.print(listArray.toString());
	        System.out.println(listArray.toString());
	        out.flush();
	        out.close();
	}
	
	
	@RequestMapping("/videomanage")
    public String videomanage(Map<String,Object> map,HttpServletRequest request,Model model){
	   ArrayList<Coverphoto> list=coverphotoService.queryCoverPhoto();
	   com.alibaba.fastjson.JSONArray array= com.alibaba.fastjson.JSONArray.parseArray(JSON.toJSONString(list));
	   model.addAttribute("data", array.toJSONString());

       return "VideoManage";
    }
	
	/**
	 * @param map
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping("/videoadd")
    public String videoadd(Map<String,Object> map,HttpServletRequest request){
       String videocode=request.getParameter("videocode");
       //Videoinfo  videoinfo=videoInfoService.serachvideo("videocode");
       System.out.println("视频管理");
       //map.put("hello",videoinfo);
       return "videoadd";
    }
	
	
	@RequestMapping("/queryCoverPhoto")
	
	public void queryCoverPhoto(HttpServletResponse response) throws IOException {
		
		ArrayList<Coverphoto> list=coverphotoService.queryCoverPhoto();
		
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
	
	/**
	 * 首页使用视频查询，限定查前9条
	 * @param response
	 */
	@RequestMapping("/queryIndexCoverPhoto")
	
	public void queryIndexCoverPhoto(HttpServletResponse response,Coverphoto coverphoto) throws IOException {
		
		ArrayList<Coverphoto> list=coverphotoService.queryIndexCoverPhoto(coverphoto);
		
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
