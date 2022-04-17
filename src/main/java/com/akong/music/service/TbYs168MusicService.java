package com.akong.music.service;

import com.akong.base.util.PageBean;
import com.akong.music.pojo.Music;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Akong
 * @create 2021/12/22 0:17
 */
@Transactional
public interface TbYs168MusicService {
    List<Music> selectMusicByName(Music music, PageBean pageBean);
}
