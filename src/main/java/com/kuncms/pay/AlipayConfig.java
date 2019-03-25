package com.kuncms.pay;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2019030863453794";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCrVgUM4J2e0T0V\r\n" + 
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
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq1YFDOCdntE9FZIQdb+R\\r\\n\" + \r\n" + 
    		"			\"qSwTiD93M+VzrYhB9S6cOQr3c83IMWNSlt5sQusm1gtlj4QGaQFd6xsDp1eYuUCj\\r\\n\" + \r\n" + 
    		"			\"laEHZdEvb0FFKvj0gm4wUAKWPc2pf8SLLdLYz5bwktq0tr7gMKKocVbWSN/8a1dD\\r\\n\" + \r\n" + 
    		"			\"rE++p4dW7TKwYDuYEHSjxwnQDRq3Ft80CPPrxiTJ+fqDuoTihL7GiT715a7JlNFV\\r\\n\" + \r\n" + 
    		"			\"ffiRv4EYhbCjXe9CFFvu8ZIgthzcZCwiqh1RB4B2/XHXo4L0xAGMXBizhx17aupV\\r\\n\" + \r\n" + 
    		"			\"CXXdFW8pFzr7RJTp7+1DGPI+QyGbmKY7Lwuhs6WWaMDmwrCHP5aa/VFbJEMi7WMB\\r\\n\" + \r\n" + 
    		"			\"gQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://localhost:8080/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://localhost:8080/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

