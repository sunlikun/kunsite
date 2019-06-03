package com.kuncms.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author wangxy
 * @version 1.0
 * @ClassName: HttpClientUtils
 * @Description: HttpClient工具类(这里用一句话描述这个类的作用)
 * @date 2019/2/21 17:20
 */
public class HttpClientUtils {
    
    // 默认字符集
    private static String encoding = "utf-8";

    
    public static String connectHttpsByPost(String path,Object o,File file) throws Exception {
    	String result =null;
    	URL urlObj = new URL(path);
        //连接
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
		con.setDoInput(true);

		con.setDoOutput(true);

		con.setUseCaches(false); // post方式不能使用缓存

		// 设置请求头信息

		con.setRequestProperty("Connection", "Keep-Alive");

		con.setRequestProperty("Charset", "UTF-8");
		// 设置边界

		String BOUNDARY = "----------" + System.currentTimeMillis();

		con.setRequestProperty("Content-Type",
				"multipart/form-data; boundary="

				+ BOUNDARY);

		// 请求正文信息

		// 第一部分：

		StringBuilder sb = new StringBuilder();

		sb.append("--"); // 必须多两道线

		sb.append(BOUNDARY);

		sb.append("\r\n");

		sb.append("Content-Disposition: form-data;name=\"media\";filelength=\""+file.length()+"\";filename=\""

				+ file.getName() + "\"\r\n");

		sb.append("Content-Type:application/octet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("utf-8");

		// 获得输出流

		OutputStream out = new DataOutputStream(con.getOutputStream());

		// 输出表头

		out.write(head);

		// 文件正文部分

		// 把文件已流文件的方式 推入到url中

		DataInputStream in = new DataInputStream(new FileInputStream(file));

		int bytes = 0;

		byte[] bufferOut = new byte[1024];

		while ((bytes = in.read(bufferOut)) != -1) {

			out.write(bufferOut, 0, bytes);

		}

		in.close();

		// 结尾部分

		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线

		out.write(foot);

		out.flush();

		out.close();

		StringBuffer buffer = new StringBuffer();

		BufferedReader reader = null;

		try {

			// 定义BufferedReader输入流来读取URL的响应

			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

			String line = null;

			while ((line = reader.readLine()) != null) {

				buffer.append(line);

			}

			if (result == null) {

				result = buffer.toString();

			}

		} catch (IOException e) {

			System.out.println("发送POST请求出现异常！" + e);

			e.printStackTrace();

			throw new IOException("数据读取异常");

		} finally {

			if (reader != null) {

				reader.close();

			}

		}
		return result;
    }
    
    

    /**
     * @param url      请求地址
     * @param headers  请求头
     * @param data     请求实体
     * @param encoding 字符集
     * @return String
     * @throws
     * @Title: sendPost
     * @Description: TODO(发送post请求)
     * @author wangxy
     * @date 2018年5月10日 下午4:36:17
     */
    public static String sendPost(String url, Map<String, String> headers, JSONObject data, String encoding) {
        
        // 创建Client
        CloseableHttpClient client = HttpClients.createDefault();
        // 创建HttpPost对象
        HttpPost httpPost = new HttpPost();
        try {
            // 设置请求地址
            httpPost.setURI(new URI(url));
            // 设置请求头
            if (headers != null) {
                Header[] allHeader = new BasicHeader[headers.size()];
                int i = 0;
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    allHeader[i] = new BasicHeader(entry.getKey(), entry.getValue());
                    i++;
                }
                httpPost.setHeaders(allHeader);
            }
            // 设置实体
            httpPost.setEntity(new StringEntity(JSON.toJSONString(data)));
            // 发送请求,返回响应对象
            CloseableHttpResponse response = client.execute(httpPost);
            return parseData(response);

        } catch (Exception e) {
           
        } finally {
            httpPost.releaseConnection();
        }
        return null;
    }

