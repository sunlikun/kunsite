package com.kuncms.wxgzh.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.github.pagehelper.util.StringUtil;
import com.kuncms.coverphoto.model.Coverphoto;
import com.kuncms.coverphoto.service.CoverphotoService;
import com.kuncms.user.service.UserService;
import com.kuncms.util.Decript;
import com.kuncms.util.HttpClientUtils;
import com.kuncms.wxgzh.model.AccessToken;
import com.kuncms.wxgzh.model.Article;
import com.kuncms.wxgzh.model.CommonUtil;
import com.kuncms.wxgzh.model.MessageUtil;
import com.kuncms.wxgzh.model.NewsMessage;
import com.kuncms.wxgzh.model.TextMeaasge;
import com.kuncms.wxgzh.model.Video;
import com.kuncms.wxgzh.model.VideoMessage;
import com.kuncms.wxgzh.model.WeChatConfig;
import com.kuncms.wxgzh.model.WeiXinUserInfo;
import com.kuncms.wxgzh.model.WeixinUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Controller
@RequestMapping("/WechatController")
public class WechatController {
	
	@Autowired
	UserService userService;
	@Autowired
	CoverphotoService coverphotoService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private final String token = "1144954876";
	
	/**
	 * 上传其他永久素材(图片素材的上限为5000，其他类型为1000)
	 * @param appid
	 * @param secret
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/uploadPermanentMaterial")
	@ResponseBody
	public JSONObject addMaterialEver(File file,String type) throws Exception {
		try {
			System.out.println("开始上传"+type+"永久素材---------------------");	
			
			//开始获取证书
			String accessToken=CommonUtil.getToken(WeChatConfig.APP_ID,WeChatConfig.APP_SECRET).getAccessToken();			
			if(StringUtil.isEmpty(accessToken)){
				System.out.println("accessToken is null");	
				return null;
			}			
			
			//上传素材	
			String path="https://api.weixin.qq.com/cgi-bin/material/add_material?access_token="+accessToken+"&type="+type;
			String result=HttpClientUtils.connectHttpsByPost(path, null, file);
			result=result.replaceAll("[\\\\]", "");
			System.out.println("result:"+result);	
			JSONObject resultJSON=JSONObject.fromObject(result);
			if(resultJSON!=null){
				if(resultJSON.get("media_id")!=null){
					System.out.println("上传"+type+"永久素材成功");
					return resultJSON;
				}else{
					System.out.println("上传"+type+"永久素材失败");
				}
			}
			
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			System.out.println("结束上传"+type+"永久素材---------------------");	
		}
	}



	
	

	@RequestMapping("/serach")
	public void serach(HttpServletResponse response) throws IOException {
		Coverphoto coverphoto=new Coverphoto();
		String serial_number="";
		coverphoto.setSerial_number(serial_number);
		ArrayList<Coverphoto> list=coverphotoService.queryCovPhoBySer_num(coverphoto);
		
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
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * 微信消息回复
	 */
	public void postMsg(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Map<String, String> map = MessageUtil.xmlToMap(request);
            String toUserName = map.get("ToUserName");
            String fromUserName = map.get("FromUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");
            
            TextMeaasge text = new TextMeaasge();
            // 发送和回复是反向的
            text.setToUserName(fromUserName);
            text.setFromUserName(toUserName);
            text.setCreateTime(new Date().getTime());
            text.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_TEXT);
//            WeiXinUserInfo weiXinUserInfo = WeixinUtil.getUserInfo(WeixinUtil.getAccessToken(WeChatConfig.APP_ID, WeChatConfig.APP_SECRET).getAccessToken(),fromUserName);
            text.setContent("没找到您需要的信息！！！请重新输入！！！");
            String respMessage = MessageUtil.messageToXml(text);
           
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {  
                // 事件类型  
                String eventType = map.get("Event"); 
                
                // 订阅  
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {  
                	text.setContent("感谢关注！相关视频编号请至官网查看，或关注快手(用户名为摄影诗)，斗鱼tv(用户名新火车)查看，输入编号可在线观看精彩视频！");
                }  
                if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {  
                    // 事件KEY值，与创建自定义菜单时指定的KEY值对应  
                    String eventKey = map.get("EventKey");  
                    if (eventKey.equals("11")) {  
                    	 text.setContent("请输入视频编号！相关视频编号请至官网查看，或关注快手(用户名为摄影诗)，斗鱼tv(用户名新火车)查看");
                    }
                    
                    respMessage = MessageUtil.messageToXml(text);
                  }
            }
          
            
            // 文本消息
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                 
                // 创建图文消息  
//                NewsMessage newsMessage = new NewsMessage();
//                newsMessage.setToUserName(fromUserName);
//                newsMessage.setFromUserName(toUserName);
//                newsMessage.setCreateTime(new Date().getTime());
//                newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
//                
//                List<Article> articleList = new ArrayList<Article>();
            	Coverphoto coverphoto=new Coverphoto();
        		String serial_number=content;
        		coverphoto.setSerial_number(serial_number);
        		ArrayList<Coverphoto> list=coverphotoService.queryCovPhoBySer_num(coverphoto);
	            // 指定消息回复  
                //if ("1".equals(content)) {
                    //text.setContent("今天的天气真不错！");
                    //respMessage = MessageUtil.messageToXml(text);
        		if(list.size()>0) {
        			Coverphoto c=list.get(0);
        			VideoMessage  video=new VideoMessage();
                    video.setToUserName(fromUserName);
	              	video.setFromUserName(toUserName);
	              	video.setCreateTime(new Date().getTime());
	              	video.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_VIDEO);
	              	Video vi=new Video();
	              	vi.setMediaId(c.getMediaId());
	              	vi.setTitle(c.getVideo_name());
	              	vi.setDescription(c.getSerial_number());
	              	video.setVideo(vi);
	              
