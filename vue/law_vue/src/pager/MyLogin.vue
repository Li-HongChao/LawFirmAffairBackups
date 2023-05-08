<template>
  <div class="box"  v-loading="loading" element-loading-text="登录中……">
    <div class="titles">**事务所咨询系统</div>

    <!--绑定表单-->
    <el-form ref="loginForm" :model="loginForm" status-icon label-width="60px" style="margin-top: 40px">
      <el-form-item prop="用户名" label="用户名">
        <el-input v-model="loginForm.username" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item prop="password" label="密码">
        <el-input type="password" v-model="loginForm.password" show-password placeholder="请输入密码"></el-input>
      </el-form-item>

      <!--按钮及事件触发-->
      <div>
        <el-form-item style="margin-top: 50px;">
          <el-button :loading="loading" class="login-btn" size="medium" type="primary" style="width:65%;" @click.native.prevent="login">
            <span v-if="!loading">登录</span>
            <span v-else>登录中...</span>
          </el-button>
          <el-button style="width: 30%; " @click="register" :plain="true">注册</el-button>

        </el-form-item>
      </div>

    </el-form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "MyLogin",
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      loading: false
    }
  },
  methods: {
    login() {
      this.loading=!this.loading
      axios.post("/login", this.loginForm).then((res) => {
        if (res.data.code === 1) {
          const type = res.data.data.category
          if (type==1){
            sessionStorage.setItem('userInfo', JSON.stringify(res.data.data.username))
            sessionStorage.setItem('userId', JSON.stringify(res.data.data.id))
            this.$router.push('/admin')
          }else if (type==2){
            sessionStorage.setItem('userInfo', JSON.stringify(res.data.data.username))
            sessionStorage.setItem('userId', JSON.stringify(res.data.data.id))
            this.$message.success("欢迎,"+res.data.data.username+"律师")
            this.$router.push('/lawyer')
          }else {
            sessionStorage.setItem('userInfo', JSON.stringify(res.data.data.username))
            sessionStorage.setItem('userId', JSON.stringify(res.data.data.id))
            this.$router.push('/user')
            this.$message.success("欢迎,用户:"+res.data.data.username)
          }
        } else if (res.data.code === 0) {
          this.$message.error(res.data.msg)
        }
      }).finally(() => {
        this.loading=!this.loading
      })
    },
    register() {
      this.$router.push('/register')
    },
    forget(){
      this.$message('功能未完善');
    }
  }

}
</script>

<style scoped>
body::-webkit-scrollbar {
  display: none
}

.box {
  width: 400px;
  height: 350px;
  position: absolute;
  left: 0;
  top: 0;
  right: 0;
  bottom: 0;
  margin: auto auto;
  border-radius: 10px;
  padding: 20px;
  font-weight: 800;
  font-size: 15px;
  transform: translateZ(0);
  /* 毛玻璃效果 */
  backdrop-filter: blur(20px);
  /* 加深颜色，方便看清文字 */
  background-color: rgba(181, 168, 168, 0.1);
  /* 阴影，偏移0，大小1，模糊值，颜色-透明度 */
  box-shadow: 0 1px 10px rgba(106, 81, 81, 0.5)
}

.titles {
  text-align: center;
  font-size: 20px;
}
</style>