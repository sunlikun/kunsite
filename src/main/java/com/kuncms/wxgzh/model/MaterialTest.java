package com.kuncms.wxgzh.model;

import java.util.List;

import org.junit.Test;

public class MaterialTest {
    
   @Test
    public void testGetMaterial() {
    	AccessToken token = CommonUtil.getToken(WeChatConfig.APP_ID,WeChatConfig.APP_SECRET);//获取接口访问凭证access_token
        //System.out.println(token);
    	List<Material> lists = CommonUtil.getMaterial(token.getAccessToken(),"video",20,10);//调用获取素材列表的方法
        System.out.println("资源个数"+lists.size());//输出
    }
//    @Test
//    public void testGetToken2() {
//    	 AccessToken token = CommonUtil.getToken(WeChatConfig.APP_ID,WeChatConfig.APP_SECRET);//其中的WeChatConfig方法中有定义的自己微信公众号的appid和appsecret值 用到时直接调用
//         	System.out.println("access_token:"+token.getAccessToken());
//         	System.out.println("expires_in:"+token.getExpiresin());
//    }
    
  
}