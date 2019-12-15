package com.kuncms.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSONObject;
import com.kuncms.coverphoto.dao.CoverphotoDao;
import com.kuncms.coverphoto.model.Coverphoto;
import com.kuncms.coverphoto.model.Play_num_record;
import com.kuncms.coverphoto.service.CoverphotoService;
import com.kuncms.thumbnail.model.Thumbnail;
import com.kuncms.thumbnail.service.ThumbnailService;

@Controller
public class FileUploadController {
	@Autowired
	private CoverphotoService coverphotoService;
	@Autowired
	private ThumbnailService thumbnailService;
	
	
	  /**
     * 缩略图上传（centos使用）
	 * @throws IOException 
     * */
    @RequestMapping("picturesUpload")
     
    public void picturesUpload(@RequestParam("fileName") MultipartFile[] fileArr,HttpServletRequest request,Thumbnail thumbnail,HttpServletResponse response) throws IOException{
        
        String status="";
        for(int i=0;i<fileArr.length;i++) {
        	if(!(fileArr[i].isEmpty())){
        		String fileName = fileArr[i].getOriginalFilename();
                int size = (int) fileArr[i].getSize();
                //System.out.println(fileName + "-->" + size);
                String filePath = ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/img/";
                //String path = "/img/"+fileName ;
                fileName =generateRandomFilename() + fileName.substring(fileName.lastIndexOf("."));
                String path = "/usr/local/kun_cms/thun";
                File dest = new File(path + "/" + fileName);
                //File dest = new File(filePath + "/" + fileName);
                //String filepath=filePath + "/" + fileName;
                if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
                    dest.getParentFile().mkdir();
                }
                try {
               	 fileArr[i].transferTo(dest); //保存文件
                    //保存文件进入数据库
                    thumbnailService.insert(path+"/"+fileName,fileName,thumbnail,request);
                    status="1";
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    status="2";
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    status="2";
                }
        	}
        	 
        }
        
        JSONObject json=new JSONObject();
        json.put("status", status);
        response.setContentType("application/json");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out= null;
        out = response.getWriter();
        out.print(json.toString());
        System.out.println(json.toString());
        out.flush();
        out.close();
       
    }
	
	/**
     * 图上传（windows使用）
	 * @throws IOException 
     * */
    @RequestMapping("test_picturesUpload")
     
    public void test_picturesUpload(@RequestParam("fileName") MultipartFile[] fileArr,HttpServletRequest request,Thumbnail thumbnail,HttpServletResponse response) throws IOException{
    	
        String status="";
        for(int i=0;i<fileArr.length;i++) {
        	if(!(fileArr[i].isEmpty())){
        		 String fileName = fileArr[i].getOriginalFilename();
                 int size = (int) fileArr[i].getSize();
                 System.out.println(fileName + "-->" + size);
                 String filePath = ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/img/";
                 String path = "/img/"+fileName ;
                 File dest = new File(filePath + "/" + fileName);
                 String filepath=filePath + "/" + fileName;
                 if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
                     dest.getParentFile().mkdir();
                 }
                 try {
               	  fileArr[i].transferTo(dest); //保存文件
                     //保存文件进入数据库
                     thumbnailService.insert(path,fileName,thumbnail,request);
                     status="1";
                 } catch (IllegalStateException e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                     status="2";
                 } catch (IOException e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                     status="2";
                 }
        		
               
            }
        	 
        }
        
        JSONObject json=new JSONObject();
        json.put("status", status);
        response.setContentType("application/json");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out= null;
        out = response.getWriter();
        out.print(json.toString());
        System.out.println(json.toString());
        out.flush();
        out.close();
		
      
       
    }
	
	/**
     * 多图上传（windows使用）
     * */
