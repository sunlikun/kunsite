package com.kuncms.pay;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.validator.internal.util.privilegedactions.GetResource;
import org.springframework.util.ResourceUtils;

import com.github.wxpay.sdk.WXPayConfig;

public class MyConfig implements WXPayConfig{

    private byte[] certData;

    public MyConfig(HttpServletRequest httpRequest) throws Exception {
    	String url=httpRequest.getScheme()+":"+"\\\\"+httpRequest.getServerName()+":"+httpRequest.getServerPort();
        System.out.println(url);
    	String certPath =url+"/cert/apiclient_cert.p12";
    	System.out.println(certPath);
    	File file = ResourceUtils.getFile("classpath:static/cert/apiclient_cert.p12");;
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    public String getAppID() {
        return "wxa1f4e7957d869f53";
    }

    public String getMchID() {
        return "1529797251";
    }

    public String getKey() {
        return "88888888888888888888888888888888";
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }
}
