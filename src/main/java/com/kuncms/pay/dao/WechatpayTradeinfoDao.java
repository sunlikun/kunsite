package com.kuncms.pay.dao;



import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kuncms.pay.model.WechatpayTradeinfo;
@Mapper
public interface WechatpayTradeinfoDao {

	void insert(WechatpayTradeinfo wechatpayTradeinfo);
   
}