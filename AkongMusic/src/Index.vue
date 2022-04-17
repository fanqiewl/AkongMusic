<template>
  <div id="app">
    <div id="downloadList" aria-labelledby="downloadList" class="modal fade" role="dialog" tabindex="-1">
      <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button aria-label="Close" class="close" data-dismiss="modal" type="button"><span
              aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">下载列表</h4>
          </div>

          <div class="modal-body">
            <div>
              <div v-for="d in this.$data.downloadList"
                   style="padding: 10px 20px;border: 1px #337ab7 solid;border-radius: 10px;margin: 10px;">
                <span style="display: inline-block;width: calc(100% - 115px);text-align: left">{{ d.name }}</span>

                <span
                  style="display: inline-block;width: 100px;text-align: right">{{
                    d.total == 0 ? "加载中" : parseInt((d.loaded / d.total) * 100) + '%'
                  }}</span>

                <div class="progress" style="margin-top: 10px;margin-bottom: 0">
                  <div :class="d.loaded != d.total?'active':''"
                       :style="{width: (d.loaded/d.total)*100+'%'}" aria-valuemax="100" aria-valuemin="0"
                       aria-valuenow="0"
                       class="progress-bar progress-bar-info progress-bar-striped" role="progressbar">
                  </div>
                </div>
              </div>
            </div>
            <div v-if="this.$data.downloadList.length == 0" style="text-align: center">
              <h4>暂时没有下载中的音乐</h4>
            </div>
          </div>

          <div class="modal-footer">
            <button class="btn btn-primary" data-dismiss="modal" type="button">Close</button>
          </div>
        </div>

      </div>
    </div>


    <div class="row" style="margin: 0">
      <div class="col-lg-6 col-sm-8 col-xs-12 col-lg-offset-3 col-sm-offset-2">
        <div class="panel panel-primary" style="margin: 10px auto 10px;">
          <div class="panel-heading">
            <div class="row" style="line-height: 34px">
              <div class="col-lg-6 col-sm-4 col-xs-4" style="text-align: left;">
                <h3 class="panel-title" style="display: inline-block">Akong Music</h3>
              </div>
              <div class="col-lg-6 col-sm-8 col-xs-8">
                <div class="input-group">
                  <input id="musicName" class="form-control" placeholder="请输入关键词..."
                         style="border-radius: 20px 0 0 20px" @keyup.enter="selClick">
                  <span class="input-group-btn">
                                        <button class="btn btn-default" style="border-radius: 0 20px 20px 0"
                                                type="button" @click="selClick">
                                            <span class="glyphicon glyphicon-search"></span>
                                        </button>
                                    </span>
                </div>
              </div>
            </div>
          </div>
          <aplayer v-if="this.play.playList.length !== 0"
                   :float="true"
                   :list="play.playList"
                   :listFolded="true"
                   :music="play.current_play"
                   :shuffle="true"
                   autoplay
                   repeat="repeat-all"
                   style="padding: 0;z-index: 99"
          />
          <ul id="musicList" class="list-group" style="text-align: left">
            <li v-for="m in list" class="list-group-item">
              <div class="dropdown">
                <div :id="m.rid" aria-expanded="true" aria-haspopup="true" class="dropdown-toggle"
                     data-toggle="dropdown" style="display: inline-block;width: calc(100% - 100px);cursor:pointer;">
                  {{ m.name }}<br>
                  <span style="color: #007bff; ">{{ m.songTimeMinutes }}</span> - <span
                  style="color: #007bff; ">{{ m.artist }}</span>
                </div>
                <div class="pull-right" style="display: inline-block">
                  <ul style="list-style: none">
                    <li v-if="m.coopFormats.includes('flac')"><span
                      style="border: 2px orange solid;border-radius: 5px;padding: 0px 4px;color: orange;font-size: 11px;font-weight: bold">SQ</span>
                    </li>
                    <li v-if="m.coopFormats.includes('320kmp3')"><span
                      style="border: 2px lawngreen solid;border-radius: 5px;padding: 0px 4px;color: lawngreen;font-size: 11px;font-weight: bold">HQ</span>
                    </li>
                  </ul>
                </div>
                <ul :id="m.rid+'menu'" :aria-labelledby="m.rid" class="dropdown-menu animated fadeInDown"
                    data-animation="fadeInDown">
                  <li class="dropdown-header">在线操作</li>
                  <li style="height: 25px"><a href="javascript:void(0)" @click.self="playMusicOn(m)">播放</a></li>
                  <li style="height: 25px"><a href="javascript:void(0)" @click.self="playMusicOn(m, true)">添加到播放列表</a>
                  </li>
                  <li class="dropdown-header">点击下载</li>

                  <li v-if="m.coopFormats.includes('128kmp3')" style="height: 30px"
                      @click="downloadMusic(m, '128kmp3')">
                    <a href="javascript:void(0)">低品
                      <span class="pull-right"
                            style="border: 2px pink solid;border-radius: 5px;padding: 0px 4px;color: pink;font-size: 11px;font-weight: bold">mp3</span>
                    </a>
                  </li>
                  <li v-if="m.coopFormats.includes('192kmp3')" style="height: 30px"
                      @click="downloadMusic(m, '192kmp3')"><a href="javascript:void(0)">标准
                    <span class="pull-right"
                          style="border: 2px #2a69ff solid;border-radius: 5px;padding: 0px 4px;color: #2a69ff;font-size: 11px;font-weight: bold">mp3</span>
                  </a></li>
                  <li v-if="m.coopFormats.includes('320kmp3')" style="height: 30px"
                      @click="downloadMusic(m, '320kmp3')"><a href="javascript:void(0)">高品
                    <span class="pull-right"
                          style="border: 2px lawngreen solid;border-radius: 5px;padding: 0px 4px;color: lawngreen;font-size: 11px;font-weight: bold">mp3</span>
                  </a></li>
                  <li v-if="m.coopFormats.includes('flac')" style="height: 30px" @click="downloadMusic(m, 'flac')"><a
                    href="javascript:void(0)">无损
                    <span class="pull-right"
                          style="border: 2px orange solid;border-radius: 5px;padding: 0px 4px;color: orange;font-size: 11px;font-weight: bold">flac</span>
                  </a></li>
                </ul>
              </div>
            </li>

            <li v-if="list.length == 0" class="list-group-item">
              <div style="text-align: center;padding: 20px;font-size: 18px">
                暂无数据
              </div>
            </li>

          </ul>

          <div class="panel-footer">
            <div style="text-align: center">
              <div class="myPagination"></div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="htmleaf-container">

      <div id='ss_menu' style="z-index: 999">
        <div
          class="menu_ball_item"
          onClick="window.open('https://qm.qq.com/cgi-bin/qm/qr?k=ZOByEGkqBbZo_koDp1w7pJU5hXCuS2DJ&'+'jump_from'+'=webapi')">
          <i class="fa fa-qq"></i>
          <span style="display: table-footer-group;font-size: 10px;">QQ群</span>
        </div>
        <div class="menu_ball_item" data-target="#downloadList" data-toggle="modal">
          <i class="glyphicon glyphicon-save"></i>
          <span style="display: table-footer-group;font-size: 10px;">下载</span>
        </div>
        <div class="menu_ball_item" onClick="window.open('https://support.qq.com/product/353158')">
          <i class="glyphicon glyphicon-edit"></i>
          <span style="display: table-footer-group;font-size: 10px;">反馈</span>
        </div>
        <div class="menu_ball_item" @click="changeEngine">
          <i class="glyphicon glyphicon-refresh"></i>
          <span style="display: table-footer-group;font-size: 10px;">切换引擎</span>
        </div>
        <div class='menu'>
          <div id='ss_toggle' class='share' data-rot='180'>
            <div class='circle'></div>
            <div class='bar'></div>
            <span id="menu_ball_text" style="display: block;font-size: 10px;line-height: 106px">菜单</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import A_Player from 'vue-aplayer';

