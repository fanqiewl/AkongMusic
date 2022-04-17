package com.akong.music.util;

import com.akong.base.util.HttpConUtil;
import com.akong.base.util.PageBean;
import com.akong.music.pojo.Music;
import com.akong.music.vo.MusicVo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 酷我音乐帮助工具类
 *
 * @author Akong
 */
public class KwMusicUtil {
    //JACKSON字符串转对象工具
    private static final ObjectMapper om = new ObjectMapper();
    // 固定值
    private static final String VALUE_ONE = "URP5PLM6RZJ";

    /**
     * 搜索音乐获取JSON字符串
     *
     * @param str      搜索的关键字
     * @param pageBean 分页对象
     * @return 返回一个JSON字符串
     */
    private static String selSongForJSON(String str, PageBean pageBean) throws Exception {
        // 将搜索的关键字进行编码
        str = URLEncoder.encode(str, "UTF-8");

        // 请求链接
        String url = "https://www.kuwo.cn/api/www/search/searchMusicBykeyWord?key=" + str
                + "&pn=" + pageBean.getPage() + "&rn=" + pageBean.getRow() + "&httpsStatus=1&reqId=";

        // 请求指向的URL地址
        URL u = new URL(url);
        // 开启连接
        HttpURLConnection con = (HttpURLConnection) u.openConnection();

        // 设置请求头
        con.setRequestProperty("Referer", "https://www.kuwo.cn/search/list?key=" + str);
        con.setRequestProperty("csrf", VALUE_ONE);

        // 设置cookie
        con.setRequestProperty("Cookie", "kw_token=" + VALUE_ONE);

        // 返回JSON字符串
        return HttpConUtil.requestCon(con).toString();
    }

