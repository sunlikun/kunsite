package com.kuncms.util;

import java.io.File;
import java.io.IOException;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kuncms.coverphoto.dao.CoverphotoDao;
import com.kuncms.coverphoto.model.Coverphoto;
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
    @RequestMapping("picturesUpload")
     
    public String picturesUpload(@RequestParam("fileName") MultipartFile[] fileArr,HttpServletRequest request,Thumbnail thumbnail){
        if(fileArr[0].isEmpty()){
            return "false";
        }
        String result="";
        for(int i=0;i<fileArr.length;i++) {
        	if(!(fileArr[i].isEmpty())){
        		String fileName = fileArr[i].getOriginalFilename();
                int size = (int) fileArr[i].getSize();
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
               	 fileArr[i].transferTo(dest); //保存文件
                    //保存文件进入数据库
                    thumbnailService.insert(path+"/"+fileName,fileName,thumbnail,request);
                    result="frame";
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    result="false";
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    result="false";
                }
        	}
        	 
        }
		return result;
       
    }
    
    
	
	
	
	/**
	 * @param file
	 * @param request
	 * @param coverphoto
	 * @return
	 * centos系统下使用的图片上传
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
        String path = "/usr/local/kun_cms/coverphoto/"+fileName ;
        File dest = new File(path + "/" + fileName);
        String filepath=path + "/" + fileName;
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
            //保存文件进入数据库
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
    
   

}