//    @RequestMapping("picturesUpload")
//     
//    public String picturesUpload(@RequestParam("fileName") MultipartFile[] fileArr,HttpServletRequest request,Thumbnail thumbnail){
//        
//        String result="";
//        for(int i=0;i<fileArr.length;i++) {
//        	if(!(fileArr[i].isEmpty())){
//        		 String fileName = fileArr[i].getOriginalFilename();
//                 int size = (int) fileArr[i].getSize();
//                 System.out.println(fileName + "-->" + size);
//                 String filePath = ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/img/";
//                 String path = "/img/"+fileName ;
//                 File dest = new File(filePath + "/" + fileName);
//                 String filepath=filePath + "/" + fileName;
//                 if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
//                     dest.getParentFile().mkdir();
//                 }
//                 try {
//               	  fileArr[i].transferTo(dest); //保存文件
//                     //保存文件进入数据库
//                     thumbnailService.insert(path,fileName,thumbnail,request);
//                     result="frame";
//                 } catch (IllegalStateException e) {
//                     // TODO Auto-generated catch block
//                     e.printStackTrace();
//                     result="false";
//                 } catch (IOException e) {
//                     // TODO Auto-generated catch block
//                     e.printStackTrace();
//                     result="false";
//                 }
//        		
//               
//            }
//        	 
//        }
//		return result;
//      
//       
//    }
    
    /**
     * 缩略图上传（centos使用）
     * */
//    @RequestMapping("picturesUpload")
     
//    public String picturesUpload(@RequestParam("fileName") MultipartFile[] fileArr,HttpServletRequest request,Thumbnail thumbnail){
//        if(fileArr[0].isEmpty()){
//            return "false";
//        }
//        String result="";
//        for(int i=0;i<fileArr.length;i++) {
//        	if(!(fileArr[i].isEmpty())){
//        		String fileName = fileArr[i].getOriginalFilename();
//                int size = (int) fileArr[i].getSize();
//                //System.out.println(fileName + "-->" + size);
//                String filePath = ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/img/";
//                //String path = "/img/"+fileName ;
//                fileName =generateRandomFilename() + fileName.substring(fileName.lastIndexOf("."));
//                String path = "/usr/local/kun_cms/thun";
//                File dest = new File(path + "/" + fileName);
//                //File dest = new File(filePath + "/" + fileName);
//                //String filepath=filePath + "/" + fileName;
//                if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
//                    dest.getParentFile().mkdir();
//                }
//                try {
//               	 fileArr[i].transferTo(dest); //保存文件
//                    //保存文件进入数据库
//                    thumbnailService.insert(path+"/"+fileName,fileName,thumbnail,request);
//                    result="frame";
//                } catch (IllegalStateException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                    result="false";
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                    result="false";
//                }
//        	}
//        	 
//        }
//		return result;
//       
//    }
    
    
	
	
	
	/**
	 * @param file
	 * @param request
	 * @param coverphoto
	 * @return
	 * centos系统封面上传
	 */
	@RequestMapping("fileUpload")
    
    public String fileUpload(@RequestParam("fileName") MultipartFile file,HttpServletRequest request,Coverphoto coverphoto){
        if(file.isEmpty()){
            return "false";
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);
        //String filePath = ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/img/";
        //String filePath = request.getSession().getServletContext().getRealPath("/usr/local/kun_cms/coverphoto");
        fileName =generateRandomFilename() + fileName.substring(fileName.lastIndexOf("."));
        String path = "/usr/local/kun_cms/coverphoto" ;
        File dest = new File(path + "/" + fileName);
        //String filepath=path + "/" + fileName;
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
            //保存文件进入数据库
           Play_num_record play_num_record=coverphotoService.queryCurrenPlayNum();//查询当前点播序列号
           //String serial_number=Common_util.getRandomNum4();
           	int now_num=play_num_record.getPlay_num_cureent()+1;
           	String serial_number="";
           	if(now_num<100){
           		serial_number="00"+now_num;
           	}else if(now_num>=100){
           		serial_number="0"+now_num;
           	}
            //coverphoto.setSerial_number(serial_number);
            //coverphotoService.upCurrenPlayNum(now_num);
            coverphotoService.insert(path+"/"+fileName,fileName,coverphoto, request);
            return "VideoManage";
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false";
        }
    }
	
	
	
   
    /**
     * 封面上传（windows系统）
     * */