    /**
     * @param url  请求地址
     * @param data 请求实体
     * @return String
     * @throws
     * @Title: sendPost
     * @Description: TODO(发送post请求 ， 请求数据默认使用json格式 ， 默认使用UTF - 8编码)
     * @author wangxy
     * @date 2018年5月10日 下午4:37:28
     */
    public static String sendPost(String url, JSONObject data) {
        // 设置默认请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json");

        return sendPost(url, headers, data, encoding);
    }

    /**
     * @param url    请求地址
     * @param params 请求实体
     * @return String
     * @throws
     * @Title: sendPost
     * @Description: TODO(发送post请求 ， 请求数据默认使用json格式 ， 默认使用UTF - 8编码)
     * @author wangxy
     * @date 2018年5月10日 下午6:11:05
     */
    public static String sendPost(String url, Map<String, Object> params) {
        // 设置默认请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json");
        // 将map转成json
        JSONObject data = JSONObject.parseObject(JSON.toJSONString(params));
        return sendPost(url, headers, data, encoding);
    }

    /**
     * @param url     请求地址
     * @param headers 请求头
     * @param data    请求实体
     * @return String
     * @throws
     * @Title: sendPost
     * @Description: TODO(发送post请求 ， 请求数据默认使用UTF - 8编码)
     * @author wangxy
     * @date 2018年5月10日 下午4:39:03
     */
    public static String sendPost(String url, Map<String, String> headers, JSONObject data) {
        return sendPost(url, headers, data, encoding);
    }

    /**
     * @param url     请求地址
     * @param headers 请求头
     * @param params  请求实体
     * @return String
     * @throws
     * @Title: sendPost
     * @Description:(发送post请求，请求数据默认使用UTF-8编码)
     * @author wangxy
     * @date 2018年5月10日 下午5:58:40
     */
    public static String sendPost(String url, Map<String, String> headers, Map<String, String> params) {
        // 将map转成json
        JSONObject data = JSONObject.parseObject(JSON.toJSONString(params));
        return sendPost(url, headers, data, encoding);
    }

    /**
     * @param url      请求地址
     * @param params   请求参数
     * @param encoding 编码
     * @return String
     * @throws
     * @Title: sendGet
     * @Description: TODO(发送get请求)
     * @author wangxy
     * @date 2018年5月14日 下午2:39:01
     */
    public static String sendGet(String url, Map<String, Object> params, String encoding) {
       
        // 创建client
        CloseableHttpClient client = HttpClients.createDefault();
        // 创建HttpGet
        HttpGet httpGet = new HttpGet();
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            // 封装参数
            if (params != null) {
                for (String key : params.keySet()) {
                    builder.addParameter(key, params.get(key).toString());
                }
            }
            URI uri = builder.build();
         
            // 设置请求地址
            httpGet.setURI(uri);
            // 发送请求，返回响应对象
            CloseableHttpResponse response = client.execute(httpGet);
            return parseData(response);
        } catch (Exception e) {
           
        } finally {
            httpGet.releaseConnection();
        }
        return null;
    }

    /**
     * @param url    请求地址
     * @param params 请求参数
     * @return String
     * @throws
     * @Title: sendGet
     * @Description: TODO(发送get请求)
     * @author wangxy
     * @date 2018年5月14日 下午2:32:39
     */
    public static String sendGet(String url, Map<String, Object> params) {
        return sendGet(url, params, encoding);
    }

    /**
     * @param url 请求地址
     * @return String
     * @throws
     * @Title: sendGet
     * @Description: TODO(发送get请求)
     * @author wangxy
     * @date 2018年5月14日 下午2:33:45
     */
    public static String sendGet(String url) {
        return sendGet(url, null, encoding);
    }

    /**
     * 解析response
     *
     * @param response
     * @return
     * @throws Exception
     */
    public static String parseData(CloseableHttpResponse response) throws Exception {
        // 获取响应状态
        int status = response.getStatusLine().getStatusCode();
        if (status == HttpStatus.SC_OK) {
            // 获取响应数据
            return EntityUtils.toString(response.getEntity(), encoding);
        } else {
            
        }
        return null;
    }
}
