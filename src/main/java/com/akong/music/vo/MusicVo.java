package com.akong.music.vo;

import com.akong.music.pojo.Music;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 前端歌曲实体类
 *
 * @author Akong
 * @create 2021/12/22 14:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicVo {
    //音乐集合
    private List<Music> musicList;
    //音乐总数
    private Integer total;
}