export default {
  name: 'Index',
  data: () => {
    return {
      test: 0,
      play: {
        current_play: {},
        playList: []
      },
      list: [{
        name: "正在加载中...",
        songTimeMinutes: null,
        artist: null,
        coopFormats: []
      }],
      toast: {
        title: "提示",
        text: "内容"
      },
      musicURL: "",
      selData: {
        musicName: "热门歌曲",
        rows: 10,
        page: 1,
        total: 0
      },
      downloadList: [],
      Engine: 1
    }
  },
  methods: {
    selMusic(musicName, page) {
      // this.$data.selData.musicName = musicName;
      this.$data.selData.musicName = musicName || this.$data.selData.musicName;
      if (musicName == "")
        this.$data.selData.musicName = musicName;
      this.$data.selData.page = page || this.$data.selData.page;

      let selData = this.$data.selData;

      // 开启遮罩
      $("#loadingModal").modal('show');

      // 判断搜索引擎
      if (this.$data.Engine == 1) {
        // 酷我引擎
        this.axios.post(this.axios['urls'].SELECT_MUSIC, {
          musicName: selData.musicName,
          rows: selData.rows,
          page: selData.page
        }).then(result => {
          // 设置音乐列表
          this.$data.list = result.data['musicList'];
          // 设置总数
          selData.total = result.data.total;

          // 重置分页
          this.pageManager(selData.page, selData.total);

          // 关闭遮罩
          $("#loadingModal").modal('hide');
        }).catch(e => {
          // 关闭遮罩
          $("#loadingModal").modal('hide');
        });
      } else if (this.$data.Engine == 2) {
        // 永硕引擎
        this.axios.post(this.axios['urls'].SELECT_MUSIC_YS168, {
          musicName: selData.musicName,
          rows: selData.rows,
          page: selData.page
        }).then(result => {
          // 设置音乐列表
          this.$data.list = result.data;
          // 设置总数
          selData.total = 10;

          // 重置分页
          this.pageManager(selData.page, selData.total);

          // 关闭遮罩
          $("#loadingModal").modal('hide');
        }).catch(e => {
          // 关闭遮罩
          $("#loadingModal").modal('hide');
        });
      }
    },
    selClick() {
      // 获取关键词
      let musicName = $("#musicName").val();
      // 重置页码
      this.$data.selData.page = 1;

      // 判断若关键词不为空再搜索
      if (musicName) {
        this.selMusic(musicName);
      } else {
        // 弹出提示
        $.jqAlert({
          type: "info",
          content: "请不要使用空值搜索"
        });
      }
    },
    playMusicOn(music, type) {
      // 请求链接
      let url = "";
      // 请求参数
      let params = null;

      // 判断搜索引擎
      if (this.$data.Engine == 1) {
        // 酷我引擎
        // url = "https://music.api.akongwl.top/api/exMusicLink";
        url = this.axios['urls'].EX_MUSIC_LINK;
        params = {rid: music.rid, format: music.coopFormats[music.coopFormats.length - 1]};
      } else if (this.$data.Engine == 2) {
        // 永硕引擎
        // url = "https://music.api.akongwl.top/api/exYs168MusicLink";
        url = this.axios['urls'].EX_MUSIC_LINK_YS168;
        params = {ysid: music.rid + "-" + music.coopFormats[music.coopFormats.length - 1]};
      }

      this.axios.post(url, params).then(result => {
        let musicUrl = this.axios['urls'].getFullPath("PLAY_MUSIC") + "?musicUrl=" + result.data;

        if (!type) {
          this.play.playList.splice(0, this.play.playList.length);
        }

        if (this.play.playList.length === 0) {
          this.play.current_play = {
            title: music.name,
            artist: music.artist,
            src: musicUrl,
            pic: music.pic
          }

          this.play.playList.push(this.play.current_play);
        } else
          this.play.playList.push({
            title: music.name,
            artist: music.artist,
            src: musicUrl,
            pic: music.pic
          });

        // 设置音乐链接
        // this.$data.musicURL = musicUrl;
      });
    },
    changeEngine() {
      //重置页码
      this.$data.selData.page = 1;

      //切换引擎
      if (this.$data.Engine == 1) {
        this.$data.Engine = 2;
        this.selMusic("");
      } else if (this.$data.Engine == 2) {
        this.$data.Engine = 1;
        this.selMusic("热门歌曲");
      }
    },
    downloadMusic(music, coopFormat) {
      //弹出提示
      $.jqAlert({
        type: "info",
        content: "正在下载，请点击右下角菜单查看"
      });

      // 请求链接
      let url = "";
      // 请求参数
      let params = null;

      // 判断搜索引擎
      if (this.$data.Engine == 1) {
        // 酷我引擎
        // url = "https://music.api.akongwl.top/api/exMusicLink";
        url = this.axios['urls'].EX_MUSIC_LINK;
        params = {rid: music.rid, format: coopFormat};
      } else if (this.$data.Engine == 2) {
        // 永硕引擎
        // url = "https://music.api.akongwl.top/api/exYs168MusicLink";
        url = this.axios['urls'].EX_MUSIC_LINK_YS168;
        params = {ysid: music.rid + "-" + coopFormat};
      }

      this.axios.post(url, params).then(result => {
        // 获取音乐链接
        let url = this.axios['urls'].getFullPath("PLAY_MUSIC") + "?musicUrl=" + result.data;
        // 截取文件名
        let fileName = music.name + " - " + music.artist + (coopFormat == "flac" ? ".flac" : ".mp3");

        console.log(fileName);

        // 调用方法下载到本地
        downloadUrlFile(url, fileName);
      });
    },
    // =====================分页============================
    pageManager: function (page, total, groups) {
      let self = this;

      $(".myPagination").Pagination({
        page: page || 1,
        count: total || 1000,
        groups: groups || 3,
        onPageChange: function (page) {
          self.selMusic(undefined, page);
        }
      });
    },
    wuhu() {
      console.log("证明我来过");
    }
  },
  created() {
    this.selMusic("热门歌曲");

    // 重置分页
    this.pageManager();
  },
  beforeUpdate() {
    // 获取全局变量
    this.$data.downloadList = window.wv.$data.downloadList;
    this.$data.play.playList = window.wv.$data.playList;
  },
  components: {
    aplayer: A_Player
  }
}

