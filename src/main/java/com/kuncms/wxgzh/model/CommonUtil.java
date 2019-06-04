package com.kuncms.wxgzh.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;


/**
 * 通用工具类
 * @author Admin
 *
 */
public class CommonUtil {
    private static Logger log = LoggerFactory.getLogger(CommonUtil.class);//定义日志获取状态输出

    // 凭证获取
    //获取access_token
    public final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    //获取素材列表
    public final static String MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";
    
    /**
     * 获取接口访问凭证
     * 
     * @param appid 凭证
     * @param appsecret 密钥
     * @return
     */
    public static AccessToken getToken(String appid, String appsecret) {
    	AccessToken token = null;
        String requestUrl = token_url.replace("APPID", appid).replace("APPSECRET", appsecret);//替换相应的参数
        // 发起GET请求获取凭证
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);//发送https请求
        if (null != jsonObject) {
            try {
                token = new AccessToken();
                token.setAccessToken(jsonObject.getString("access_token"));
                token.setExpiresin(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                token = null;
                // 获取token失败
                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return token;
    }
    
    /**
     * 发送https请求
     * 
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);

            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("连接超时：{}", ce);
        } catch (Exception e) {
            log.error("https请求异常：{}", e);
        }
        return jsonObject;
    }
    
    /**
     * 获取素材列表并存入集合中
     * @param accessToken 获取接口凭证的唯一标识
     * @param type 素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）
     * @param offset 从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
     * @param count 返回素材的数量，取值在1到20之间
     * @return
     */
    public static List<Material> getMaterial(String accessToken,String type,int offset,int count) { 
        List<Material> lists = new ArrayList<Material>();//定义图文素材实体类集合
        String outputStr="";//定义一个空的参数字符串
        String requestUrl = MATERIAL.replace("ACCESS_TOKEN", accessToken);//替换调access_token
        MaterialParam para = new MaterialParam();//调用接口所需要的参数实体类
        para.setType(type);
        para.setOffset(offset);
        para.setCount(count);
        JSONObject jsonObject = new JSONObject();
        jsonObject = JSONObject.fromObject(para);
        outputStr = jsonObject.toString();//将参数对象转换成json字符串

        jsonObject = httpsRequest(requestUrl, "POST", outputStr);  //发送https请求(请求的路径,方式,所携带的参数)
        // 如果请求成功,根据请求的类型进行处理
        if (null != jsonObject) {
           
        	try {  
        		 if(type=="news"){
        			  JSONArray jsonArray = jsonObject.getJSONArray("item");
                      for (int i = 0; i < jsonArray.size(); i++) {
                          JSONObject json = (JSONObject) jsonArray.get(i);
                          json = json.getJSONObject("content");
                          System.out.println(json);

                          JSONArray arr = json.getJSONArray("news_item");
                          json = (JSONObject) arr.get(0);

                          Material material = new Material();
                          String title = json.getString("title");
                          String author = json.getString("author");
                          String digest = json.getString("digest");
                          String thumb_media_id = json.getString("thumb_media_id");
                          //System.out.println(thumb_media_id);
                          String url = json.getString("url");
                          String content = json.getString("content");
                          material.setTitle(title);
                          material.setAuthor(author);
                          material.setDigest(digest);
                          material.setThumb_media_id(thumb_media_id);
                          material.setUrl(url);
                          material.setContent(content);
                          material.setShow_cover_pic(1);
                          lists.add(material);
                          }
                 }else if(type=="video") {
                	  JSONArray jsonArray = jsonObject.getJSONArray("item");
                      for (int i = 0; i < jsonArray.size(); i++) {
                          JSONObject json = (JSONObject) jsonArray.get(i);
                          //json = json.getJSONObject("content");
                          //System.out.println(json);

                          //JSONArray arr = json.getJSONArray("news_item");
                          //json = (JSONObject) arr.get(0);

                          Material material = new Material();
                          String media_id = json.getString("media_id");
                          String name = json.getString("name");
                          String update_time = json.getString("update_time");
                          //String url = json.getString("url");
                         
                          material.setMedia_id(media_id);
                          material.setName(name);
                          material.setUpdate_time(update_time);
                         
                       
                          //material.setUrl(url);
                         
                          lists.add(material);
                      }
              
                }
            } catch (JSONException e) {  
                accessToken = null;  
                // 获取Material失败  
                log.error("获取Material失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
            }  
        }  
        return lists;  
    }
   
}