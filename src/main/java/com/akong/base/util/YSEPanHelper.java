package com.akong.base.util;

import com.akong.base.pojo.YSDir;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 永硕e盘操作工具类
 *
 * @author Akong
 * @since 2022/4/4 1:06
 */
public class YSEPanHelper {
    /**
     * 网盘域名
     */
    public static String url = "http://fanqiewl.ysepan.com/";
    /**
     * 网盘密码
     */
    public static String pwd = "yin20020419";
    /**
     * 网盘HOST地址
     */
    public static String HOST = "http://cc.ys168.com/";
    /**
     * 登录Cookie验证
     */
    public static List<HttpCookie> cookies = null;

    private static String post_up = "-----------------------------74266228016131691941175105308\r\n" +
            "Content-Disposition: form-data; name=\"guid\"\r\n" +
            "\r\n" +
            "[guid]\r\n" +
            "-----------------------------74266228016131691941175105308\r\n" +
            "Content-Disposition: form-data; name=\"pz\"\r\n" +
            "\r\n" +
            "[pz]\r\n" +
            "-----------------------------74266228016131691941175105308\r\n" +
            "Content-Disposition: form-data; name=\"id\"\r\n" +
            "\r\n" +
            "WU_FILE_0\r\n" +
            "-----------------------------74266228016131691941175105308\r\n" +
            "Content-Disposition: form-data; name=\"name\"\r\n" +
            "\r\n" +
            "[文件名]\r\n" +
            "-----------------------------74266228016131691941175105308\r\n" +
            "Content-Disposition: form-data; name=\"type\"\r\n" +
            "\r\n" +
            "audio/mpeg\r\n" +
            "-----------------------------74266228016131691941175105308\r\n" +
            "Content-Disposition: form-data; name=\"lastModifiedDate\"\r\n" +
            "\r\n" +
            "[时间]\r\n" +
            "-----------------------------74266228016131691941175105308\r\n" +
            "Content-Disposition: form-data; name=\"size\"\r\n" +
            "\r\n" +
            "[文件大小]\r\n" +
            "-----------------------------74266228016131691941175105308\r\n" +
            "Content-Disposition: form-data; name=\"file\"; filename=\"[文件名]\"\r\n" +
            "Content-Type: application/octet-stream\r\n\r\n";


    /**
     * 初始化登录信息
     *
     * @param url 网盘域名
     * @param pwd 网盘密码
     */
    public static void initLoginInfo(String url, String pwd) {
        YSEPanHelper.url = url;
        YSEPanHelper.pwd = pwd;
    }

    /**
     * 登录网盘（重载）
     *
     * @return 返回状态
     */
    public static String login() {
        return login(null);
    }