    /**
     * 搜索歌曲
     *
     * @param str 关键字
     * @return 返回存储 歌曲总数 && 歌曲集合的前端实体
     */
    public static MusicVo selSong(String str, PageBean pageBean) {
        // 定义歌曲集合
        ArrayList<Music> am = new ArrayList<>();
        // 定义前端音乐实体
        MusicVo mv = new MusicVo();

        try {
            // 获取JSON字符串
            String jsonString = selSongForJSON(str, pageBean);

            // 将JSON字符串转为对象
            JsonNode job = om.readTree(jsonString);

            // 提取状态码判断提取失败则抛出异常
            if (job.get("code").asInt() != 200) {
                throw new RuntimeException("歌曲读取失败");
            }
            // 提取歌曲总数
            Integer total = job.get("data").get("total").asInt();
            mv.setTotal(total);

            // 提取歌曲集合
            JsonNode ja = job.get("data").get("list");

            // 遍历整个集合提取信息
            for (int i = 0; i < ja.size(); i++) {
                // 单个数据
                JsonNode obj = ja.get(i);
                // 生成歌曲实体
                Music music = new Music(obj.get("rid").asText(), obj.get("pic").asText(), obj.get("name").asText(), obj.get("artist").asText(),
                        obj.get("album").asText(), obj.get("songTimeMinutes").asText());

                // 设置歌曲为网络爬取类型
                music.setLocalMusic(false);

                //获取歌曲的音质
                boolean flag = KwMusicUtil.setCoopFormats(music);

                //判断是否成功获取到歌曲品质
                if (flag)
                    // 将歌曲加入到预先准备好的集合中
                    am.add(music);
            }
        } catch (UnknownHostException e) {
            System.err.println("无网络，请检查网络");
        } catch (SocketTimeoutException e) {
            System.err.println("连接超时，网络状态不佳");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 将歌曲集合设置到前端实体
        mv.setMusicList(am);

        // 将前端实体返回出去
        return mv;
    }

    /**
     * 获取音乐可播放的品质 ==》 并设置给指定歌曲对象
     *
     * @param m 传入指定歌曲对象
     * @return 返回读取的状态
     */
    public static boolean setCoopFormats(Music m) {
        try {
            // 请求指向的URL地址
            URL u = new URL(
                    "https://m.kuwo.cn/newh5/singles/songinfoandlrc?musicId=" + m.getRid() + "&httpsStatus=1&reqId=");
            // 开启连接
            HttpURLConnection con = (HttpURLConnection) u.openConnection();

            //接收JSON字符串
            String jsonString = HttpConUtil.requestCon(con).toString();

            // 将响应字符串转为对象
            JsonNode job = om.readTree(jsonString);

            // 判断若音乐未查询成功则抛出异常
            if (job.get("status").asInt() != 200) {
                throw new RuntimeException("网络异常：请重试");
            }

            //提取音乐歌词
            JsonNode lyricList = om.readTree(jsonString).get("data").get("lrc" + "list");

            // 提取音乐的品质
            List<String> strings = om.readValue(job.get("data").get("song" + "info").get("coopFormats").toString(), new TypeReference<List<String>>() {
            });

            // 将音乐品质转为数组的形式设置到对象
            m.setCoopFormats(strings.toArray(new String[strings.size()]));

            // 返回true代表成功
            return true;
        } catch (UnknownHostException e) {
            System.err.println("无网络，请检查网络");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 出现异常返回false
        return false;
    }

    /**
     * 根据音乐品质获取指定音乐
     * <p>
     * URL u = new URL("https://www.kuwo.cn/url?format=mp3&rid=" + m.getRid()
     * + "&response=url&type=convert_url3&br=" + format + "&from=web&httpsStatus=1");
     *
     * @param m      指定音乐对象
     * @param format 品质
     * @return 返回查询得到的音乐链接
     */
    public static String playSong(Music m, String format) {
        try {
            // 请求指向的URL地址
            URL u = new URL("https://antiserver.kuwo.cn/anti.s?format=mp3|mp3&rid=MUSIC_" + m.getRid() + "&type=convert_url&response=res&br=" + format);

            // 开启连接
            HttpURLConnection con = (HttpURLConnection) u.openConnection();

            // 将响应字符串转为对象
            JsonNode job = om.readTree(HttpConUtil.requestCon(con).toString().replaceAll("\'", "\\\""));

            // 判断若音乐未查询成功则抛出异常
            if (job.get("code").asInt() != 200) {
                throw new RuntimeException("网络异常：请重试");
            }

            // 返回音乐的链接
            return job.get("url").asText();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 出现异常返回null
        return null;
    }

    /**
     * 显示所有歌手
     *
     * @param map 参数集合（category=>类别编号，prefix=>名字首字母，pageIndex=>当前显示页码）
     * @return 返回得到的JSON字符串
     * @throws Exception 抛出异常
     */
    public static String showSingers(Map<String, Object> map) throws Exception {
        //定义链接
        String urlString = "https://www.kuwo.cn/api/www/artist/artistInfo?";

        //添加类别
        urlString += "category=" + Integer.valueOf(map.get("category") + "");
        //名字首字母
        urlString += "&prefix=" + map.get("prefix").toString();
        //当前页码
        urlString += "&pn=" + Integer.valueOf(map.get("pageIndex") + "");
        //显示数量
        urlString += "&rn=30";

        //创建URL
        URL url = new URL(urlString);

        //开启连接
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        // 设置请求头
        con.setRequestProperty("Referer", "https://www.kuwo.cn/singers");
        con.setRequestProperty("csrf", VALUE_ONE);

        // 设置cookie
        con.setRequestProperty("Cookie", "kw_token=" + VALUE_ONE);

        return HttpConUtil.requestCon(con).toString();
    }

    /**
     * 热门搜索 + 关键字提示
     *
     * @param str 搜索的关键字
     * @return 返回得到的JSON字符串
     */
    public static String hotSearch(String str) throws Exception {
        //定义请求链接
        URL url = new URL("https://www.kuwo.cn/api/www/search/searchKey?key=" + str);
        //开启连接
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        //设置请求头
        con.setRequestProperty("Referer", "https://www.kuwo.cn/");
        con.setRequestProperty("csrf", VALUE_ONE);

        //设置Cookie
        con.setRequestProperty("Cookie", "kw_token=" + VALUE_ONE);

        return HttpConUtil.requestCon(con).toString();
    }

    /**
     * 每日推荐
     * @return  返回得到的音乐实体
     */
    public static Music recommendedDaily() throws Exception {
        // 定义请求链接
        URL url = new URL("https://kuwo.cn/api/www/bang/bang/musicList?bangId=93&pn=1&rn=30&httpsStatus=1");
        // 开启连接
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        // 设置请求头
        con.setRequestProperty("Referer", "https://kuwo.cn/rankList");
        con.setRequestProperty("csrf", VALUE_ONE);

        //设置Cookie
        con.setRequestProperty("Cookie", "kw_token=" + VALUE_ONE);

        // 调用工具获取到JSON
        String s = HttpConUtil.requestCon(con).toString();
        // 将JSON转为实体对象
        JsonNode jsonNode = om.readTree(s);

        // 得到歌曲列表
        JsonNode musicList = jsonNode.get("data").get("musicList");
        // 随机得到其中的一首歌曲
        JsonNode obj = musicList.get((int) (Math.random() * musicList.size()));

        // 生成歌曲实体
        Music music = new Music(obj.get("rid").asText(), obj.get("pic").asText(), obj.get("name").asText(), obj.get("artist").asText(),
                obj.get("album").asText(), obj.get("songTimeMinutes").asText());

        // 获取音质
        if(!setCoopFormats(music))
            return null;

        // 调用播放音乐的方法（得到音乐链接）
        music.setMusicUrl(playSong(music, music.getCoopFormats()[0]));

        // 返回得到的音乐JSON
        return music;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(recommendedDaily());
    }
}