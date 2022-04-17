create table tb_ys168_music
(
    rid             int auto_increment comment '音乐编号'
        primary key,
    pic             varchar(200) null comment '音乐图片路径
',
    name            varchar(200) not null comment '音乐名
',
    artist          varchar(100) null comment '歌手',
    album           varchar(100) null comment '专辑
',
    songTimeMinutes varchar(10)  null comment '音乐时长',
    coopFormats     varchar(100) null comment '音质
'
)
    comment '永硕盘中的歌曲';

INSERT INTO akong_music.tb_ys168_music (rid, pic, name, artist, album, songTimeMinutes, coopFormats) VALUES (1, null, '我的一个道姑朋友', '双笙', '我的一个道姑朋友', '04:44', 'flac,320kmp3,192kmp3');
INSERT INTO akong_music.tb_ys168_music (rid, pic, name, artist, album, songTimeMinutes, coopFormats) VALUES (2, null, '好想爱这个世界啊', '华晨宇', '新世界NEW WORLD', '04:18', 'flac,320kmp3,192kmp3');
INSERT INTO akong_music.tb_ys168_music (rid, pic, name, artist, album, songTimeMinutes, coopFormats) VALUES (3, null, '撒野', '凯瑟喵', '撒野', '04:08', 'flac,320kmp3,192kmp3');
INSERT INTO akong_music.tb_ys168_music (rid, pic, name, artist, album, songTimeMinutes, coopFormats) VALUES (4, null, '心做し', '双笙', '心做し', '04:29', '320kmp3,192kmp3');
INSERT INTO akong_music.tb_ys168_music (rid, pic, name, artist, album, songTimeMinutes, coopFormats) VALUES (5, null, '所念皆星河', 'CMJ', '所念皆星河', '03:40', 'flac,320kmp3,192kmp3');
INSERT INTO akong_music.tb_ys168_music (rid, pic, name, artist, album, songTimeMinutes, coopFormats) VALUES (6, null, '所念皆星河', '房东的猫', '所念皆星河', '04:04', 'flac,320kmp3,192kmp3');
INSERT INTO akong_music.tb_ys168_music (rid, pic, name, artist, album, songTimeMinutes, coopFormats) VALUES (7, null, '白羊（Cover 徐秉龙）', '石善允', '白羊', '02:48', 'flac,320kmp3,192kmp3');
INSERT INTO akong_music.tb_ys168_music (rid, pic, name, artist, album, songTimeMinutes, coopFormats) VALUES (8, null, '今晚约会吧', 'SORROW', '今晚约会吧', '03:08', 'flac,320kmp3,192kmp3');
INSERT INTO akong_music.tb_ys168_music (rid, pic, name, artist, album, songTimeMinutes, coopFormats) VALUES (1001, null, 'Interlude：Wings', 'BTS (防弹少年团)', 'WINGS', '02:24', 'flac,320kmp3,192kmp3');