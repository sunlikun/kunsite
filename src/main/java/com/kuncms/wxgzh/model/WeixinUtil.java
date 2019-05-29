package com.kuncms.wxgzh.model;

import java.io.UnsupportedEncodingException;

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

}

