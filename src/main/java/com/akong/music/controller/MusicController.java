package com.akong.music.controller;

import com.akong.base.util.PageBean;
import com.akong.base.util.StringUtil;
import com.akong.music.pojo.Music;
import com.akong.music.service.TbYs168MusicService;
import com.akong.music.util.KwMusicUtil;
import com.akong.music.util.Ys168MusicUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 音乐控制器
 *
 * @author Akong
 * @create 2021/11/25 8:25
 */
@Controller
@RequestMapping("/api")
public class MusicController {
    private final ObjectMapper om = new ObjectMapper();

    @Resource
    private TbYs168MusicService tbYs168MusicService;

    /**
     * 搜索歌曲 --> 酷我爬取
     */
    @RequestMapping(value = "/selMusic")
    public void selMusic(String musicName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //创建分页对象
        PageBean pageBean = new PageBean();
        //传递数据
        pageBean.setRequest(request);

        //向页面传递数据
        om.writeValue(response.getOutputStream(), KwMusicUtil.selSong(musicName, pageBean));
    }

    /**
     * 提取音乐链接 --> 酷我爬取
     */
    @RequestMapping(value = "/exMusicLink")
    public void exMusicLink(Music music, String format, HttpServletResponse response) throws IOException {
        //获取歌曲链接
        String s = KwMusicUtil.playSong(music, format);
        //判断是否获取成功
        if (StringUtil.isNotBlank(s))
            //获取成功返回链接
            om.writeValue(response.getOutputStream(), s);

        //否则返回失败提示
        om.writeValue(response.getOutputStream(), 404);
    }

    /**
     * 读取音乐转为流发送到前端
     */
    @RequestMapping("/playMusic")
    public void playMusic(String musicUrl, HttpServletResponse response) {
        try {
            //创建连接对象
            URL url = new URL(musicUrl);
            URLConnection conn = url.openConnection();
            //设置超时
            conn.setConnectTimeout(1000);
            conn.setReadTimeout(5000);

            //发起连接
            conn.connect();
            //获取流
            InputStream inputStream = conn.getInputStream();

            //获取Range长度
            String length = conn.getHeaderField("Content-Length");

            //设置响应头
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Content-Length", length);

            response.setHeader("Content-Type", "audio/mpeg;charset=utf-8");

            //流转换
            IOUtils.copy(inputStream, response.getOutputStream());

            response.flushBuffer();

            //关闭流
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 搜索音乐 --> 永硕盘（数据库）获取
     */
    @RequestMapping("/selMusic_ys168")
    public void selMusic_ys168(String musicName, Music music, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 设置查询的音乐名
        music.setName(musicName);

        // 创建分页实体
        PageBean pageBean = new PageBean();
        // 设置其参数
        pageBean.setRequest(request);

        // 转换为JSON数据向前端发送
        om.writeValue(response.getOutputStream(), tbYs168MusicService.selectMusicByName(music, pageBean));
    }

    /**
     * 读取音乐链接 --> 永硕盘获取
     */
    @RequestMapping("/exYs168MusicLink")
    public void exYs168MusicLink(String ysid, HttpServletResponse response) throws IOException {
        String s = Ys168MusicUtil.selMusicLink(ysid);
        om.writeValue(response.getOutputStream(), s != null ? s : false);
    }

    /**
     * 每日推荐一曲
     */
    @ResponseBody
    @RequestMapping("/toDayMusic")
    public Music toDayMusic() throws Exception {
        return KwMusicUtil.recommendedDaily();
    }

}
