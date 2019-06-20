package com.kuncms.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kuncms.coverphoto.model.Coverphoto;
import com.kuncms.coverphoto.service.CoverphotoService;
import com.kuncms.wxgzh.model.AccessToken;
import com.kuncms.wxgzh.model.CommonUtil;
import com.kuncms.wxgzh.model.Material;
import com.kuncms.wxgzh.model.MaterialTest;
import com.kuncms.wxgzh.model.WeChatConfig;

/**
 * 整合定时任务
 * EnableScheduling 实现spring中的task功能
 * @Scheduled中配置时间表达式
 * @author 231
 *
 */
@Component
@Configuration        // 相当于配置beans, 
@EnableScheduling    // <task:*>, 让spring进行任务调度
public class SchedulingConfig {
	@Autowired
	CoverphotoService coverphotoService;
	
	@Scheduled(cron="0 */1 * * * ?")    // 
    public void scheduler() {
        System.out.println("定时任务开始");
      //查询视频库，如果存在MediaId为空的项，则调用微信接口，进行MediaId绑定
    	ArrayList<Coverphoto> list=coverphotoService.queryVideoWithnoMediaId();
	    	if(list.size()>0) {
	    		AccessToken token = CommonUtil.getToken(WeChatConfig.APP_ID,WeChatConfig.APP_SECRET);//获取接口访问凭证access_token
	            //System.out.println(token);
	    		int i=0;
	    		while(true) {
	    			List<Material> lists = CommonUtil.getMaterial(token.getAccessToken(),"video",i,20);//调用获取素材列表的方法
	                System.out.println("资源个数"+lists.size());//输出
	                for(int j=0;j<lists.size();j++) {
	                	Material material=lists.get(j);
	                	String name=material.getName();
	                	String mediaId=material.getMedia_id();
	                	for(int k=0;k<list.size();k++) {
	                		Coverphoto coverphoto=list.get(k);
	                		if(coverphoto.getVideo_name().endsWith(name)) {
	                			coverphoto.setMediaId(mediaId);
	                			coverphotoService.upVideo(coverphoto);
	                		}
	                	}
	                }
	                i=i+20;
	                if(lists.size()==0) {
	                	System.out.println("定时任务结束");
	                	break;//结束循环
	                }
	    		}
	    	}else {
	    		System.out.println("没有要更新的数据,定时任务结束");
	    	}
      
    }
    
}