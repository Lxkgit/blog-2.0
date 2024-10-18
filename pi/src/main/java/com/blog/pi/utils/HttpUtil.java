package com.blog.pi.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author lxk
 * @CreateTime 2024-10-01
 */

public class HttpUtil {

    /**
     * http get 接口请求
     * @param url url
     * @param header 请求头
     * @throws Exception
     */
    public static Map<String, Object> httpGet(String url, Map<String, String> header) throws Exception{

        // 返回数据
        Map<String, Object> result = new HashMap<>();

        //创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //创建请求对象
        HttpGet httpGet = new HttpGet(url);

        header.forEach(httpGet::addHeader);

        //发送请求，接受响应结果
        CloseableHttpResponse response = httpClient.execute(httpGet);

        //获取服务端返回的状态码   getStatusLine()获取响应行
        int statusCode = response.getStatusLine().getStatusCode();

        HttpEntity entity = response.getEntity();//获取响应体

        //借用EntityUtils工具类toString方法将其转为Json字符串
        String body = EntityUtils.toString(entity);

        //关闭资源
        response.close();
        httpClient.close();

        result.put("code", statusCode);
        result.put("data", body);
        return result;
    }

    public static Map<String, Object> httpPost(String url, Map<String, String> header, Map<String, Object> param) throws Exception{

        // 返回数据
        Map<String, Object> result = new HashMap<>();

        // 创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建请求对象
        HttpPost httpPost = new HttpPost(url);

        // 添加请求头参数
        if (header != null) {
            header.forEach(httpPost::addHeader);
        }

        // 请求体  封装成什么类型无所谓  但是下面调用new StringEntity()封装时
        // 必须转为JSON字符串 (与setContentType("application/json")保持一致)
        JSONObject jsonObject = new JSONObject();
        param.forEach((key, value) -> {
            jsonObject.put(key, value.toString());
        });

        StringEntity entity = new StringEntity(jsonObject.toString());

        // 指定请求编码方式
        entity.setContentEncoding("utf-8");

        // 数据格式
        entity.setContentType("application/json");
        httpPost.setEntity(entity);

        // 发送请求
        CloseableHttpResponse response = httpClient.execute(httpPost);

        // 解析返回结果
        int statusCode = response.getStatusLine().getStatusCode();

        HttpEntity entity1 = response.getEntity();
        String body = EntityUtils.toString(entity1);

        //关闭资源
        response.close();
        httpClient.close();

        result.put("code", statusCode);
        result.put("data", body);

        return result;
    }
}