    /**
     * 登录网盘
     *
     * @param code 验证码（不需要验证码时可为空）
     * @return 返回状态
     */
    public static String login(String code) {
        // 定义请求的地址
        StringBuilder openUrl = new StringBuilder("f_ht/ajcx/gly.aspx?cz=dl&yzm=");
        // 插入协议头
        openUrl.insert(0, HOST);

        // 判断是否需要验证码
        if (StringUtil.isNotBlank(code)) {
            // 不为空则拼接上验证码
            openUrl.append(code);
        }

        // 拼接必要的数据
        openUrl.append("&_dlmc=");
        // 拼接网站前缀
        openUrl.append(subHostHead(url));

        // 定义参数
        String content = null;

        try {
            content = "glmm=" + URLEncoder.encode(pwd, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 调用工具发送POST请求得到结果
        String post = HttpConUtil.post(openUrl.toString(), content);

        // 定义返回结果信息
        String info = "登录成功";

        // 判断结果设置结果信息
        if (post.contains("alert"))
            // 代表登录失败 =》 提取失败信息
            info = post.substring(post.indexOf("alert") + 7, post.indexOf(")") - 1);

        return info;
    }

    /**
     * 搜索指定目录
     *
     * @param name 目录名
     */
    public static YSDir selectDir(String name) {
        // 定义请求链接http://cc.ys168.com//f_ht/ajcx/wj.aspx?cz=dq&jsq=0&mlbh=1885556&wjpx=1&_dlmc=694336034&_dlmm=
        String openUrl = "http://cb.ysepan.com/f_ht/ajcx/ml.aspx?cz=ml_dq&_dlmc=" + subHostHead(url) + "&_dlmm=";

        try {
            // 构建URL
            URL u = new URL(openUrl);
            // 建立连接
            HttpURLConnection con = (HttpURLConnection) u.openConnection();
            // 设置Referer
            con.setRequestProperty("Referer", "http://cb.ysepan.com/f_ht/ajcx/000ht.html?bbh=1163");
            // 设置Cookie
            con.setRequestProperty("Cookie", String.valueOf(cookies.get(0)));
            // 通过工具类得到结果
            String get = HttpConUtil.requestCon(con).toString();

            // 定义正则表达式
            String pat = "<li id=\"(.*?)\" .*?href=\"javascript:;\">(.*?)</a><label>(.*?)</label>";
            Pattern pattern = Pattern.compile(pat);
            // 进行匹配
            Matcher matcher = pattern.matcher(get);

            // 获取结果
            if (matcher.find()) {
                // 返回目录对象
                return new YSDir(matcher.group(1).substring(matcher.group(1).indexOf("_") + 1), matcher.group(2), matcher.group(3));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("未找到目录");
    }

    /**
     * 获取目录的pz值
     *
     * @param dir 永硕E盘目录对象
     * @return 返回目录的PZ值
     */
    public static String getPz(YSDir dir) {
        // 定义请求链接
        String openUrl = "http://cc.ys168.com//f_ht/ajcx/wj.aspx?cz=dq&jsq=0&mlbh=" + dir.getDirId() + "&wjpx=1&_dlmc=" + subHostHead(url) + "&_dlmm=";

        try {
            // 构建URL
            URL u = new URL(openUrl);
            // 建立连接
            HttpURLConnection con = (HttpURLConnection) u.openConnection();
            // 设置Referer
            con.setRequestProperty("Referer", "http://cc.ys168.com/f_ht/ajcx/000ht.html?bbh=1139");
            // 设置Cookie
            con.setRequestProperty("Cookie", String.valueOf(cookies.get(0)));
            // 通过工具类得到结果
            String get = HttpConUtil.requestCon(con).toString();

            // 截取PZ值
            return get.substring(get.indexOf("x.scpz = '") + 10, get.indexOf("';x.xzzt"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 找不到则抛出异常
        throw new RuntimeException("未找到" + dir.getDirName() + "的PZ值");
    }

    /**
     * 上传音乐
     */
    public static void uploadMusic(String dir, String path) {
        // 读取文件信息
        File file = new File(path);
        // 判断文件是否存在
        if (!file.exists())
            throw new RuntimeException("音乐文件不存在");

        // 定义表单尾部数据
        String tail = "\r\n-----------------------------74266228016131691941175105308--";

        // 生成guid
        String guid = guid();
        // 获取目录的PZ值
        String pz = getPz(selectDir(dir));
        // 获取文件名
        String fileName = file.getName();
        // 获取当前时间
        String date = new Date().toLocaleString().replaceAll("-", "/");
        // 获取文件大小
        String size = String.valueOf(file.length());

        /*System.out.println("guid：" + guid);
        System.out.println("pz：" + pz);
        System.out.println("fileName：" + fileName);
        System.out.println("date：" + date);
        System.out.println("size：" + size);*/

        // 设置表单请求数据
        post_up = post_up
                .replaceAll("\\[guid]", guid)
                .replaceAll("\\[pz]", pz)
                .replaceAll("\\[文件名]", fileName)
                .replaceAll("\\[时间]", date)
                .replaceAll("\\[文件大小]", size);

        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("https.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "8888");
        System.setProperty("https.proxyPort", "8888");


        try {
            URL u = new URL("http://ys-d.ys168.com/fileup/js.aspx?zml=&wjm=" + URLEncoder.encode(fileName));
            HttpURLConnection con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("POST");

            // 配置con连接
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=---------------------------74266228016131691941175105308");

            // 设置Referer
            con.setRequestProperty("Referer", "http://zy.ys168.com/f_zy/ck/scjd.html?v=0.2&bbh=1121175");
            // 设置Cookie
            con.setRequestProperty("Cookie", String.valueOf(cookies.get(0)));

            // 开启连接
            con.connect();

            // 获取数据输出流
            DataOutputStream out = new DataOutputStream(con
                    .getOutputStream());

            // 写入参数
            out.write(post_up.getBytes(StandardCharsets.UTF_8));

            // 读取文件流
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            byte[] bytes = new byte[1024];
            int len;

            while (-1 != (len = bis.read(bytes))) {
                // 写入文件数据
                out.write(bytes, 0, len);
            }

            // 写入尾部内容
            out.writeBytes(tail);

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

            ObjectMapper om = new ObjectMapper();
            JsonNode jsonNode = om.readTree(sb.toString());

            if (!jsonNode.get("hasError").asBoolean())
                System.out.println("上传成功");
            else
                System.out.println("上传失败");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // System.out.println(post_up);
    }

    public static String test() {
        // 定义请求的地址
        StringBuilder openUrl = new StringBuilder("f_ht/ajcx/lyd.aspx?cz=lytj&pdgk=1&pdgly=0&pdzd=0&tou=1&yzm=undefined&_dlmc=");
        // 插入协议头
        openUrl.insert(0, HOST);

        // 拼接网站前缀
        openUrl.append(subHostHead(url));

        // 定义请求头
        Map<String, String> map = new HashMap<>();
        map.put("Referer", HOST + "f_ht/ajcx/000ht.html?bbh=1121175");
        map.put("Cookie", cookies.get(0).toString());

        // 定义参数
        StringBuilder content = new StringBuilder();

        try {
            content.append("sm=").append(URLEncoder.encode("Akong", "UTF-8"));
            content.append("&nr=").append(URLEncoder.encode("歪比巴卜", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 使用工具类发送请求
        return HttpConUtil.post(openUrl.toString(), content.toString(), map);
    }

    /**
     * 截取域名前缀
     *
     * @return 返回域名前缀
     */
    public static String subHostHead(String url) {
        // 定义截取的正则表达式
        String pat = "(?<=http://|https://)(.*?)(?=.ys168.com|.ysepan.com)";
        Pattern pattern = Pattern.compile(pat);
        Matcher matcher = pattern.matcher(url);

        // 进行匹配
        if (matcher.find()) {
            // 返回匹配结果
            return matcher.group();
        }

        // 未匹配到前缀（抛出异常）
        throw new RuntimeException("未匹配到网站前缀");
    }

    /**
     * 生成guid
     *
     * @return 返回guid
     */
    private static String guid() {
        StringBuilder a = new StringBuilder(StringUtil._10_to_N(System.currentTimeMillis(), 32));

        for (int d = 0; 5 > d; d++) {
            a.append(StringUtil._10_to_N((long) Math.floor(65535 * Math.random()), 32));
        }

        return a.insert(0, "wu_").toString();
    }

    public static void main(String[] args) throws IOException {

        // System.out.println(YSEPanHelper.subHostHead("http://fanqiewl.ysepan.com/"));
        System.out.println(YSEPanHelper.login());

        System.out.println(test());

        // System.out.println(post_up);

        // System.out.println(guid());

        // YSDir akongMusic = selectDir("AkongMusic");

        // uploadMusic("AkongMusic", "E:\\音乐\\棒棒堂 - 说说.mp3");
    }
}
