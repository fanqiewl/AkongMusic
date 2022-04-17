<template>
  <div :class="windowWidth > windowHeight ? 'login-back' : 'login-back-phone'">
    <div class="row my-login">
      <div class="col-xs-10 col-xs-offset-1 col-lg-4 col-lg-offset-4">
        <el-card class="my-card" shadow="always">
          <div slot="header" class="clearfix" style="text-align: center;font-size: 20px;font-weight: bold">
            <span>后台管理</span>
          </div>

          <!-- 表单主体 -->
          <el-form :model="loginFrom" label-position="left" label-width="50px">
            <el-form-item label="账号:">
              <el-input v-model="loginFrom.username" clearable placeholder="请输入账号"></el-input>
            </el-form-item>
            <el-form-item label="密码:">
              <el-input v-model="loginFrom.password" clearable placeholder="请输入密码" show-password></el-input>
            </el-form-item>
          </el-form>

          <!-- 操作按钮 -->
          <div>
            <el-button class="btn-block" round type="success" @click="login">登录</el-button>
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      windowWidth: document.documentElement.clientWidth,  //实时屏幕宽度
      windowHeight: document.documentElement.clientHeight,   //实时屏幕高度
      loginFrom: {
        username: null,
        password: null
      }
    }
  },
  methods: {
    login() {
      // 伪登录
      if ('admin' === this.loginFrom.username && 'admin' === this.loginFrom.password) {
        console.log(this.loginFrom);
        localStorage.setItem("admin", "true");
        this.$message.success('登陆成功');
        // 跳转页面登录成功
        window.location.href = "/admin";
      } else {
        this.$message.error('账号或密码错误');
      }
    }
  },
  mounted() {
    let that = this;
    // <!--把window.onresize事件挂在到mounted函数上-->
    window.onresize = () => {
      return (() => {
        window.fullHeight = document.documentElement.clientHeight;
        window.fullWidth = document.documentElement.clientWidth;
        that.windowHeight = window.fullHeight;  // 高
        that.windowWidth = window.fullWidth; // 宽
      })()
    };
  }
}
</script>

<style>
.my-login {
  position: absolute;
  width: 100%;
  opacity: 90%;
  top: 25%;

  margin: 0;
}

.my-card {
  box-shadow: 0 0 10px gray !important;
  border-radius: 10px;
  padding: 10px;
}

.login-back {
  height: 100%;
  background-repeat: no-repeat;
  background-size: 100%;
  background-image: url("/static/imgs/back01.png");
}

.login-back-phone {
  height: 100%;
  background-repeat: no-repeat;
  background-size: auto 100%;
  background-image: url("/static/imgs/back01.png");
}
</style>
