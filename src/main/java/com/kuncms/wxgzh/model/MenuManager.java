package com.kuncms.wxgzh.model;

 
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
  
/** 
 * 菜单管理器类 
 *  
 * @author liufeng 
 * @date 2013-08-08 
 */  
public class MenuManager {  
    private static Logger log = LoggerFactory.getLogger(MenuManager.class);  
  
    public static void main(String[] args) {  
        // 第三方用户唯一凭证  
        String appId = WeChatConfig.APP_ID;  
        // 第三方用户唯一凭证密钥  
        String appSecret = WeChatConfig.APP_SECRET;  
  
        // 调用接口获取access_token  
        AccessToken at = CommonUtil.getToken(appId, appSecret);  
  
        if (null != at) {  
            // 调用接口创建菜单  
            int result = WeixinUtil.createMenu(getMenu(), at.getAccessToken());  
  
            // 判断菜单创建结果  
            if (0 == result) {
            	log.info("菜单创建成功！");
            	System.out.println("菜单创建成功！");
            } else  {
            	log.info("菜单创建失败，错误码：" + result);  
            	System.out.println("菜单创建失败，错误码：" + result);
            }
                
        }  
    }  
  
    /** 
     * 组装菜单数据 
     *  
     * @return 
     */  
    private static Menu getMenu() {  
        CommonButton btn11 = new CommonButton();  
        btn11.setName("在线播放");  
        btn11.setType("view");  
        btn11.setKey("11");
        btn11.setUrl("http://www.pergirls.com/we_moreOnline");
//  
//        CommonButton btn12 = new CommonButton(); 
//        btn12.setName("金币点播");  
//        btn12.setType("view");  
//        btn12.setKey("12");
//        btn12.setUrl("http://www.pergirls.com/toVipPlayNumInfo");
        
//        ComplexButton mainBtn1 = new ComplexButton();  
//        mainBtn1.setName("点播码");  
//        mainBtn1.setSub_button(new CommonButton[] {btn11,btn12}); 
 //  
//        CommonButton btn13 = new CommonButton();  
//        btn13.setName("周边搜索");  
//        btn13.setType("click");  
//        btn13.setKey("13");  
//  
//        CommonButton btn14 = new CommonButton();  
//        btn14.setName("历史上的今天");  
//        btn14.setType("click");  
//        btn14.setKey("14");  
        
        CommonButton btn00 = new CommonButton();  
        btn00.setName("每日福利"); 
        btn00.setType("click");  
        btn00.setKey("00");
        
        CommonButton btn21 = new CommonButton();  
        btn21.setName("官网下载"); 
        btn21.setType("view");  
        btn21.setKey("21");
        btn21.setUrl("http://www.pergirls.com/WechatController/index_auth");
//  
//        CommonButton btn22 = new CommonButton();  
//        btn22.setName("经典游戏");  
//        btn22.setType("click");  
//        btn22.setKey("22");  
//  
//        CommonButton btn23 = new CommonButton();  
//        btn23.setName("美女电台");  
//        btn23.setType("click");  
//        btn23.setKey("23");  
//  
//        CommonButton btn24 = new CommonButton();  
//        btn24.setName("人脸识别");  
//        btn24.setType("click");  
//        btn24.setKey("24");  
//  
//        CommonButton btn25 = new CommonButton();  
//        btn25.setName("聊天唠嗑");  
//        btn25.setType("click");  
//        btn25.setKey("25");  
//  
//        CommonButton btn31 = new CommonButton();  
//        btn31.setName("Q友圈");  
//        btn31.setType("click");  
//        btn31.setKey("31");  
//  
//        CommonButton btn32 = new CommonButton();  
//        btn32.setName("电影排行榜");  
//        btn32.setType("click");  
//        btn32.setKey("32");  
//  
        CommonButton btn33 = new CommonButton();  
        btn33.setName("我的账户");  
        btn33.setKey("33"); 
        btn33.setType("view");  
        btn33.setUrl("http://www.pergirls.com/WechatController/auth");
  
        CommonButton duihuan = new CommonButton();  
        duihuan.setName("金币兑换");  
        duihuan.setKey("dh"); 
        duihuan.setType("view");  
        duihuan.setUrl("http://www.pergirls.com/WechatController/auth_duihuan");
  
        ComplexButton mainBtn2 = new ComplexButton();  
        mainBtn2.setName("个人中心"); 
        mainBtn2.setSub_button(new CommonButton[] { btn33, duihuan});  
  
//        ComplexButton mainBtn3 = new ComplexButton();  
//        mainBtn3.setName("更多体验");  
        //mainBtn3.setSub_button(new CommonButton[] { btn31, btn32, btn33 });  
  
        /** 
         * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br> 
         *  
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br> 
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br> 
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 }); 
         */  
        Menu menu = new Menu();  
        //menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });  
        menu.setButton(new Button[] {btn00, btn21, mainBtn2 }); 
        return menu;  
    }  
}  