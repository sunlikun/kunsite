package com.kuncms.pay.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.util.Base64;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayOpenPublicTemplateMessageIndustryModifyRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayOpenPublicTemplateMessageIndustryModifyResponse;
import com.kuncms.core.CoreController;
import com.kuncms.pay.model.AlipayTradeInfo;
import com.kuncms.pay.service.AlipayTradeInfoService;
import com.kuncms.thumbnail.service.ThumbnailService;
import com.kuncms.user.model.User;
import com.kuncms.user.service.UserService;
@Controller
public class Alipay {
	@Autowired
	AlipayTradeInfoService alipayTradeInfoService;
	@Autowired
	UserService userService;
	
	String ALIPAY_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsLg3MWFpxoS35S5belVylSjbsWTYqwMlCyvyLJA7eyRHYWlpoQkMGW3KHk4I2qSNqejI5Ky1HhHsju0Ka+O9Y3uCI97/Of9oVTLhjImvjkZMCUi/dVdTQ4lUMrERHZcaRXxbieB9lV1YMttJDKYXVBTHTxVHc6RgwQQZ5pzNRXZAxLzmEl9tYQn2ZI7qRSIdGAwbQlI8ups+IhwAviEGI0RfaQA6MKyEQtEZC1h4ulNUsWXulj1CresYSbqSXm3zC/siwNNFOCTrfbxg2/c7f3wa6QHhiky+thP1X/dDRUrN07YFtYHVXoH9ko/teBTZZrw1cbXkK4lgdxwnkqkF4QIDAQAB";
	String APP_PRIVATE_KEY="MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCrVgUM4J2e0T0V\r\n" + 
			"khB1v5GpLBOIP3cz5XOtiEH1Lpw5CvdzzcgxY1KW3mxC6ybWC2WPhAZpAV3rGwOn\r\n" + 
			"V5i5QKOVoQdl0S9vQUUq+PSCbjBQApY9zal/xIst0tjPlvCS2rS2vuAwoqhxVtZI\r\n" + 
			"3/xrV0OsT76nh1btMrBgO5gQdKPHCdANGrcW3zQI8+vGJMn5+oO6hOKEvsaJPvXl\r\n" + 
			"rsmU0VV9+JG/gRiFsKNd70IUW+7xkiC2HNxkLCKqHVEHgHb9cdejgvTEAYxcGLOH\r\n" + 
			"HXtq6lUJdd0VbykXOvtElOnv7UMY8j5DIZuYpjsvC6GzpZZowObCsIc/lpr9UVsk\r\n" + 
			"QyLtYwGBAgMBAAECggEAf5MXQ5VkB8NItm0B8IrpzItTJ9oJygyJa6bPZd6E2DyA\r\n" + 
			"MCwDajLIZinLdrBdRHdL8eaeV0V7RvngaJPi1d15cIpsVbXCmhaBiBPDC/M76fdj\r\n" + 
			"IzWlhcs0zrP2V+vNyasrb/8EM3KJ9YpxF+Yfr1VUwFLIZTly0j8x8RWujmIrKKG/\r\n" + 
			"OjZiOq/nIRw/13TnYhPKIKeRUH2EyDh30GGNVLi+iVmIpIjG7SnxGXD2xdtMuD1S\r\n" + 
			"IN2PzdwMRd/0/ZAYx/lJmSsRqqNEdJc+O8BFew8x7fDpMPlvWbEFTBPewVllmA2j\r\n" + 
			"i0DrvMkbeiTet3NlZeDxcZGctOE/TGJX+ZwmW0SQoQKBgQDgMaGIwARgUva9wjI5\r\n" + 
			"UKM6gKM1thcNwZRiNQAvlIld4yQUy0G5FjqxGJ8WVNktnrk+VofLPdAZEcaQb1E+\r\n" + 
			"ddKlEFT7h61Eu+ONLMN8VpaighWDahyjMFkgBy9yKoH9T839v0On7cAm6g446XI0\r\n" + 
			"CMrvmL+yscBNLQwi1xInF4N5FQKBgQDDpKyqAW/UAoXInAn9ZdlIot5RUz27qqty\r\n" + 
			"lSy6703mMpw9QWEGMwKqtNsTkdZy3Xhqr/8TtoYJhaP4eocXanldZC5uTI5JHc/N\r\n" + 
			"Dw5/kiASBe3lIfG7AfX0T31FNpO0aIBtq0NNNbeQ06P1YlO3het/byxnW9o0xUFj\r\n" + 
			"bMMGekVpvQKBgDNLx8z1Oksb0G7chwdMJvzUwAMjFaVFHIFX5SH912xO80t6sh/6\r\n" + 
			"MRWiL4w5f7OLdJEJ1WowS3Pg+FDF1432AWtZEEi443EtmEQN8PB7E149a1S1K7Y+\r\n" + 
			"8rV8T2PMzA88ekJLx9wdbvC4buagq0uyk1Q82+ez+e7ulq9GDFY7cA59AoGAMA5p\r\n" + 
			"IzFLKMad2hMKGSZFYyQue8d4qHpZtsGRxTE2706LNjiy/nTVON1H2ty90mU4SOyG\r\n" + 
			"OW9iKUCccFKYkFXlDd2n0gwX/znFa1dBdnCMVCEEuH3IBOmMYsCLws6PJYdt1IJ2\r\n" + 
			"+6YwOvDhm0rVyYSpvhmuzIyxYrj5QNTcauDp3/kCgYAtowvu3RkbroxD8ACu4T8D\r\n" + 
			"po5wx9DrZF2Ldbb2xiO9h//ZvT299sd67HIBZIrEi04TJKBU1iYZwUSck9bucML3\r\n" + 
			"372Ku9S8flh64rfRgUhI/tuA7BkFKz9bCVY23zX05UjyiRNaSOpwt3yjOUyOWK+i\r\n" + 
			"n0KMECgnjknDLns0Pnx8oQ==";
	String CHARSET="UTF-8";
	String  APP_ID="2019030863453794";
	String SIGN_TYPE="RSA2";
	String FORMAT="json";
	
