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

            <el-form-item label="图片" prop="imageUrl">

              <el-input v-model="form.imageUrl"/>

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
        imageUrl: ''
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
        axios.post("/legal", this.form).then(res => {
          if (res.data.code === 1) {
            this.$message.success("新增成功！继续添加，或者去首页查看？");
            this.addLegalBefore()
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
    }
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


</style>