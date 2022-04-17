package com.akong.music.service.impl;

import com.akong.base.util.PageBean;
import com.akong.music.dao.TbYs168MusicDao;
import com.akong.music.pojo.Music;
import com.akong.music.service.TbYs168MusicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Akong
 * @create 2021/12/22 0:19
 */
@Service(value = "tbYs168MusicService")
public class TbYs168MusicServiceImpl implements TbYs168MusicService {
    @Resource
    private TbYs168MusicDao tbYs168MusicDao;

    @Override
    public List<Music> selectMusicByName(Music music, PageBean pageBean) {
        return tbYs168MusicDao.selectMusicByName(music, pageBean);
    }
}