	public void getAlipayClient() throws AlipayApiException{
		//实例化客户端
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
		//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.open.public.template.message.industry.modify 
		AlipayOpenPublicTemplateMessageIndustryModifyRequest request = new AlipayOpenPublicTemplateMessageIndustryModifyRequest();
		//SDK已经封装掉了公共参数，这里只需要传入业务参数
		//此次只是参数展示，未进行字符串转义，实际情况下请转义
		request.setBizContent("  {" +
		"    \"primary_industry_name\":\"IT科技/IT软件与服务\"," +
		"    \"primary_industry_code\":\"10001/20102\"," +
		"    \"secondary_industry_code\":\"10001/20102\"," +
		"    \"secondary_industry_name\":\"IT科技/IT软件与服务\"" +
		" }");
		AlipayOpenPublicTemplateMessageIndustryModifyResponse response = alipayClient.execute(request); 
		//调用成功，则处理业务逻辑
		if(response.isSuccess()){
			//.....
		}
	}
	
	/**
	 * @param httpRequest
	 * @param httpResponse
	 * @throws ServletException
	 * @throws IOException
	 * alipay进行金币充值
	 */
	@RequestMapping("alipay")
	public void alipay(HttpServletRequest httpRequest, HttpServletResponse httpResponse,String val) throws ServletException, IOException {
			PKCS8EncodedKeySpec priPKCS8    = new PKCS8EncodedKeySpec( Base64.decodeFast(APP_PRIVATE_KEY) );
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE); //获得初始化的AlipayClient
			AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
			//String url=httpRequest.getScheme()+"://"+httpRequest.getServerName()+":"+httpRequest.getServerPort();
			//System.out.println(url);
			alipayRequest.setReturnUrl("http://www.pergirls.com/membership");
			alipayRequest.setNotifyUrl("http://www.pergirls.com/notify");//在公共参数中设置回跳和通知地址
			String out_trade_no=UUID.randomUUID().toString();
			HttpSession session=httpRequest.getSession();
			alipayRequest.setBizContent("{" +
			"    \"out_trade_no\":\""+out_trade_no+"\"," +
			"    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
			"    \"total_amount\":\""+val+"\"," +
			"    \"subject\":\"普格娱乐金币充值\"," +
			"    \"body\":\"普格娱乐金币充值\"," +
			"    \"passback_params\":\""+session.getAttribute("loginName")+"\"," +
			"    \"extend_params\":{" +
			"    \"sys_service_provider_id\":\"2088511833207846\"" +
			"    }"+
			"  }");//填充业务参数
			String form="";
			try {
			form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
			} catch (AlipayApiException e) {
			e.printStackTrace();
			}
			httpResponse.setContentType("text/html;charset=" + CHARSET);
			httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
			httpResponse.getWriter().flush();
			httpResponse.getWriter().close();
	}
	
	
	
	@RequestMapping(value = "/notify")
	public void notify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, AlipayApiException {
		System.out.println("支付宝交易异步通知");
		
		//获取支付宝POST过来反馈信息
		
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			System.out.println(valueStr);
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		
		boolean signVerified = AlipaySignature.rsaCheckV1(params,ALIPAY_PUBLIC_KEY, CHARSET, SIGN_TYPE); //调用SDK验证签名
		
		//——请在这里编写您的程序（以下代码仅作参考）——
		
		/* 实际验证过程建议商户务必添加以下校验：
		1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
		4、验证app_id是否为该商户本身。
		*/
		System.out.println("signVerified:"+signVerified);
		if(signVerified) {//验证成功
			System.out.println("验证成功");
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		
			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
		
			//交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
			
			//通知时间
			String notify_time = new String(request.getParameter("notify_time").getBytes("ISO-8859-1"),"UTF-8");
			
			//订单金额
			String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
			//实收金额
			//String receipt_amount = new String(request.getParameter("receipt_amount").getBytes("ISO-8859-1"),"UTF-8");
			
			//订单标题
			String subject = new String(request.getParameter("subject"));
			
			//交易创建时间
			String gmt_create = new String(request.getParameter("gmt_create").getBytes("ISO-8859-1"),"UTF-8");
			
			//交易付款时间
			String gmt_payment = new String(request.getParameter("gmt_payment").getBytes("ISO-8859-1"),"UTF-8");
			//交易结束时间
			//String gmt_close = new String(request.getParameter("gmt_close").getBytes("ISO-8859-1"),"UTF-8");
			
			//公共回传参数，如果请求时传递了该参数，则返回给商户时会在异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝
			String passback_params = new String(request.getParameter("passback_params").getBytes("ISO-8859-1"),"UTF-8");
			//int gold_coin=Integer.parseInt(total_amount)*10;
			
			AlipayTradeInfo alipayTradeInfo=new AlipayTradeInfo();
			
			if(trade_status.equals("TRADE_FINISHED")){
				//判断该笔订单是否在商户网站中已经做过处理
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//如果有做过处理，不执行商户的业务程序
					
				//注意：
				//退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
				System.out.println("支付宝交易失败");
			}else if (trade_status.equals("TRADE_SUCCESS")){
				//判断该笔订单是否在商户网站中已经做过处理
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//如果有做过处理，不执行商户的业务程序
				System.out.println("支付宝交易成功");
				//注意：
				//付款完成后，支付宝系统发送该交易状态通知
				alipayTradeInfo.setId(out_trade_no);
				//alipayTradeInfo.setGmt_close(gmt_close);
				alipayTradeInfo.setGmt_create(gmt_create);
				alipayTradeInfo.setGmt_payment(gmt_payment);
				alipayTradeInfo.setNotify_time(notify_time);
				alipayTradeInfo.setSubject(subject);
				alipayTradeInfo.setTotal_amount(total_amount);
				alipayTradeInfo.setTrade_no(trade_no);
				alipayTradeInfo.setTrade_status(trade_status);
				alipayTradeInfo.setUser_name(passback_params);
				alipayTradeInfoService.insert(alipayTradeInfo);
				
				CoreController  con=new CoreController();
				//查询用户现有金币数并增加
				User user=new User();
				user.setUser_name(passback_params);
				ArrayList<User> userl=(ArrayList<User>) userService.check_username(user);
				User loginuser=null;
				if(userl.size()>0){
					 loginuser=userl.get(0);
				}
				int now_gold_coin=loginuser.getGold_coin();
				System.out.println("now_gold_coin"+now_gold_coin);
				int gold_coin=(int) (Double.parseDouble(total_amount)*10);
				gold_coin=gold_coin+now_gold_coin;
				System.out.println("gold_coin"+gold_coin);
				userService.addUserGoldCoin(loginuser.getId(),gold_coin);
			}
			
			
			  response.getOutputStream().print("success");
		}else {//验证失败
			
			  response.getOutputStream().print("fail");
			//调试用，写文本函数记录程序运行情况是否正常
			//String sWord = AlipaySignature.getSignCheckContentV1(params);
			//AlipayConfig.logResult(sWord);
		}
		
		//——请在这里编写您的程序（以上代码仅作参考）——
	}
	
	
	
	
	
	
	
	
}
