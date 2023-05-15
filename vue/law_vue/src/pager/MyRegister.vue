<template>
  <div class="box" v-loading="loading" element-loading-text="处理中……">
    <el-row type="flex" justify="center">
      <el-form ref="form" :model="user" status-icon label-width="80px" :rules="rules">
        <h3>注册</h3>
        <hr>
        <el-form-item prop="name" label="用户名">
          <el-input v-model="user.name" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item prop="phone" label="电话号码">
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
        <el-link style="width:40%; display: inline-block;margin-top: 40px;margin-left: 200px" @click="back">
          已有密码，返回登录
        </el-link>
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
      rules: {//校验规则
        phone: [{pattern: /^1[3456789]\d{9}$/, required: true, message: '请检查格式', trigger: 'blur'}],
        name: [{max: 8, required: true, message: '需要汉字哦~', trigger: 'blur'}],
        area: [{required: true, message: '擅长领域为必填项', trigger: 'blur'}],
        password: [{min: 6, max: 12, required: true, message: '密码为必填项,最短长度为6，最长为12奥~', trigger: 'blur'}],
        rating: [{max: 1, pattern: /^[0-9]*$/, required: true, message: '必填项，最大为9', trigger: 'blur'}],
        feeRate: [{pattern: /^\d+\.?\d{0,2}$/, required: true, message: '收费标准为必填项', trigger: 'blur'}],
        email: [{
          pattern: /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)$/,
          required: true,
          message: '请检查邮箱格式',
          trigger: 'blur'
        }]
      },
      loading: false
    };
  },
  created() {
  },
  methods: {
    back() {
      this.$router.push('/')
    },
    doRegister() {
      if (!this.checkForm()) {
        this.$message.error('请完善表单相关信息！');
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
    },
    lawyerRegister() {
      this.$router.push("/registerLawyer")
    },
    checkForm() {
      // 1.校验必填项
      let validForm = false;
      this.$refs.form.validate(valid => {
        validForm = valid
      })
      return validForm;
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