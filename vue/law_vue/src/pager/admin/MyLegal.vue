<template>
  <div class="box" style="position:relative;">

    <el-button type="primary" icon="el-icon-edit" round
               @click="addLegalBefore"
               style="position:fixed;
               z-index: 1;
               bottom: 8%;
               right: 8%;">新增
    </el-button>


    <el-dialog title="新增新闻" :visible.sync="addLegal">

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

            <el-form-item>

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

      </el-form>

      <div slot="footer" class="dialog-footer">

        <el-button @click="addLegal=false">取消</el-button>

        <el-button type="primary" @click="addLegals()">确定</el-button>

      </div>

    </el-dialog>


    <el-dialog title="修改新闻信息" :visible.sync="alterRole">

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

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="alterRole = false">取 消</el-button>
        <el-button type="primary" @click="alertAdmins">确 定</el-button>
      </div>
    </el-dialog>

    <el-table :data="tableData" style="width: 100% ; padding: 10px;">

      <el-table-column
          label="序号"
          type="index"
          width="50"
          align="center">
      </el-table-column>

      <el-table-column label="标题" style="width: 100%" align="center" show-overflow-tooltip="show-overflow-tooltip">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.title }}</span>
        </template>
      </el-table-column>

      <el-table-column label="作者" align="center" show-overflow-tooltip="show-overflow-tooltip">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.author }}</span>
        </template>
      </el-table-column>

      <el-table-column label="正文" align="center" show-overflow-tooltip="show-overflow-tooltip">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.content }}</span>
        </template>
      </el-table-column>

      <el-table-column label="发布时间" align="center" show-overflow-tooltip="show-overflow-tooltip">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.time }}</span>
        </template>
      </el-table-column>

      <el-table-column label="图片链接" align="center" show-overflow-tooltip="show-overflow-tooltip"  width="100px">

        <template slot-scope="scope">
          <el-popover
              placement="right"
              title=""
              trigger="hover">
            <el-image style="border-radius: 10px;max-height: 500px;max-width: 500px" :src="scope.row.imageUrl" m/>
            <el-image slot="reference" :src="scope.row.imageUrl"
                      alt="${request.contextPath}/static/images/class1.png"
                      style="border-radius: 10px; max-height: 100px;max-width: 100px"/>
          </el-popover>
        </template>
      </el-table-column>

      <el-table-column label="操作" style="width: 100%" align="center">
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
  name: "MyLawyer",
  data() {
    return {
      tableData: [{
        author: '',
        time: "",
        title: '',
        content: '',
        imageUrl: ''
      }],
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
          trigger: 'blur'}]
      },
      alterRole: false,
      addLegal: false,
      imageUrl: '',
    }
  },
  methods: {
    //修改
    handleEdit(index, row) {
      this.alterRole = true
      axios.get("/legal/select?id=" + row.id).then(((res) => {
        if (res.data.code === 1) {
          //修改第一个调用的先是查询
          this.form.imageUrl = row.imgUrl
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
        this.form.imageUrl = this.imageUrl
        axios.put("/legal", this.form).then(res => {
          if (res.data.code == 1) {
            this.$message.success("修改成功！");
          } else {
            alert("出现错误" + res.data.msg);
          }
        }).finally(() => {
          this.getAll()
          this.alterRole = false
        });
      }
    },
    //删除
    handleDelete(index, row) {
      this.$confirm("此操作删除不可恢复，是否继续", "警告", {
        type: 'info'
      }).then(() => {
        console.log("已经执行")
        axios.delete(`/legal?id=` + row.id).then(((res) => {
          if (res.data.code == 1) {
            this.$message.success("删除成功！");
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
      axios.get("/legal/selectALL").then(res => {
        this.tableData = res.data.data
        console.log(res.data.data)
      })
    },
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
      this.addLegal = true
    },

    //新增
    addLegals() {
      if (!this.checkForm()) {
        this.$message.error('请完善表单相关信息！');
      } else {
        this.form.imageUrl = this.imageUrl
        axios.post("/legal", this.form).then(res => {
          if (res.data.code === 1) {
            this.$message.success("注册成功！");
            this.addLegal = false
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

.box {
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