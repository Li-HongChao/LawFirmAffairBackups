<template>
  <div style="z-index: 66">
    <el-button type="primary" icon="el-icon-edit" round
               @click="addAdminBefore"
               style="position:fixed;
               z-index: 666;
               bottom: 8%;
               right: 8%;">新增
    </el-button>


    <el-dialog title="新增律师" :visible.sync="addAdmin">

      <el-form ref="form" :model="form" :rules="rules" label-position="right"
               label-width="100px">

        <el-form-item label=""  prop="imageUrl">
          <el-upload
              class="avatar-uploader"
              action="http://localhost:8082/register/upload"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
            <img v-if="imageUrl!=''" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon">上传图片</i>
          </el-upload>
        </el-form-item>

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

          <el-col :span="8">

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

      <div slot="footer" class="dialog-footer">

        <el-button @click="addAdmin=false">取消</el-button>

        <el-button type="primary" @click="addAdmins()">确定</el-button>

      </div>

    </el-dialog>


    <el-dialog title="修改律师信息" :visible.sync="alterAdmin">

      <el-form ref="form" :model="form" :rules="rules" label-position="right"
               label-width="100px">

        <el-form-item label=""  prop="imageUrl">
          <el-upload
              class="avatar-uploader"
              action="http://localhost:8082/register/upload"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
            <img v-if="imageUrl!=''" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon">上传图片</i>
          </el-upload>
        </el-form-item>

        <el-row>

          <el-col :span="8">

            <el-form-item label="律师姓名" prop="name">

              <el-input v-model="form.name"/>

            </el-form-item>

          </el-col>

          <el-col :span="8">

            <el-form-item label="律师级别" prop="rating">

              <el-input v-model="form.rating"/>

            </el-form-item>

          </el-col>

          <el-col :span="8">

            <el-form-item label="律师电话" prop="phone">

              <el-input v-model="form.phone"/>

            </el-form-item>

          </el-col>

          <el-col :span="8">

            <el-form-item label="擅长领域" prop="area">

              <el-input v-model="form.area"/>

            </el-form-item>

          </el-col>

          <el-col :span="8">

            <el-form-item label="账户密码" prop="password">

              <el-input v-model="form.password"/>

            </el-form-item>

          </el-col>

          <el-col :span="8">

            <el-form-item label="收费标准" prop="feeRate">

              <el-input v-model="form.feeRate"/>

            </el-form-item>

          </el-col>

          <el-col :span="8">

            <el-form-item label="所在事务所">

              <el-input v-model="form.lawFirm"/>

            </el-form-item>

          </el-col>

          <el-col :span="12">

            <el-form-item label="工作时间">

              <el-input v-model="form.working"/>

            </el-form-item>

          </el-col>

        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="alterAdmin = false">取 消</el-button>
        <el-button type="primary" @click="alertAdmins">确 定</el-button>
      </div>
    </el-dialog>

    <el-table :data="tableData" style="width: 100% ;">

      <el-table-column
          label="序号"
          type="index"
          width="50"
          align="center"
      >
      </el-table-column>

      <el-table-column label="姓名" style="width: 100%" align="center">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.name }}</span>
        </template>
      </el-table-column>

      <el-table-column label="电话" align="center">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.phone }}</span>
        </template>
      </el-table-column>

      <el-table-column label="擅长领域" align="center">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.area }}</span>
        </template>
      </el-table-column>

      <el-table-column label="收费/小时" align="center">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.feeRate }}</span>
        </template>
      </el-table-column>

      <el-table-column label="星级" align="center">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.rating }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" style="width: 150%" align="center">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑/详情</el-button>


          <el-button style="margin-left: 15px;" size="mini" type="danger"
                     @click="handleDelete(scope.$index, scope.row)">删除
          </el-button>
        </template>
      </el-table-column>

    </el-table>
  </div>
</template>

<script>

import axios from "axios";

export default {
  name: "LawyerContent",
  data() {
    return {
      tableData: [{
        name: '',
        phone: '',
        area: '',
        feeRate: '',
        rating: '',
        imgUrl:""
      }
      ],
      imageUrl: '',
      name: '',
      alterAdmin: false,
      addAdmin: false,
      form: {
        name: '',
        phone: '',
        area: '',
        password: '',
        lawFirm: '',
        feeRate: '',
        working: '',
        imgUrl:"",
        rating: ''
      },
      formLabelWidth: '120px',
      rules: {//校验规则
        phone: [{pattern: /^1[3456789]\d{9}$/, required: true, message: '请检查格式', trigger: 'blur'}],
        name: [{max: 8, required: true, message: '需要汉字哦~', trigger: 'blur'}],
        area: [{required: true, message: '擅长领域为必填项', trigger: 'blur'}],
        password: [{min: 6, max: 12, required: true, message: '密码为必填项,最短长度为6，最长为12奥~', trigger: 'blur'}],
        rating: [{pattern: /^[0-9]$/, required: true, message: '必填项，最大为9', trigger: 'blur'}],
        feeRate: [{pattern: /^\d+\.?\d{0,2}$/, required: true, message: '收费标准为必填项', trigger: 'blur'}]
      },
    }
  },
  methods: {
    addAdminBefore() {
      this.form = {
        name: '',
        phone: '',
        area: '',
        password: '',
        lawFirm: '',
        feeRate: '',
        working: '',
        rating: '',
        imgUrl: ""
      }
      this.addAdmin = true
    },
    //新增
    addAdmins() {
      this.loading = true
      if (!this.checkForm()) {
        this.$message.error('请完善表单相关信息！');
      } else {
        this.form.imgUrl=this.imageUrl
        axios.post("/register/lawyer", this.form).then(res => {
          if (res.data.code === 1) {
            this.$message.success("注册成功！");
            this.addAdmin = false
          } else {
            alert("您输入的用户名已存在！");
          }
        }).finally(() => {
          this.getAll()
          this.form.imgUrl=""
        });
      }
    },
    //修改
    handleEdit(index, row) {
      this.alterAdmin = true
      axios.get("/select/byId/lawyer?id=" + row.id).then(((res) => {
        if (res.data.code === 1) {
          //修改第一个调用的先是查询
          this.imageUrl=row.imgUrl
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
        this.form.imgUrl=this.imageUrl
        axios.put("/update/lawyer", this.form).then(res => {
          if (res.data.code == 1) {
            this.$message.success("修改成功！");
          } else {
            alert("出现错误" + res.data.msg);
          }
        }).finally(() => {
          this.alterAdmin = false
          this.form.imgUrl=""
        });
      }
    },
    //删除
    handleDelete(index, row) {
      this.$confirm("此操作删除不可恢复，是否继续", "警告", {
        type: 'info'
      }).then(() => {
        axios.delete(`/delete/lawyer?id=` + row.id).then(((res) => {
          if (res.data.code == 1) {
            this.$message.success("修改成功！");
          } else {
            alert("出现错误" + res.data.msg);
          }
        })).finally(() => {
          this.getAll()
        })
      })
    },
    //获取所有
    getAll() {
      axios.get("/select/lawyerAll").then(res => {
        this.tableData = res.data.data
        console.log(res.data.data)
      })
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
      this.imageUrl="http://localhost:8082/image/"+res.data
      console.log(this.imageUrl)
      sessionStorage.setItem("imgUrl",this.imageUrl)
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
  }
  ,
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
  overflow-y: scroll;
}

::-webkit-scrollbar {
  display: none;
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