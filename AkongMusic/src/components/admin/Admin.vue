<template>
  <el-container style="height: 100%">
    <el-aside :width="isCollapse?'64px':'200px'" style="height: 100%">
      <!-- 左侧 -->
      <el-menu :collapse="isCollapse" :collapse-transition="false" :router="true"
               class="el-menu-vertical-demo"
               default-active="1"
               style="height: 100%">
        <el-menu-item class="menu-header" index="/admin" @click="$router.go(0)">
          <i class="el-icon-s-platform" style="font-size: 30px;font-weight: bold;color: black"></i>
          <span slot="title">后台管理中心</span>
        </el-menu-item>
        <el-submenu index="1">
          <template slot="title">
            <i class="el-icon-headset"></i>
            <span slot="title">音乐管理</span>
          </template>
          <el-menu-item index="/admin/music">选项1</el-menu-item>
          <el-menu-item index="1-2">选项2</el-menu-item>
        </el-submenu>
      </el-menu>
    </el-aside>
    <el-main style="padding: 0">
      <el-container style="height: 100%">
        <!-- 主内容 -->
        <el-header class="nav-header">
          <!-- 头部 -->
          <el-button :icon="isCollapse?'el-icon-s-unfold':'el-icon-s-fold'" class="icon-button" type="text"
                     @click="isCollapse = !isCollapse">菜单
          </el-button>
        </el-header>

        <el-main>
          <router-view/>
        </el-main>
      </el-container>
    </el-main>
  </el-container>
</template>

<script>

export default {
  name: "Admin",
  data() {
    return {
      isCollapse: false
    }
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    }
  },
  watch: {
    isCollapse() {
      console.log($(".el-aside").css("width"));
      $(".el-aside").css("width", "100px");
      console.log($(".el-aside").css("width"));
    }
  },
  beforeCreate() {
    // 判断如果没有登录则去login
    if (!localStorage.getItem("admin"))
      window.location.href = "/admin/login";
  },
  created() {

  }
}
</script>

<style>
.menu-header {
  font-size: 20px;
  font-weight: bold;
}

.menu-header:hover {
  background-color: white;
}

.nav-header {
  height: 50px !important;
  line-height: 50px;
  box-shadow: rgba(0, 0, 0, 0.05) 0 2px 2px, rgba(0, 0, 0, 0.05) 0 1px 0;
}

.icon-button {
  font-size: 16px;
  color: black;
}

.icon-button:hover {
  color: black;
}

.icon-button:focus {
  color: black;
}
</style>
