package com.kuncms.wxgzh.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.kuncms.coverphoto.model.Coverphoto;
import com.kuncms.coverphoto.service.CoverphotoService;
import com.kuncms.user.model.User;
import com.kuncms.user.service.UserService;
import com.kuncms.util.Decript;
import com.kuncms.util.HttpClientUtils;
import com.kuncms.wxgzh.model.AccessToken;
import com.kuncms.wxgzh.model.AdvancedUtil;
import com.kuncms.wxgzh.model.Article;
import com.kuncms.wxgzh.model.CommonUtil;
import com.kuncms.wxgzh.model.CustomerMessage;
import com.kuncms.wxgzh.model.MessageUtil;
import com.kuncms.wxgzh.model.NewsMessage;
import com.kuncms.wxgzh.model.SNSUserInfo;
import com.kuncms.wxgzh.model.TextMeaasge;
import com.kuncms.wxgzh.model.Video;
import com.kuncms.wxgzh.model.VideoMessage;
import com.kuncms.wxgzh.model.WeChatConfig;
import com.kuncms.wxgzh.model.WeiXinUserInfo;
import com.kuncms.wxgzh.model.WeixinOauth2Token;
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
	 * @param response
	 * @param currentPage
	 * @param pageSize
	 * @throws IOException
	 * 点播信息获取页信息查询
	 */
	@RequestMapping("/queryVipPlayNumInfo")
	
	public void queryPlayNumInfo(HttpServletResponse response,int currentPage, int pageSize,Coverphoto coverphoto) throws IOException {
		//System.out.println(currentPage+"   "+pageSize);
			PageHelper.startPage(currentPage , pageSize);
		 	ArrayList<Coverphoto> list=coverphotoService.queryVipPlayNumInfo(coverphoto);
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
	 * @param map
	 * @param request
	 * @return
	 * 跳转到vip点播码查看页（微信使用）
	 */
	@RequestMapping("/toVipPlayNumInfo")
    public String toPlayNumInfo(Map<String,Object> map,HttpServletRequest request){
   
    
		return "VipPlay";
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
	@RequestMapping("/wechat_details")
    public String details(Map<String,Object> map,String id,String baiduyun_pass,String baiduyun_address,String t_gold_coin,Model model,HttpServletRequest request){
	  
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
			model.addAttribute("user_id",user_id);
			model.addAttribute("user_name",user_name);
			model.addAttribute("gold_coin",gold_coin);
		}
		
		
		System.out.println("t_gold_coin"+t_gold_coin);
		model.addAttribute("baiduyun_pass",baiduyun_pass);
		
		model.addAttribute("baiduyun_address",baiduyun_address);
       return "wechat_details";
    }
	
	/**
	 * 公众号跳转到网站首页
	 * @param map
	 * @param request
	 * @param model
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request,Model model) throws JSONException, IOException{

	        // 用户同意授权后，能获取到code
	        String code = request.getParameter("code");
	        String state = request.getParameter("state");
	        
	    
	        WeixinOauth2Token wat = WechatController.getOauth2AccessToken(WeChatConfig.APP_ID, WeChatConfig.APP_SECRET, code);
		 	
		  	
		  	SNSUserInfo snsUserInfo = null;
	        // 拼接请求地址
	        String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
	        requestUrl = requestUrl.replace("ACCESS_TOKEN", wat.getAccessToken()).replace("OPENID", wat.getOpenId());
	        // 通过网页授权获取用户信息
	        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
	        if (null != jsonObject) {
	            try {
	                snsUserInfo = new SNSUserInfo();
	                // 用户的标识
	                snsUserInfo.setOpenId(jsonObject.getString("openid"));
	                // 昵称
	                snsUserInfo.setNickname(jsonObject.getString("nickname"));
	                // 性别（1是男性，2是女性，0是未知）
	                snsUserInfo.setSex(jsonObject.getInt("sex"));
	                // 用户所在国家
	                snsUserInfo.setCountry(jsonObject.getString("country"));
	                // 用户所在省份
	                snsUserInfo.setProvince(jsonObject.getString("province"));
	                // 用户所在城市
	                snsUserInfo.setCity(jsonObject.getString("city"));
	                // 用户头像
	                snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
	                // 用户特权信息
	                snsUserInfo.setPrivilegeList(JSONArray.toList(jsonObject.getJSONArray("privilege"), List.class));
	                User user1=new User();
	                //user1.setOpenid(jsonObject.getString("openid"));
	                user1.setUnionid(jsonObject.getString("unionid"));
	                ArrayList<User> user=userService.check_username(user1);
	                if(user.size()>0) {
	                	model.addAttribute("id",user.get(0).getId());
	                	model.addAttribute("user_name",user.get(0).getUser_name());
	                	HttpSession session = request.getSession();
	 	    	        session.setAttribute("loginName",user.get(0).getUser_name());
	 	    	        session.setAttribute("gold_coin",user.get(0).getGold_coin());
	 	    	        session.setAttribute("user",user.get(0));
	                }
	               
	            	
	            } catch (Exception e) {
	                snsUserInfo = null;
	                int errorCode = jsonObject.getInt("errcode");
	                String errorMsg = jsonObject.getString("errmsg");
	                System.out.println("获取用户信息失败 errcode:{} errmsg:{}"+errorCode+errorMsg);
	            }
	        }
	        return "wecaht_index";

      }
	
	
	@RequestMapping(value = "/index_auth")
   	@ResponseBody
   	public void index_auth(HttpServletRequest request,HttpServletResponse response) throws Exception {
    	 // 设置编码
    	request.setCharacterEncoding("utf-8");
    	response.setContentType("text/html;charset=utf-8");
    	response.setCharacterEncoding("utf-8");
        /**
         * 第一步：用户同意授权，获取code:https://open.weixin.qq.com/connect/oauth2/authorize
         * ?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE
         * &state=STATE#wechat_redirect
         */
        String redirect_uri = URLEncoder.encode(
                "http://www.pergirls.com/WechatController/index", "UTF-8");// 授权后重定向的回调链接地址，请使用urlencode对链接进行处理（文档要求）
        // 按照文档要求拼接访问地址
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
                + WeChatConfig.APP_ID
                + "&redirect_uri="
                + redirect_uri
                + "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
        response.sendRedirect(url);// 跳转到要访问的地址
        
    }
	
	
	/**
	 * 跳转到充值页面
	 * @param map
	 * @return
	 */
	@RequestMapping("/we_recharge")
    public String we_recharge(Map<String,Object> map,Model model,HttpServletRequest request){
	   //ArrayList<Coverphoto> list=thumbnailService.queryCoverPhoto();
	   //com.alibaba.fastjson.JSONArray array= com.alibaba.fastjson.JSONArray.parseArray(JSON.toJSONString(list));
	   //model.addAttribute("data", array.toJSONString());
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("user");
//		String currTime = PayToolUtil.getCurrTime();
//		String strRandom = String.valueOf(PayToolUtil.buildRandom(4));
//		String strTime = currTime.substring(8, currTime.length());
//	    String nonce_str = strTime + strRandom;
	    
//		model.addAttribute("user_name",user.getUser_name());
//		model.addAttribute("id",user.getId());
//		model.addAttribute("appId",APPID);
//		model.addAttribute("timestamp",PayToolUtil.getCurrTime());
//		model.addAttribute("nonceStr",nonce_str);
//		model.addAttribute("signature",signature);
		
       return "we_recharge";
    }
	
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request,Model model) throws JSONException, IOException{

	        // 用户同意授权后，能获取到code
	        String code = request.getParameter("code");
	        String state = request.getParameter("state");
	        
	        // 用户同意授权
//	        if (!"authdeny".equals(code)) {
//	            // 获取网页授权access_token
//	            WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken(WeChatConfig.APP_ID,WeChatConfig.APP_SECRET, code);
//	            // 网页授权接口访问凭证
//	            String accessToken = weixinOauth2Token.getAccessToken();
//	            // 用户标识
//	            String openId = weixinOauth2Token.getOpenId();
//	            // 获取用户信息
//	            SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);
//	
//	            // 设置要传递的参数
//	            request.setAttribute("snsUserInfo", snsUserInfo);
//	            request.setAttribute("state", state);
//	        }
		  	
	        WeixinOauth2Token wat = WechatController.getOauth2AccessToken(WeChatConfig.APP_ID, WeChatConfig.APP_SECRET, code);
		 	
		  	
		  	SNSUserInfo snsUserInfo = null;
	        // 拼接请求地址
	        String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
	        requestUrl = requestUrl.replace("ACCESS_TOKEN", wat.getAccessToken()).replace("OPENID", wat.getOpenId());
	        // 通过网页授权获取用户信息
	        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
	        if (null != jsonObject) {
	            try {
	                snsUserInfo = new SNSUserInfo();
	                // 用户的标识
	                snsUserInfo.setOpenId(jsonObject.getString("openid"));
	                // 昵称
	                snsUserInfo.setNickname(jsonObject.getString("nickname"));
	                // 性别（1是男性，2是女性，0是未知）
	                snsUserInfo.setSex(jsonObject.getInt("sex"));
	                // 用户所在国家
	                snsUserInfo.setCountry(jsonObject.getString("country"));
	                // 用户所在省份
	                snsUserInfo.setProvince(jsonObject.getString("province"));
	                // 用户所在城市
	                snsUserInfo.setCity(jsonObject.getString("city"));
	                // 用户头像
	                snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
	                // 用户特权信息
	                snsUserInfo.setPrivilegeList(JSONArray.toList(jsonObject.getJSONArray("privilege"), List.class));
	                User user1=new User();
	                //user1.setOpenid(jsonObject.getString("openid"));
	                user1.setUnionid(jsonObject.getString("unionid"));
	                ArrayList<User> user=userService.check_username(user1);
	                if(user.size()>0) {
	                	model.addAttribute("id",user.get(0).getId());
	                	model.addAttribute("user_name",user.get(0).getUser_name());
	                	HttpSession session = request.getSession();
	 	    	        session.setAttribute("loginName",user.get(0).getUser_name());
	 	    	        session.setAttribute("gold_coin",user.get(0).getGold_coin());
	 	    	        session.setAttribute("user",user.get(0));
	                }
	               
	            	
	            } catch (Exception e) {
	                snsUserInfo = null;
	                int errorCode = jsonObject.getInt("errcode");
	                String errorMsg = jsonObject.getString("errmsg");
	                System.out.println("获取用户信息失败 errcode:{} errmsg:{}"+errorCode+errorMsg);
	            }
	        }
	        return "wechat_membership";

      }
	
	/**
     * 获取网页授权凭证
     * 
     * @param appId 公众账号的唯一标识
     * @param appSecret 公众账号的密钥
     * @param code
     * @return WeixinAouth2Token
     */
    public static WeixinOauth2Token getOauth2AccessToken(String appId, String appSecret, String code) {
        WeixinOauth2Token wat = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        requestUrl = requestUrl.replace("CODE", code);
        // 获取网页授权凭证
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                wat = new WeixinOauth2Token();
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getInt("expires_in"));
                wat.setRefreshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));
            } catch (Exception e) {
                wat = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                System.out.println("获取网页授权凭证失败 errcode:{} errmsg:{}"+errorCode+errorMsg);
               
            }
        }
        return wat;
    }
	
	
	@RequestMapping(value = "/auth")
   	@ResponseBody
   	public void GuideServlet(HttpServletRequest request,HttpServletResponse response) throws Exception {
    	 // 设置编码
    	request.setCharacterEncoding("utf-8");
    	response.setContentType("text/html;charset=utf-8");
    	response.setCharacterEncoding("utf-8");
        /**
         * 第一步：用户同意授权，获取code:https://open.weixin.qq.com/connect/oauth2/authorize
         * ?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE
         * &state=STATE#wechat_redirect
         */
        String redirect_uri = URLEncoder.encode(
                "http://www.pergirls.com/WechatController/login", "UTF-8");// 授权后重定向的回调链接地址，请使用urlencode对链接进行处理（文档要求）
        // 按照文档要求拼接访问地址
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
                + WeChatConfig.APP_ID
                + "&redirect_uri="
                + redirect_uri
                + "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
        response.sendRedirect(url);// 跳转到要访问的地址
        
    }

	
	
	
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
         //   boolean orderFlag=false;
            
            TextMeaasge text = new TextMeaasge();
            // 发送和回复是反向的
            text.setToUserName(fromUserName);
            text.setFromUserName(toUserName);
            text.setCreateTime(new Date().getTime());
            text.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_TEXT);
            WeiXinUserInfo weiXinUserInfo = WeixinUtil.getUserInfo(WeixinUtil.getAccessToken(WeChatConfig.APP_ID, WeChatConfig.APP_SECRET).getAccessToken(),fromUserName);
            text.setContent("收到了您的留言，客服会在24小时内进行处理");
            String respMessage = MessageUtil.messageToXml(text);
           // String respMessage1 = "";
           
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {  
                // 事件类型  
                String eventType = map.get("Event"); 
                
                // 订阅  
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {  
                	text.setContent("欢迎关注pergirls!如遇问题可在公众号内留言,客服将在24小时内解答。您也可以复制网址www.pergirls.com至手机及电脑浏览器进行相关浏览。");
//                	orderFlag=true;
//                	Coverphoto coverphoto=new Coverphoto();
//            		String serial_number=content;
//            		coverphoto.setSerial_number("0011");
//            		ArrayList<Coverphoto> list=coverphotoService.queryCovPhoBySer_num(coverphoto);
//            		Coverphoto c=list.get(0);
//        			VideoMessage  video=new VideoMessage();
//                    video.setToUserName(fromUserName);
//	              	video.setFromUserName(toUserName);
//	              	video.setCreateTime(new Date().getTime());
//	              	video.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_VIDEO);
//	              	Video vi=new Video();
//	              	vi.setMediaId(c.getMediaId());
//	              	vi.setTitle(c.getVideo_name());
//	              	vi.setDescription(c.getSerial_number());
//	              	video.setVideo(vi);
//	                respMessage1 = MessageUtil.messageToXml(video);
                	//用户完成订阅后自动在普格娱乐官网进行注册
                	User user=new User();
    		        user.setOpenid(weiXinUserInfo.getOpenid());
    		        user.setIs_wechat("1");
    		        user.setHeadimgurl(weiXinUserInfo.getHeadimgurl());
    		        user.setUser_name(weiXinUserInfo.getNickname());
    		        user.setUnionid(weiXinUserInfo.getUnionid());
    		        user.setOfficial_account("1");
    		        
    		        System.out.println(weiXinUserInfo.getUnionid()+"=======");
    		        ArrayList<User> userlis=userService.isRegister(user);
    		        if(userlis.size()>0){//已经注册
    		        	 user.setOfficial_account("1");
    	    		     userService.update_offi_acc(user);
    		        }else{//尚未注册
    		        	userService.newsignup(user);
    		        }
    		        respMessage = MessageUtil.messageToXml(text);
                }
                
                // 取消订阅  
                if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {  
                	text.setContent("再见!");
                	//用户完成订阅后自动在普格娱乐官网进行注册
                	User user=new User();
    		        user.setOpenid(weiXinUserInfo.getOpenid());
    		        user.setOfficial_account("0");
    		        user.setUnionid(weiXinUserInfo.getUnionid());
    		        userService.update_offi_acc(user);
    		        
    		        respMessage = MessageUtil.messageToXml(text);
                }
                
                
                if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {  
                    // 事件KEY值，与创建自定义菜单时指定的KEY值对应  
                    String eventKey = map.get("EventKey");  
                    if (eventKey.equals("11")) {  
                    	 text.setContent("请点击“获取点播号”进入官网查看视频点播号，返回在公众号输入后即可播放");
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
        		 if (serial_number.equals("客服")) {  
                     // 事件KEY值，与创建自定义菜单时指定的KEY值对应  
        			 System.out.println("转发");
                     	String openid=map.get("FromUserName"); //用户openid
                     	String mpid=map.get("ToUserName"); //公众号原始ID
                 	    CustomerMessage customer=new CustomerMessage();
                 	    customer.setToUserName(fromUserName);
                 	    customer.setFromUserName(toUserName);
                 	    customer.setCreateTime(new Date().getTime());
                 	    customer.setMsgType("transfer_customer_service");
                 	    
                 	    
                  }else if(list.size()>0) {
        			Coverphoto c=list.get(0);
        			//判断该视频是金币点播还是普通点播
        			String isvip=c.getIsvip();
        			if (!"".equals(isvip) && isvip != null) {
        				if(isvip.equals("1")){//是vip点播
            				//查询点播所需金币
            				String play_goin=c.getPlay_goin();
            				//查询当前用户所需的金币数
            				User user=new User();
            				user.setUnionid(weiXinUserInfo.getUnionid());
            				ArrayList<User> userlis=userService.isRegister(user);
              		        if(userlis.size()>0){//已经注册
              		        	User existUser=userlis.get(0);
              		        	int gold_coin=existUser.getGold_coin();
              		        	System.out.println("vip点播"+gold_coin+" Integer.parseInt(play_goin) "+Integer.parseInt(play_goin));
              		        	if(gold_coin>=Integer.parseInt(play_goin)){//金币充足可以点播
              		        		//existUser.setGold_coin(gold_coin-Integer.parseInt(play_goin));
              		        		int goin= gold_coin-Integer.parseInt(play_goin);
              		        		userService.addUserGoldCoin(existUser.getId(),goin);
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
              		        	}else{//金币不足无法点播
              		        		String openid=map.get("FromUserName"); //用户openid
              	                 	String mpid=map.get("ToUserName"); //公众号原始ID
              	             	    CustomerMessage customer=new CustomerMessage();
              	             	    customer.setToUserName(fromUserName);
              	             	    customer.setFromUserName(toUserName);
              	             	    customer.setCreateTime(new Date().getTime());
              	             	    customer.setMsgType("transfer_customer_service");
              	        			text.setContent("点播此视频需要"+Integer.parseInt(play_goin)+"金币抱歉您的金币数不足，请至我的账户中进行金币充值后操作");
              	        			respMessage = MessageUtil.messageToXml(text);
              		        	}
              		        	

              		        }else{//尚未注册
              		        	String openid=map.get("FromUserName"); //用户openid
          	                 	String mpid=map.get("ToUserName"); //公众号原始ID
          	             	    CustomerMessage customer=new CustomerMessage();
          	             	    customer.setToUserName(fromUserName);
          	             	    customer.setFromUserName(toUserName);
          	             	    customer.setCreateTime(new Date().getTime());
          	             	    customer.setMsgType("transfer_customer_service");
          	        			text.setContent("抱歉，你的账号可能有问题，暂不能进行点播");
          	        			respMessage = MessageUtil.messageToXml(text);
              		        } 
            				
            			}else{//不是vip点播
            				System.out.println("非vip点播");
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
            			}
        			}else{
        				System.out.println("非vip点播");
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
        			}
        			
        			
        		}else {
        			System.out.println("转发");
                 	String openid=map.get("FromUserName"); //用户openid
                 	String mpid=map.get("ToUserName"); //公众号原始ID
             	    CustomerMessage customer=new CustomerMessage();
             	    customer.setToUserName(fromUserName);
             	    customer.setFromUserName(toUserName);
             	    customer.setCreateTime(new Date().getTime());
             	    customer.setMsgType("transfer_customer_service");
        			text.setContent("已经收到您的反馈信息,客服将在24小时内对您的发送的信息进行处理并给您回复,如果您想点播视频，请输入正确的点播编号");
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
	        //response.getWriter().write(respMessage1);// 将回应发送给微信服务器  
            
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
