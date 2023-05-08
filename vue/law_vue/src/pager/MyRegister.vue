<template>
  <div class="box" v-loading="loading" element-loading-text="处理中……">
    <el-row type="flex" justify="center">
      <el-form ref="loginForm" :model="user" status-icon label-width="80px">
        <h3>注册</h3>
        <hr>
        <el-form-item prop="username" label="用户名">
          <el-input v-model="user.name" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item prop="email" label="电话号码">
          <el-input v-model="user.phone" placeholder="请输入电话"></el-input>
        </el-form-item>
        <el-form-item prop="email" label="邮箱">
          <el-input v-model="user.email" placeholder="请输入邮箱"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="设置密码">
          <el-input v-model="user.password" show-password placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="doRegister">注册账号</el-button>
          <el-button type="primary" @click="lawyerRegister">我是律师</el-button>
        </el-form-item>
        <el-link   style="width:40%; display: inline-block;margin-top: 40px;margin-left: 200px"  @click="back">已有密码，返回登录</el-link>
      </el-form>

    </el-row>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "MyRegister",
  data() {
    return {
      user: {
        name: "",
        phone: "",
        email: "",
        password: ""
      },
      loading: false
    };
  },
  created() {
  },
  methods: {
    back(){
      this.$router.push('/')
    },
    doRegister() {
      if (!this.user.name) {
        this.$message.error("请输入用户名！");
      } else if (!this.user.email) {
        this.$message.error("请输入邮箱！");
      } else {
        {
          var reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
          if (!reg.test(this.user.email)) {
            this.$message.error("请输入有效的邮箱！");
          } else if (!this.user.password) {
            this.$message.error("请输入密码！");
          } else {
            this.loading = true
            axios.post("/register/user", this.user).then(res => {
              if (res.data.code === 1) {
                this.$message.success("注册成功！");
                this.$router.push({path: "/"});
              } else {
                alert("您输入的用户名已存在！");
              }
            }).finally(() => {
              this.loading = false
            });
          }
        }
      }
    },
    lawyerRegister(){
      this.$router.push("/registerLawyer")
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.box {
  width: 400px;
  height: 450px;
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
</style>