$(function (ev) {
  $('#loadingModal').modal({backdrop: 'static', keyboard: false});

  // =====================菜单球======================

  let toggle = $('#ss_toggle');
  let menu = $('#ss_menu');
  let rot;
  $('#ss_toggle').on('click', function (ev) {
    rot = parseInt($(this).data('rot')) - 180;
    menu.css('transform', 'rotate(' + rot + 'deg)');
    menu.css('webkitTransform', 'rotate(' + rot + 'deg)');
    if (rot / 180 % 2 == 0) {
      toggle.parent().addClass('ss_active');
      toggle.addClass('close');
    } else {
      toggle.parent().removeClass('ss_active');
      toggle.removeClass('close');
    }
    $(this).data('rot', rot);

    return false;
  });

  $(".menu_ball_item").on('click', function (ev) {
    rot = parseInt(toggle.data('rot')) - 180;
    menu.css('transform', 'rotate(' + rot + 'deg)');
    menu.css('webkitTransform', 'rotate(' + rot + 'deg)');
    if (rot / 180 % 2 == 0) {
      toggle.parent().addClass('ss_active');
      toggle.addClass('close');
    } else {
      toggle.parent().removeClass('ss_active');
      toggle.removeClass('close');
    }
    toggle.data('rot', rot);
  });

  $("body").on('click', function (ev) {
    if (rot / 180 % 2 == 0) {
      rot = parseInt(toggle.data('rot')) - 180;
      menu.css('transform', 'rotate(' + rot + 'deg)');
      menu.css('webkitTransform', 'rotate(' + rot + 'deg)');

      toggle.parent().removeClass('ss_active');
      toggle.removeClass('close');

      toggle.data('rot', rot);
    }

  });

  menu.on('transitionend webkitTransitionEnd oTransitionEnd', function () {
    if (rot / 180 % 2 == 0) {
      $('#ss_menu div i').addClass('ss_animate');
      $('#menu_ball_text').css("display", "none");
    } else {
      $('#ss_menu div i').removeClass('ss_animate');
      $('#menu_ball_text').css("display", "block");
    }
  });

});
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
