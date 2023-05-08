<template>
  <div style="position:relative;">
    <el-button type="primary" icon="el-icon-edit" round
               @click="addAdminBefore"
               style="position:fixed;
               z-index: 1;
               bottom: 8%;
               right: 8%;">新增
    </el-button>
    <el-dialog title="新增用户" :visible.sync="addAdmin" width="50%">
      <el-form ref="form" :model="form" :rules="rules">
        <el-form-item label="姓名" :label-width="formLabelWidth" prop="name">
          <el-input v-model="form.name" autocomplete="off" placeholder="请输入管姓名"></el-input>
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
        <el-button @click="addAdmin = false">取 消</el-button>
        <el-button type="primary" @click="addAdmins">确 定</el-button>
      </div>
    </el-dialog>

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

    <el-table :data="tableData" style="width: 100% ; ">

      <el-table-column
          label="序号"
          type="index"
          width="50"
          align="center">
      </el-table-column>

      <el-table-column label="姓名" style="width: 100%" align="center">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.name }}</span>
        </template>
      </el-table-column>

      <el-table-column label="电话号码" style="width: 100%" align="center">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.phone }}</span>
        </template>
      </el-table-column>

      <el-table-column label="邮箱" style="width: 100%" align="center">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.email }}</span>
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
  name: "MyUser",
  data() {
    return {
      tableData: [{
        name: '',
        password: '',
        phone:'',
        email:''
      }
      ],
      username: '',
      alterAdmin: false,
      addAdmin: false,
      form: {
        id:'',
        name: '',
        password: '',
        phone:'',
        email:''
      },
      formLabelWidth: '120px',
      rules: {//校验规则
        phone: [{pattern:/^1[3456789]\d{9}$/ ,required: true, message: '请检查格式', trigger: 'blur'}],
        name: [{max:8,required: true, message: '需要汉字哦~', trigger: 'blur'}],
        area: [{required: true, message: '擅长领域为必填项', trigger: 'blur'}],
        password: [{min:6,max:12,required: true, message: '密码为必填项,最短长度为6，最长为12奥~', trigger: 'blur'}],
        rating: [{ max:1,pattern:/^[0-9]*$/,required: true, message: '必填项，最大为9', trigger: 'blur'}],
        feeRate: [{pattern:/^\d+\.?\d{0,2}$/ ,required: true, message: '收费标准为必填项', trigger: 'blur'}],
        email: [{pattern:/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)$/ ,required: true, message: '请检查邮箱格式', trigger: 'blur'}]
      },
    }
  },
  methods: {
    //修改
    handleEdit(index, row) {
      this.alterAdmin=true
      axios.get("/select/byId/user?id=" + row.id).then(((res) => {
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
          this.alterAdmin=false
        });
      }

    },
    //删除
    handleDelete(index, row) {
      this.$confirm("此操作删除不可恢复，是否继续", "警告", {
        type: 'info'
      }).then(() => {
        axios.delete(`/delete/user?id=` + row.id).then(((res) => {
          if (res.data.code==1){
            this.$message.success("修改成功！");
          }else {
            alert("出现错误" + res.data.msg);
          }
        })).finally(() => {
          this.getAll()
        })
      })
    },
    //获取所有
    getAll() {
      axios.get("/select/userAll").then(res => {
        this.tableData = res.data.data
        console.log(res.data.data)
      })
    },
    //新增
    addAdminBefore(){
      this.form = {
        id:'',
        name: '',
        password: '',
        phone:'',
        email:''
      }
      this.addAdmin=true
    },
    addAdmins() {
      if (!this.checkForm()) {
        this.$message.error('请完善表单相关信息！');
      }  else {
        this.loading = true
        axios.post("/register/user", this.form).then(res => {
          if (res.data.code === 1) {
            this.$message.success("注册成功！");
            this.addAdmin = false
          } else {
            alert("您输入的用户名已存在！");
          }
        }).finally(() => {
          this.getAll()
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
  overflow-y: scroll;
}

::-webkit-scrollbar {
  display: none;
}
</style>