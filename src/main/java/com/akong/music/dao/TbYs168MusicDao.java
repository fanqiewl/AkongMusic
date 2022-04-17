package com.akong.music.dao;

import com.akong.base.util.PageBean;
import com.akong.music.pojo.Music;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TbYs168MusicDao {
    List<Music> selectMusicByName(@Param(value = "music") Music music, @Param(value = "pageBean") PageBean pageBean);
}