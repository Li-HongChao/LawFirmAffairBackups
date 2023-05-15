<template>
  <div style="position:relative;">


    <el-card style="margin: 7% auto;width: 70%; height: 60%; padding: 5%" shadow="always">

      <el-form ref="form" :model="form" :rules="rules" label-position="right"
               label-width="100px">

        <el-row>

          <el-col :span="12">

            <el-form-item label="新闻标题" prop="title">

              <el-input v-model="form.title"/>

            </el-form-item>

          </el-col>

          <el-col :span="12">

            <el-form-item label="新闻作者" prop="author">

              <el-input v-model="form.author"/>

            </el-form-item>

          </el-col>

          <el-col :span="12">

            <el-form-item label="图片">

              <el-upload
                  class="avatar-uploader"
                  action="http://localhost:8082/legal/upload"
                  :show-file-list="false"
                  :on-success="handleAvatarSuccess"
                  :before-upload="beforeAvatarUpload">
                <img v-if="imageUrl!=''" :src="imageUrl" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"
                   style="font-size: 20px;border: #a1a1a1 solid 1px;border-radius: 10px">上传图片</i>
              </el-upload>

            </el-form-item>

          </el-col>

          <el-col :span="24">

            <el-form-item label="新闻内容" prop="content">

              <el-input v-model="form.content" type="textarea"/>

            </el-form-item>

          </el-col>


        </el-row>

        <el-button type="primary" @click="addLegals()"
                   style="float: right"
        >确定
        </el-button>

      </el-form>

    </el-card>

  </div>
</template>
<script>
import axios from "axios";

export default {
  name: "lawyerLegals",
  data() {
    return {
      form: {
        author: '',
        time: "",
        title: '',
        content: '',
        imageUrl: '',
      },
      rules: {//校验规则
        author: [{max: 8, min: 1, required: true, message: '长度最大为8，最小为1', trigger: 'blur'}],
        title: [{required: true, message: '请完整填写！', trigger: 'blur'}],
        content: [{required: true, message: '请完整填写！', trigger: 'blur'}],
        imageUrl: [{required: true, message: '请完整填写！', trigger: 'blur'}],
        url: [{
          pattern: /^(?:http(s)?:\/\/)?[\w.-]+(?:\.[\w-]+)+[\w\-_~:/?#[\]@!&',;=.]+$/,
          required: true,
          message: '收费标准为必填项',
          trigger: 'blur'
        }]
      },
      imageUrl: '',
    }
  },
  methods: {
    addLegalBefore() {
      this.form = {
        name: '',
        phone: '',
        area: '',
        password: '',
        lawFirm: '',
        feeRate: '',
        working: '',
        rating: ''
      }
    },
    //新增
    addLegals() {
      if (!this.checkForm()) {
        this.$message.error('请完善表单相关信息！');
      } else {
        this.form.imageUrl = this.imageUrl
        axios.post("/legal", this.form).then(res => {
          if (res.data.code === 1) {
            this.$message.success("新增成功！");
            this.addLegal = false
            this.$router.push("/lawyerIndex")
          } else {
            alert("您输入的用户名已存在！");
          }
        }).finally(() => {
          this.getAll()
          this.addLegal = false
        });
      }
    },
    checkForm() {
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
    },
  },
  created() {
    this.getAll()
  }

}
</script>

<style scoped>
body {
  width: 100%;
}

div {
  height: 100%;
  width: 100%;
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
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
}

.avatar {
  width: 120px;
  height: 120px;
  display: block;
}

</style>