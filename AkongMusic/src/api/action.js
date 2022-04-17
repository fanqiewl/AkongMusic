export default {
  // 'SERVER': 'https://music.api.akongwl.top/api',
  'SERVER': 'http://localhost:8080/AkongMusic/api',

  // 酷我引擎搜索音乐
  'SELECT_MUSIC': '/selMusic',
  // 永硕E盘搜索音乐
  'SELECT_MUSIC_YS168': '/selMusic_ys168',

  // 酷我引擎提取音乐链接
  'EX_MUSIC_LINK': '/exMusicLink',
  // 永硕E盘提取音乐链接
  'EX_MUSIC_LINK_YS168': '/exYs168MusicLink',

  // 播放音乐
  'PLAY_MUSIC': '/playMusic',

  // 后台登录
  'ADMIN_LOGIN': '/admin',

  // 获取总路径
  'getFullPath': k => (wv.axios.urls.SERVER + wv.axios.urls[k])
}
