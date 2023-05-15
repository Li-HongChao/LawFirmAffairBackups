<template>
  <div class="box" style="position:relative;">
    <el-button type="primary" icon="el-icon-edit" round
               @click="addAdmin = true"
               style="position:fixed;
               z-index: 1;
               bottom: 8%;
               right: 8%;">新增
    </el-button>
    <el-dialog title="新增管理员" :visible.sync="addAdmin" width="50%">
      <el-form :model="form" :rules="rules" ref="form">
        <el-form-item label="管理员姓名" :label-width="formLabelWidth" prop="username">
          <el-input v-model="form.username" autocomplete="off" placeholder="请输入管理员姓名"></el-input>
        </el-form-item>
        <el-form-item label="密码" :label-width="formLabelWidth" prop="password">
          <el-input v-model="form.password" autocomplete="off" placeholder="请输入密码"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addAdmin = false">取 消</el-button>
        <el-button type="primary" @click="addAdmins">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="修改管理员信息" :visible.sync="alterAdmin" width="50%">
      <el-form :model="form" :rules="rules" ref="form">
        <el-form-item label="管理员姓名" :label-width="formLabelWidth" prop="username">
          <el-input v-model="form.username" autocomplete="off" placeholder="请输入管理员姓名"></el-input>
        </el-form-item>
        <el-form-item label="密码" :label-width="formLabelWidth" prop="password">
          <el-input v-model="form.password" autocomplete="off" placeholder="请输入密码"></el-input>
        </el-form-item>
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
          align="center">
      </el-table-column>

      <el-table-column label="姓名" style="width: 100%" align="center">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.username }}</span>
        </template>
      </el-table-column>

      <el-table-column label="密码" width="120%" align="center">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.password }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" style="width: 100%" align="center">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>


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
  name: "MyAdmins",
  data() {
    return {
      tableData: [{
        username: '',
        password: ''
      }
      ],
      username: '',
      alterAdmin: false,
      addAdmin: false,
      form: {
        id: '',
        username: '',
        password: '',
      },
      formLabelWidth: '120px',
      rules: {//校验规则
        phone: [{pattern: /^1[3456789]\d{9}$/, required: true, message: '请检查格式', trigger: 'blur'}],
        username: [{ required: true, message: '请填写姓名', trigger: 'blur'}],
        area: [{required: true, message: '擅长领域为必填项', trigger: 'blur'}],
        password: [{min: 6, max: 12, required: true, message: '密码为必填项,最短长度为6，最长为12奥~', trigger: 'blur'}],
        rating: [{pattern: /^[0-9]$/, required: true, message: '必填项，最大为9', trigger: 'blur'}],
        feeRate: [{pattern: /^\d+\.?\d{0,2}$/, required: true, message: '收费标准为必填项', trigger: 'blur'}]
      },
    }
  },
  methods: {
    //修改
    handleEdit(index, row) {
      this.alterAdmin = true
      axios.get("/select/byId/admin?id=" + row.id).then(((res) => {
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
      }  else {
        axios.put("/update/admin", this.form).then(res => {
          if (res.data.code === 1) {
            this.$message.success("修改成功！");
          } else {
            alert("出现错误" + res.data.msg);
          }
        }).finally(() => {
          this.alterAdmin = false
          this.getAll()
        });
      }
    },
    //删除
    handleDelete(index, row) {
      this.$confirm("此操作删除不可恢复，是否继续", "警告", {
        type: 'info'
      }).then(() => {
        axios.delete(`/delete/admin?id=` + row.id).then(((res) => {
          console.log(res.data.code)
        })).finally(() => {
          this.getAll()
        })
      })
    },
    //获取所有
    getAll() {
      axios.get("/select/adminAll").then(res => {
        this.tableData = res.data.data
        console.log(res.data.data)
      })
    },
    //新增
    addAdmins() {
      if (!this.checkForm()) {
        this.$message.error('请完善表单相关信息！');
      } else {
        this.loading = true
        axios.post("/register/admin", this.form).then(res => {
          if (res.data.code === 1) {
            this.$message.success("注册成功！");
            this.addAdmin = false
          } else {
            alert("您输入的用户名已存在！");
          }
        }).finally(() => {
          this.getAll()
          this.form = {
            id: '',
            username: '',
            password: '',
          }
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

.box {
  height: 100%;
  width: 100%;
  overflow-y: scroll;
}

::-webkit-scrollbar {
  display: none;
}
</style>