	              	respMessage = MessageUtil.messageToXml(video);
        		}else {
        			text.setContent("内容编号有误，没有此资源！");
        			respMessage = MessageUtil.messageToXml(text);
        		}
                	
                //}
                //单图文消息
//                else if("2".equals(content)){
//                    Article article = new Article();
//                    article.setTitle("微信公众帐号开发教程Java版");
//                    article.setDescription("第一张图片");
//                    article.setPicUrl("http://pic.qiantucdn.com/58pic/26/10/40/58c04e46c2ffa_1024.jpg!/fw/780/watermark/url/L3dhdGVybWFyay12MS4zLnBuZw==/align/center");
//                    article.setUrl("http://www.cnblogs.com/x-99/");
//                    articleList.add(article);
//                    // 设置图文消息个数 
//                    newsMessage.setArticleCount(articleList.size());
//                    // 设置图文消息包含的图文集合 
//                    newsMessage.setArticles(articleList);
//                    // 将图文消息对象转换成xml字符串 
//                    respMessage = MessageUtil.messageToXml(newsMessage);
//                }
               // 多图文消息
//                else if("3".equals(content)){
//                    Article article1 = new Article();
//                    article1.setTitle("微信公众帐号开发教程Java版");
//                    article1.setDescription("");
//                    article1.setPicUrl("http://pic.qiantucdn.com/58pic/26/10/40/58c04e46c2ffa_1024.jpg!/fw/780/watermark/url/L3dhdGVybWFyay12MS4zLnBuZw==/align/center");
//                    article1.setUrl("http://www.cnblogs.com/x-99/");
//                    Article article2 = new Article();
//                    article2.setTitle("微信公众帐号开发教程.NET版");
//                    article2.setDescription("");
//                    article2.setPicUrl("http://pic.qiantucdn.com/58pic/26/10/40/58c04e46c2ffa_1024.jpg!/fw/780/watermark/url/L3dhdGVybWFyay12MS4zLnBuZw==/align/center");
//                    article2.setUrl("http://www.cnblogs.com/x-99/");
//                    Article article3 = new Article();
//                    article3.setTitle("微信公众帐号开发教程C版");
//                    article3.setDescription("");
//                    article3.setPicUrl("http://pic.qiantucdn.com/58pic/26/10/40/58c04e46c2ffa_1024.jpg!/fw/780/watermark/url/L3dhdGVybWFyay12MS4zLnBuZw==/align/center");
//                    article3.setUrl("http://www.cnblogs.com/x-99/");
//                    
//                    articleList.add(article1);
//                    articleList.add(article2);
//                    articleList.add(article3);
//                    // 设置图文消息个数 
//                    newsMessage.setArticleCount(articleList.size());
//                    // 设置图文消息包含的图文集合 
//                    newsMessage.setArticles(articleList);
//                    // 将图文消息对象转换成xml字符串 
//                    respMessage = MessageUtil.messageToXml(newsMessage);
//                }
//                
            }
            
            System.out.println("消息回复 "+respMessage);
            response.setContentType("application/json");
	        response.setHeader("Pragma", "No-cache");
	        response.setHeader("Cache-Control", "no-cache");
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write(respMessage);// 将回应发送给微信服务器  
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
	
	
	
	/**
	 * 微信公众号接入验证
	 * @param map
	 * @return
	 */
	@RequestMapping("/auth_wechat")
	protected void auth_wechat(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		boolean isGet = request.getMethod().toLowerCase().equals("get");
		
		if (isGet) {
	            System.out.println("开始签名校验");
	    	    String signature = request.getParameter("signature");
	    	    String timestamp = request.getParameter("timestamp");
	    	    String nonce = request.getParameter("nonce");
	    	    String echostr = request.getParameter("echostr");
	    	 
	    	    ArrayList<String> array = new ArrayList<String>();
	    	    array.add(signature);
	    	    array.add(timestamp);
	    	    array.add(nonce);
	    	 
	    	    //排序
	    	    String sortString = sort(token, timestamp, nonce);
	    	    //加密
	    	    String mytoken = Decript.SHA1(sortString);
	    	    //校验签名
	    	    if (mytoken != null && mytoken != "" && mytoken.equals(signature)) {
	    	        System.out.println("签名校验通过。");
	    	        response.getWriter().println(echostr); //如果检验成功输出echostr，微信服务器接收到此输出，才会确认检验完成。
	    	    } else {
	    	        System.out.println("签名校验失败。");
	    	    }

	        }else{
	            try {  
	                
	                // 接收消息并返回消息  
	                postMsg(request, response);
	                
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	    }
		
	}
	 
	 
	 
	/**
	 * 排序方法
	 * @param token
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static String sort(String token, String timestamp, String nonce) {
	    String[] strArray = { token, timestamp, nonce };
	    Arrays.sort(strArray);
	 
	    StringBuilder sbuilder = new StringBuilder();
	    for (String str : strArray) {
	        sbuilder.append(str);
	    }
	 
	    return sbuilder.toString();
	}
	
}
