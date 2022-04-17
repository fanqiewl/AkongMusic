package com.akong.music.util;

import com.akong.base.util.HttpConUtil;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 永硕E盘提取存储音乐
 *
 * @author Akong
 * @create 2021/11/26 18:18
 */
public class Ys168MusicUtil {
    //列表名
    private static final String MLBH = "1986620";
    //永硕名
    private static final String _DLMC = "fanqiewl";
    //定义Referer
    private static final String REFERER = "https://cb.ys168.com/f_ht/ajcx/000ht.html";

    /**
     * 读取永硕盘中音乐文件夹中的所有内容
     */
    public static String selMusicAll() throws Exception {
        //定义请求链接
        URL url = new URL("http://cb.ys168.com/f_ht/ajcx/wj.aspx?cz=dq&mlbh=" + MLBH + "&_dlmc=" + _DLMC);
        //打开链接
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        //设置Referer
        con.setRequestProperty("Referer", REFERER);

        return HttpConUtil.requestCon(con).toString();
    }

    /**
     * 根据指定编号获取永硕盘中的音乐链接
     */
    public static String selMusicLink(String ysid) {
        // 读取源码
        String str = null;
        try {
            str = selMusicAll();

            //正则表达式
            String pat = "((<a class=\"new\" href=\")|(<a href=\"))(http://ys-[A-Za-z].(ysepan|ys168).com/.*?)(《(\\d+\\-(320kmp3|192kmp3|flac))》)";
            Pattern pattern = Pattern.compile(pat);
            Matcher matcher = pattern.matcher(str);

            while (matcher.find()) {
                if (ysid.equals(matcher.group(7))) // 读取到内容则返回 --> 拼接后缀
                    return matcher.group(4) + ("mp3".equals(ysid.substring(ysid.length() - 3)) ? "temp.mp3" : "temp.flac");
            }

            //判断是否读取到内容
//            if (matcher.find()) {
//                //读取到内容则返回 --> 拼接后缀
//                return matcher.group(4) + ("mp3".equals(ysid.substring(ysid.length() - 3)) ? "temp.mp3" : "temp.flac");
//            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        //否则返回null
        return null;
    }

    public static void main(String[] args) throws Exception {
        String str = selMusicAll();

        System.out.println(str);

        //正则表达式
        String pat = "((<a class=\"new\" href=\")|(<a href=\"))(http://ys-[A-Za-z].(ysepan|ys168).com/.*?)(《(\\d+\\-(320kmp3|192kmp3|flac))》)";
        Pattern pattern = Pattern.compile(pat);
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            System.out.println(matcher.group(4) + "---" + matcher.group(7) + matcher.groupCount());
        }
    }
}
