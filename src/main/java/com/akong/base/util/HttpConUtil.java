package com.akong.base.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * Http流读取工具
 *
 * @author Akong
 * @create 2021/11/26 18:24
 */
public class HttpConUtil {
    /**
     * 执行URL连接并获取内容
     *
     * @param con URL连接对象
     * @return 返回内容
     * @throws Exception 抛出异常
     */
    public static StringBuilder requestCon(HttpURLConnection con) throws Exception {
        // 设置请求时长
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        // 启动连接
        con.connect();

        // 响应字符串
        StringBuilder jsonString = new StringBuilder();

        // 判断是否请求成功
        if (con.getResponseCode() >= 200 && con.getResponseCode() <= 299) {
            // 获取响应流对象
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));

            // 临时储存字符串
            String temp;

            // 读取流对象
            while (null != (temp = br.readLine())) {
                // 加入到响应字符串
                jsonString.append(temp);
            }
        } else if (con.getHeaderField("Location") != null) {
            jsonString.append("{'code':200,'url':'").append(con.getHeaderField("Location")).append("'}");
        } else {
            // 若请求不成功抛出异常
            throw new RuntimeException("网络异常：请重试");
        }
        return jsonString;
    }

    /**
     * Post请求
     *
     * @param url  请求地址
     * @param data 请求参数
     * @return 返回结果
     */
    public static String post(String url, String data, Map<String, String> headers) {
        try {
            CookieManager cookieManager = new CookieManager();
            CookieHandler.setDefault(cookieManager);

            // 定义URL
            URL u = new URL(url);
            // 建立HTTP连接
            HttpURLConnection con = (HttpURLConnection) u.openConnection();
            // 设置请求类型
            con.setRequestMethod("POST");

            // 配置con连接
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // 判断请求头是否为空
            if (null != headers) {
                // 不为空则进行设置
                headers.forEach(con::setRequestProperty);
            }

            // 开启连接
            con.connect();

            // 获取数据输出流
            DataOutputStream out = new DataOutputStream(con
                    .getOutputStream());

            // 写入参数
            out.writeBytes(data);

            // 读取流信息
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;

            // 定义返回内容
            StringBuilder sb = new StringBuilder();

            // 输出返回信息
            while ((line = reader.readLine()) != null) {
                // 拼接内容
                sb.append(line);
                // 换行
                sb.append("\r\n");
            }

            // 得到对应的Cookie
            YSEPanHelper.cookies = cookieManager.getCookieStore().getCookies();

            // 关闭流
            reader.close();
            out.flush();
            out.close();

            // 关闭连接
            con.disconnect();

            // 返回请求内容
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 重载Post请求
     *
     * @param url  请求地址
     * @param data 请求参数
     * @return 返回结果
     */
    public static String post(String url, String data) {
        return post(url, data, null);
    }
}
