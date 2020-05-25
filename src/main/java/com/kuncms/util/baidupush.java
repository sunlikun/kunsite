package com.kuncms.util;  
   
import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.io.PrintWriter;  
import java.net.URL;  
import java.net.URLConnection;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kuncms.coverphoto.model.Coverphoto;
import com.kuncms.coverphoto.service.CoverphotoService;  


@Controller
public class baidupush {  
	@Autowired
	
	CoverphotoService coverphotoService;
    
	@RequestMapping("/baidupush") 
    public void push() {  
    	
    	
        String url = "http://data.zz.baidu.com/urls?site=www.pergirls.com&token=PifHkrXOMWa3CUGB";//网站的服务器连接  
       
        //查询出所有要推送的资源的链接
    	Coverphoto coverphoto=new Coverphoto();
    	ArrayList<Coverphoto> list=coverphotoService.queryCoverPhoto(coverphoto);
    	String[] param = new String[list.size()];
    	for(int i=0;i<list.size();i++){
    		param[i]="http://www.pergirls.com/details?id="+list.get(i).getId()+"&baiduyun_pass="+list.get(i).getBaiduyun_pass()+
    				"&t_gold_coin="+list.get(i).getGold_coin();
    		System.out.println("http://www.pergirls.com/details?id="+list.get(i).getId()+"&baiduyun_pass="+list.get(i).getBaiduyun_pass()+
    				"&t_gold_coin="+list.get(i).getGold_coin());
    	 }
        String json = Post(url, param);//执行推送方法  
        System.out.println("结果是"+json);  //打印推送结果  
   
    }  
    
   
       
    /** 
     * 百度链接实时推送 
     * @param PostUrl 
     * @param Parameters 
     * @return 
     */  
    public static String Post(String PostUrl,String[] Parameters){  
        if(null == PostUrl || null == Parameters || Parameters.length ==0){  
            return null;  
        }  
        String result="";  
        PrintWriter out=null;  
        BufferedReader in=null;  
        try {  
            //建立URL之间的连接  
            URLConnection conn=new URL(PostUrl).openConnection();  
            //设置通用的请求属性  
            conn.setRequestProperty("Host","data.zz.baidu.com");  
            conn.setRequestProperty("User-Agent", "curl/7.12.1");  
            conn.setRequestProperty("Content-Length", "83");  
            conn.setRequestProperty("Content-Type", "text/plain");  
                
            //发送POST请求必须设置如下两行  
            conn.setDoInput(true);  
            conn.setDoOutput(true);  
                
            //获取conn对应的输出流  
            out=new PrintWriter(conn.getOutputStream());  
            //发送请求参数  
            String param = "";  
            for(String s : Parameters){  
                param += s+"\n";  
            }  
            out.print(param.trim());  
            //进行输出流的缓冲  
            out.flush();  
            //通过BufferedReader输入流来读取Url的响应  
            in=new BufferedReader(new InputStreamReader(conn.getInputStream()));  
            String line;  
            while((line=in.readLine())!= null){  
                result += line;  
            }  
                
        } catch (Exception e) {  
            System.out.println("发送post请求出现异常！"+e);  
            e.printStackTrace();  
        } finally{  
            try{  
                if(out != null){  
                    out.close();  
                }  
                if(in!= null){  
                    in.close();  
                }  
                    
            }catch(IOException ex){  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }  
   
}