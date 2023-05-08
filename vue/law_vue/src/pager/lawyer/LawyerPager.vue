<template>
  <div style="height: 100%">

    <div class="header" style="width: 100%;position: fixed; z-index: 666;">
      <ul style="width: 100%; position:relative;">
        <el-dropdown @command="handleCommand">
          <li style="height: 65px; line-height: 65px;padding-left: 20px">
            <img style="width: 35px;height: 35px;margin-right: 20px" src="@/assets/logo.png" alt="">
          </li>
          <li style="font-weight: 550">{{ username }}</li>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="logout" >退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>

        <!--导航栏-->
        <div style="position:absolute; top: 0; right: 0; margin-left: 20px;">
          <li class="routers">
            <router-link to="/lawyerIndex">首页</router-link>
          </li>
          <li class="routers">
            <router-link to="/lawyerLegals">新增资讯</router-link>
          </li>
          <li class="routers">
            <router-link to="/before">我的咨询</router-link>
          </li>
          <li class="routers">
            <router-link to="">关于我们</router-link>
          </li>
        </div>
      </ul>
    </div>


    <div class="main" style="padding-top: 5%;">
      <router-view></router-view>
    </div>

  </div>
</template>

<script>

export default {
  name: "LawyerPager",
  data() {
    return {
      //用户头像
      circleUrl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
      //用户存在session里的名字
      username: JSON.parse(sessionStorage.getItem('userInfo')),
      id: JSON.parse(sessionStorage.getItem('userId')),
    }
  },
  methods: {
    handleCommand() {
        this.$router.push("/")
    },
  }
}
</script>

<style scoped>
.header {
  background: #fcfcfc;
  position: relative;
  height: 65px;
  box-shadow: 0 1px 10px rgba(0, 0, 0, 0.5)
}

.header li {
  display: inline-block;
}

.routers {
  line-height: 65px;
  padding: 0 16px;
  font-weight: 550;
}

.routers:hover {
  transform: perspective(0px) scale(1.2, 1.2);
  transition: all 0.2s;
  cursor: pointer;
}

.main {
  background: #f5f5f5;
  height: 100%;
  width: 100%;
}
</style>