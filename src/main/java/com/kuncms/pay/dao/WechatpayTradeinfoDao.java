package com.kuncms.pay.dao;



import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kuncms.pay.controller.WechatPay;
import com.kuncms.pay.model.WechatpayTradeinfo;
import com.kuncms.user.model.User;
@Mapper
public interface WechatpayTradeinfoDao {

	void insert(WechatpayTradeinfo wechatpayTradeinfo);

	void updateByOut_trade_no(WechatpayTradeinfo wechatpayTradeinfo);

	ArrayList<WechatPay> queryRechargeRecord(WechatpayTradeinfo wechatpayTradeinfo);
   
}