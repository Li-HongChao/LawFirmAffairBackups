<template>
  <div class="box">
    <el-form ref="form" :model="form" :rules="rules" label-position="right"
             label-width="100px">

      <el-row>
        <el-col :span="12">
          <el-form-item label="律师图片" prop="imageUrl">
            <el-upload
                class="avatar-uploader el-icon-camera-solid"
                action="http://localhost:8082/register/upload"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
              <img v-if="imageUrl!=''" :src="imageUrl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon">上传图片</i>
            </el-upload>
          </el-form-item>
        </el-col>
      </el-row>


      <el-row>

        <el-col :span="12">

          <el-form-item label="律师姓名" prop="name">

            <el-input v-model="form.name"/>

          </el-form-item>

        </el-col>

        <el-col :span="12">

          <el-form-item label="律师级别" prop="rating">

            <el-input v-model="form.rating"/>

          </el-form-item>

        </el-col>

        <el-col :span="12">

          <el-form-item label="律师电话" prop="phone">

            <el-input v-model="form.phone"/>

          </el-form-item>

        </el-col>

        <el-col :span="12">

          <el-form-item label="擅长领域" prop="area">

            <el-input v-model="form.area"/>

          </el-form-item>

        </el-col>

        <el-col :span="12">

          <el-form-item label="账户密码" prop="password">

            <el-input v-model="form.password"/>

          </el-form-item>

        </el-col>

        <el-col :span="12">

          <el-form-item label="收费标准" prop="feeRate">

            <el-input v-model="form.feeRate"/>

          </el-form-item>

        </el-col>

        <el-col :span="24">

          <el-form-item label="所在事务所">

            <el-input v-model="form.lawFirm"/>

          </el-form-item>

        </el-col>

        <el-col :span="24">

          <el-form-item label="工作时间">

            <el-input v-model="form.working"/>

          </el-form-item>

        </el-col>

      </el-row>

    </el-form>

    <div slot="footer" class="dialog-footer" align="center">

      <el-button type="primary" @click="addAdmins()">申请审核</el-button>

    </div>

  </div>

</template>

<script>
import axios from "axios";

export default {
  name: "MyLawyerRegister",
  data() {
    return {
      form: {
        name: '',
        phone: '',
        area: '',
        password: '',
        lawFirm: '',
        feeRate: '',
        working: '',
        rating: '',
        imgUrl:"",
        status:""
      },
      rules: {//校验规则
        phone: [{pattern: /^1[3456789]\d{9}$/, required: true, message: '请检查格式', trigger: 'blur'}],
        name: [{max: 8, required: true, message: '最大长度为8！', trigger: 'blur'}],
        area: [{required: true, message: '擅长领域为必填项', trigger: 'blur'}],
        password: [{min: 6, max: 12, required: true, message: '密码为必填项,最短长度为6，最长为12！', trigger: 'blur'}],
        rating: [{pattern: /^[0-9]$/,max:1, required: true, message: '必填项，最大为9', trigger: 'blur'}],
        feeRate: [{pattern: /^\d+\.?\d{0,2}$/, required: true, message: '收费标准为必填项', trigger: 'blur'}]
      },
      imageUrl: '',
    }
  },
  methods: {
    //新增
    addAdmins() {
      this.loading = true
      if (!this.checkForm()) {
        this.$message.error('请完善表单相关信息！');
      } else {
        this.form.status=0
        this.form.imgUrl = this.imageUrl
        axios.post("/register/lawyer", this.form).then(res => {
          if (res.data.code === 1) {
            this.$message.success("注册成功！请等待管理员审核完毕");
            this.addAdmin = false
            this.$router.push('/')
          } else {
            alert("您输入的用户名已存在！");
          }
        }).finally(() => {
          this.getAll()
          this.form.imgUrl = ""
        });
      }
    }, checkForm() {
      // 1.校验必填项
      let validForm = false;
      this.$refs.form.validate(valid => {
        validForm = valid
      })
      return validForm;
    },
    //图片回显
    handleAvatarSuccess(res) {
      this.imageUrl = "http://localhost:8082/image/" + res.data
      console.log(this.imageUrl)
      sessionStorage.setItem("imgUrl", this.imageUrl)
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    }
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
</script>

<style scoped>
.box {
  width: 600px;
  height: 500px;
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


.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 10px;
  color: #8c939d;
  width: 60px;
  height: 50px;
  line-height: 50px;
  text-align: center;
}

.avatar {
  width: 60px;
  height: 60px;
  display: block;
}
</style>