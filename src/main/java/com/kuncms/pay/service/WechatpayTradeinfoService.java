package com.kuncms.pay.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuncms.pay.dao.WechatpayTradeinfoDao;
import com.kuncms.pay.model.WechatpayTradeinfo;
import com.kuncms.util.DateUtil;
@Service
public class WechatpayTradeinfoService {
	@Autowired
	WechatpayTradeinfoDao wechatpayTradeinfoDao;
	public void insert(WechatpayTradeinfo wechatpayTradeinfo,javax.servlet.http.HttpServletRequest request) throws ParseException {
		// TODO Auto-generated method stub
		String id=UUID.randomUUID().toString().replace("-", "").toLowerCase();
	
		wechatpayTradeinfo.setId(id);
		DateUtil dateUtil=new DateUtil();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		wechatpayTradeinfo.setCreateTime(format.parse(dateUtil.getNow()));
		wechatpayTradeinfo.setUpdateTime(format.parse(dateUtil.getNow()));
		HttpSession session = request.getSession();
		String userName=(String) session.getAttribute("loginName");
		wechatpayTradeinfo.setCreateBy(userName);
		wechatpayTradeinfo.setUpdateBy(userName);
		wechatpayTradeinfo.setFlag("1");
		wechatpayTradeinfoDao.insert(wechatpayTradeinfo);
	}
	public void updateByOut_trade_no(WechatpayTradeinfo wechatpayTradeinfo) throws ParseException {
		// TODO Auto-generated method stub

	
		DateUtil dateUtil=new DateUtil();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		wechatpayTradeinfo.setUpdateTime(format.parse(dateUtil.getNow()));
		wechatpayTradeinfoDao.updateByOut_trade_no(wechatpayTradeinfo);
	}
   
}