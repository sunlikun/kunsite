package com.kuncms.core;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.kuncms.user.service.UserService;
import com.kuncms.util.Decript;


@Controller
public class WechatController {
	
	@Autowired
	UserService userService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private final String token = "1144954876";
	
	/**
	 * 微信公众号接入验证
	 * @param map
	 * @return
	 */
	@RequestMapping("/auth_wechat")
	protected void auth_wechat(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