//    @RequestMapping("fileUpload")
//     
//    public String fileUpload(@RequestParam("fileName") MultipartFile file,HttpServletRequest request,Coverphoto coverphoto){
//        if(file.isEmpty()){
//            return "false";
//        }
//        String fileName = file.getOriginalFilename();
//        int size = (int) file.getSize();
//        System.out.println(fileName + "-->" + size);
//        //String filePath = ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/img/";
//        String filePath = request.getSession().getServletContext().getRealPath("/coverphoto");
//        String path = "/coverphoto/"+fileName ;
//        File dest = new File(filePath + "/" + fileName);
//        String filepath=filePath + "/" + fileName;
//        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
//            dest.getParentFile().mkdir();
//        }
//        try {
//            file.transferTo(dest); //保存文件
//           //保存文件进入数据库
//            coverphotoService.insert(path,fileName,coverphoto, request);
//            return "VideoManage";
//        } catch (IllegalStateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return "false";
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return "false";
//        }
//   }
    
	/**
     * 缩略图上传（centos使用）
     * */
    @RequestMapping("thumbnailFileUpload")
     
    public String thumbnailFileUpload(@RequestParam("fileName") MultipartFile file,HttpServletRequest request,Thumbnail thumbnail){
        if(file.isEmpty()){
            return "false";
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);
        String filePath = ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/img/";
        //String path = "/img/"+fileName ;
        String path = "/usr/local/kun_cms/thun/"+fileName ;
        File dest = new File(path + "/" + fileName);
        //File dest = new File(filePath + "/" + fileName);
        String filepath=filePath + "/" + fileName;
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
            //保存文件进入数据库
            thumbnailService.insert(path+"/"+fileName,fileName,thumbnail,request);
            return "frame";
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false";
        }
    }
    
    
    /**
     * 缩略图上传（windows使用）
     * */
//   @RequestMapping("thumbnailFileUpload")
     
//    public String thumbnailFileUpload(@RequestParam("fileName") MultipartFile file,HttpServletRequest request,Thumbnail thumbnail){
//        if(file.isEmpty()){
//            return "false";
//        }
//        String fileName = file.getOriginalFilename();
//        int size = (int) file.getSize();
//        System.out.println(fileName + "-->" + size);
//        String filePath = ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/img/";
//        String path = "/img/"+fileName ;
//        File dest = new File(filePath + "/" + fileName);
//        String filepath=filePath + "/" + fileName;
//        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
//            dest.getParentFile().mkdir();
//        }
//        try {
//            file.transferTo(dest); //保存文件
//            //保存文件进入数据库
//            thumbnailService.insert(path,fileName,thumbnail);
//            return "frame";
//        } catch (IllegalStateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return "false";
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return "false";
//        }
//    }
    
  public static String generateRandomFilename(){  
        String fourRandom = "";
        //产生4位的随机数(不足4位前加零)
        int   randomNum =   (int)(Math.random()*10000);
        fourRandom = randomNum +"";
        int randLength =  fourRandom.length();
        if(randLength <4){
            for(int i=1; i <=4-randLength; i++)
                fourRandom = fourRandom + "0";
        } 
        StringBuilder sb = new StringBuilder("");
         Calendar cal=Calendar.getInstance();
         sb.append(cal.get(Calendar.YEAR))
        .append(twoNumbers(cal.get(Calendar.MONTH) + 1))
        .append(twoNumbers(cal.get(Calendar.DAY_OF_MONTH)))
        .append(twoNumbers(cal.get(Calendar.HOUR)))
        .append(twoNumbers(cal.get(Calendar.MINUTE)))
        .append(twoNumbers(cal.get(Calendar.SECOND)))
        .append(fourRandom);
        return sb.toString(); 
  } 
  private static String twoNumbers(int number){
    String _number = number + "";
    if(_number.length() < 2){
     _number = "0" + _number;
    }
    return _number;
  }

}