package com.akong.music.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Arrays;

/**
 * Music实体类
 *
 * @author Akong
 * @create 2021/11/24 16:52
 */
public class Music {
    // 音乐编号
    private String rid;
    // 音乐图片路径
    private String pic;
    // 音乐名
    private String name;
    // 歌手
    private String artist;
    // 专辑
    private String album;
    // 音乐时长
    private String songTimeMinutes;
    // 音质
    private String[] coopFormats;
    // 歌曲链接
    private String musicUrl;
    // 判断是否为存储于数据库内的音乐
    private boolean localMusic = true;

    public boolean isLocalMusic() {
        return localMusic;
    }

    public void setLocalMusic(boolean localMusic) {
        this.localMusic = localMusic;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name.replaceAll("&nbsp;", " ");
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist.replaceAll("&nbsp;", " ");
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album.replaceAll("&nbsp;", " ");
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getSongTimeMinutes() {
        return songTimeMinutes;
    }

    public void setSongTimeMinutes(String songTimeMinutes) {
        this.songTimeMinutes = songTimeMinutes;
    }

    public String[] getCoopFormats() {
        return coopFormats;
    }

    public void setCoopFormats(String[] coopFormats) {
        this.coopFormats = coopFormats;
    }

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public Music(String rid, String pic, String name, String artist, String album, String songTimeMinutes,
                 String[] coopFormats) {
        this.rid = rid;
        this.pic = pic;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.songTimeMinutes = songTimeMinutes;
        this.coopFormats = coopFormats;
    }

    public Music(String rid, String pic, String name, String artist, String album, String songTimeMinutes) {
        this.rid = rid;
        this.pic = pic;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.songTimeMinutes = songTimeMinutes;
    }

    public Music() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        return "Music{" +
                "rid='" + rid + '\'' +
                ", pic='" + pic + '\'' +
                ", name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", songTimeMinutes='" + songTimeMinutes + '\'' +
                ", coopFormats=" + Arrays.toString(coopFormats) +
                ", localMusic=" + localMusic +
                '}';
    }
}
