package com.kuncms.wxgzh.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONException;

import net.sf.json.JSONObject;




/**
 * 公众平台通用接口工具类
 * @author xzl
 *
 */
public class WeixinUtil {
    
    private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);
    // 菜单创建（POST） 限100（次/天）  
    public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN"; 
    
    /** 
     * 创建菜单 
     *  
     * @param menu 菜单实例 
     * @param accessToken 有效的access_token 
     * @return 0表示成功，其他值表示失败 
     */  
    public static int createMenu(Menu menu, String accessToken) {  
        int result = 0;  
      
        // 拼装创建菜单的url  
        String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);  
        // 将菜单对象转换成json字符串  
        String jsonMenu = JSONObject.fromObject(menu).toString();  
        // 调用接口创建菜单  
        JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);  
      
        if (null != jsonObject) {  
            if (0 != jsonObject.getInt("errcode")) {  
                result = jsonObject.getInt("errcode");  
                log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
            }  
        }  
      
        return result;  
    }  
     
    
    /**
     * 获取access_token
     * 
     * @param appid 凭证
     * @param appsecret 密钥
     * @return
     */
//    public static AccessToken getAccessToken(String appid, String appsecret) {
//
//    // 获取access_token的接口地址（GET） 限200（次/天）
//        final String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
//
//        AccessToken accessToken = null;
//
//        String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
//        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
//        // 如果请求成功
//        if (null != jsonObject) {
//            try {
//                accessToken = new AccessToken();
//                accessToken.setAccessToken(jsonObject.getString("access_token"));
//                accessToken.setExpiresin(jsonObject.getInt("expires_in"));
//            } catch (JSONException e) {
//                accessToken = null;
//                // 获取token失败
//                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
//            }
//        }
//        return accessToken;
//    }
    
    /**
     * URL编码（utf-8）
     * 
     * @param source
     * @return
     */
    public static String urlEncodeUTF8(String source) {
      String result = source;
      try {
        result = java.net.URLEncoder.encode(source, "utf-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
      return result;
    }
    
    /**
     * 根据内容类型判断文件扩展名
     * 
     * @param contentType 内容类型
     * @return
     */
    public static String getFileExt(String contentType) {
      String fileExt = "";
      if ("image/jpeg".equals(contentType))
        fileExt = ".jpg";
      else if ("audio/mpeg".equals(contentType))
        fileExt = ".mp3";
      else if ("audio/amr".equals(contentType))
        fileExt = ".amr";
      else if ("video/mp4".equals(contentType))
        fileExt = ".mp4";
      else if ("video/mpeg4".equals(contentType))
        fileExt = ".mp4";
      return fileExt;
    }
    

    /**
     * 获取用户信息
     * 
     * @param accessToken 接口访问凭证
     * @param openId 用户标识
     * @return WeixinUserInfo
     */
//    public static WeiXinUserInfo getUserInfo(String accessToken, String openId) {
//        WeiXinUserInfo weixinUserInfo = null;
//      // 拼接请求地址
//      String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
//      requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
//      // 获取用户信息
//      JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);
//      if (null != jsonObject) {
//        try {
//          weixinUserInfo = new WeiXinUserInfo();
//          // 用户的标识
//          weixinUserInfo.setOpenid(jsonObject.getString("openid"));
//          // 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
//          weixinUserInfo.setSubscribe(jsonObject.getInt("subscribe"));
//          // 用户关注时间
//          weixinUserInfo.setSubscribe_time(jsonObject.getString("subscribe_time"));
//          // 昵称
//          weixinUserInfo.setNickname(jsonObject.getString("nickname"));
//          // 用户的性别（1是男性，2是女性，0是未知）
//          weixinUserInfo.setSex(jsonObject.getInt("sex"));
//          // 用户所在国家
//          weixinUserInfo.setCountry(jsonObject.getString("country"));
//          // 用户所在省份
//          weixinUserInfo.setProvince(jsonObject.getString("province"));
//          // 用户所在城市
//          weixinUserInfo.setCity(jsonObject.getString("city"));
//          // 用户的语言，简体中文为zh_CN
//          weixinUserInfo.setLanguage(jsonObject.getString("language"));
//          // 用户头像
//          weixinUserInfo.setHeadimgurl(jsonObject.getString("headimgurl"));
//        } catch (Exception e) {
//          if (0 == weixinUserInfo.getSubscribe()) {
//            log.error("用户{}已取消关注", weixinUserInfo.getOpenid());
//          } else {
//            int errorCode = jsonObject.getInt("errcode");
//            String errorMsg = jsonObject.getString("errmsg");
//            log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
//          }
//        }
//      }
//      return weixinUserInfo;
//    }
    
    /** 
     * 发起https请求并获取结果 
     *  
     * @param requestUrl 请求地址 
     * @param requestMethod 请求方式（GET、POST） 
     * @param outputStr 提交的数据 
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值) 
     */  
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {  
        JSONObject jsonObject = null;  
        StringBuffer buffer = new StringBuffer();  
        try {  
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化  
            TrustManager[] tm = { new MyX509TrustManager() };  
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
            sslContext.init(null, tm, new java.security.SecureRandom());  
            // 从上述SSLContext对象中得到SSLSocketFactory对象  
            SSLSocketFactory ssf = sslContext.getSocketFactory();  
  
            URL url = new URL(requestUrl);  
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  
            httpUrlConn.setSSLSocketFactory(ssf);  
  
            httpUrlConn.setDoOutput(true);  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setUseCaches(false);  
            // 设置请求方式（GET/POST）  
            httpUrlConn.setRequestMethod(requestMethod);  
  
            if ("GET".equalsIgnoreCase(requestMethod))  
                httpUrlConn.connect();  
  
            // 当有数据需要提交时  
            if (null != outputStr) {  
                OutputStream outputStream = httpUrlConn.getOutputStream();  
                // 注意编码格式，防止中文乱码  
                outputStream.write(outputStr.getBytes("UTF-8"));  
                outputStream.close();  
            }  
  
            // 将返回的输入流转换成字符串  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
            inputStream.close();  
            inputStream = null;  
            httpUrlConn.disconnect();  
            jsonObject = JSONObject.fromObject(buffer.toString());  
        } catch (ConnectException ce) {  
            log.error("Weixin server connection timed out.");  
        } catch (Exception e) {  
            log.error("https request error:{}", e);  
        }  
        return jsonObject;  
    }  
 

}

