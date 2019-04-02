package com.kuncms.pay.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.wxpay.sdk.WXPay;
import com.kuncms.pay.MyConfig;
@Controller
public class WechatPay {
	@RequestMapping("wechat_pay")
	public void wechat_pay(HttpServletRequest httpRequest) throws Exception{
		 MyConfig config = new MyConfig(httpRequest);
	     WXPay wxpay = new WXPay(config);
	     System.out.println(config.getAppID());
	     Map<String, String> data = new HashMap<String, String>();
	     data.put("body", "腾讯充值中心-QQ会员充值");
	     data.put("out_trade_no", "2016090910595900000012");
	     data.put("device_info", "");
	     data.put("fee_type", "CNY");
	     data.put("total_fee", "1");
	     data.put("spbill_create_ip", "39.98.164.226");
	     data.put("notify_url", "http://www.example.com/wxpay/notify");
	     data.put("trade_type", "JSAPI");  // 此处指定为扫码支付
	     data.put("product_id", "12");

	     try {
	         Map<String, String> resp = wxpay.unifiedOrder(data);
	         System.out.println(resp);
	     } catch (Exception e) {
	         e.printStackTrace();
	     }
	}
	
}
