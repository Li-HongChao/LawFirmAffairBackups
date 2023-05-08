<template>
  <div style="height: 100%">

    <el-dialog  title="修改用户信息" :visible.sync="alterAdmin" width="50%">
      <el-form ref="form" :model="form" :rules="rules">
        <el-form-item label="姓名" :label-width="formLabelWidth" prop="name">
          <el-input v-model="form.name" autocomplete="off" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item label="密码" :label-width="formLabelWidth" prop="password">
          <el-input v-model="form.password" autocomplete="off" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="电话" :label-width="formLabelWidth" prop="phone">
          <el-input v-model="form.phone" autocomplete="off" placeholder="请输入用户电话"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" :label-width="formLabelWidth" prop="email">
          <el-input v-model="form.email" autocomplete="off" placeholder="请输入邮箱地址"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="alterAdmin = false">取 消</el-button>
        <el-button type="primary" @click="alertAdmins">确 定</el-button>
      </div>
    </el-dialog>

    <div class="header" style="width: 100%;position: fixed; z-index: 666;">
      <ul style="width: 100%; position:relative;">
        <el-dropdown @command="handleCommand">
          <li style="height: 65px; line-height: 65px;padding-left: 20px">
            <img style="width: 35px;height: 35px;margin-right: 20px" src="@/assets/logo.png" alt="">
          </li>
          <li style="font-weight: 550">{{ username }}</li>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="login" v-if="login">马上登录</el-dropdown-item>
            <el-dropdown-item command="user" v-if="user">个人中心</el-dropdown-item>
            <el-dropdown-item command="logout" v-if="user">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>

        <!--导航栏-->
        <div style="position:absolute; top: 0; right: 0; margin-left: 20px;">
          <li class="routers">
            <router-link to="/indexs">首页</router-link>
          </li>
          <li class="routers">
            <router-link to="/lawyerTeams">律师团队</router-link>
          </li>
          <li class="routers">
            <router-link to="/news">法律资讯</router-link>
          </li>
          <li class="routers">
            <router-link to="/beforeUser">我的咨询</router-link>
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
import axios from "axios";

export default {
  name: 'UserPager',
  data() {
    return {
      //用户头像
      circleUrl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
      //用户存在session里的名字
      username: JSON.parse(sessionStorage.getItem('userInfo')),
      id:JSON.parse(sessionStorage.getItem('userId')),
      //判断显示登录还是显示个人中心
      login: false,
      user: false,
      alterAdmin:false,
      rules: {//校验规则
        phone: [{pattern:/^1[3456789]\d{9}$/ ,required: true, message: '请检查格式', trigger: 'blur'}],
        name: [{max:8,required: true, message: '需要汉字哦~', trigger: 'blur'}],
        area: [{required: true, message: '擅长领域为必填项', trigger: 'blur'}],
        password: [{min:6,max:12,required: true, message: '密码为必填项,最短长度为6，最长为12奥~', trigger: 'blur'}],
        rating: [{ max:1,pattern:/^[0-9]*$/,required: true, message: '必填项，最大为9', trigger: 'blur'}],
        feeRate: [{pattern:/^\d+\.?\d{0,2}$/ ,required: true, message: '收费标准为必填项', trigger: 'blur'}],
        email: [{pattern:/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)$/ ,required: true, message: '请检查邮箱格式', trigger: 'blur'}]
      },
      form: {
        id:'',
        name: '',
        password: '',
        phone:'',
        email:''
      },
      formLabelWidth: '120px',
    }
  },
  methods: {
    handleCommand(command) {
      if (command==="logout"){
        this.$router.push("/")
      }else if (command===this.username){
        this.alterAdmin=false
        this.handleEdit(this.username)
      }else if(command==="login"){
        this.$router.push("/")
      }
    },
    //修改
    handleEdit(name) {
      this.alterAdmin = true
      axios.get("/select/user?name=" + name).then(((res) => {
        if (res.data.code === 1) {
          //修改第一个调用的先是查询
          this.form = res.data.data
        } else {
          this.$message.error(res.data.msg)
        }
      }))
    },
    alertAdmins() {
      if (!this.checkForm()) {
        this.$message.error('请完善表单相关信息！');
      } else {
        axios.put("/update/user", this.form).then(res => {
          if (res.data.code == 1) {
            this.$message.success("修改成功！");
          } else {
            alert("出现错误" + res.data.msg);
          }
        }).finally(() => {
          this.getAll()
          this.alterAdmin = false
        });
      }
    }
  },
  created() {
    this.name=JSON.parse(sessionStorage.getItem('userInfo'))
      if (this.name){
        this.user=true
      }else {
        this.login=true
      }
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