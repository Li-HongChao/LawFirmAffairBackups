<template>
  <div class="box" style="position:relative;">
    <el-button type="primary" icon="el-icon-edit" round
               @click="addAdminBefore"
               style="position:fixed;
               z-index: 1;
               bottom: 8%;
               right: 8%;">新增
    </el-button>
    <el-dialog title="新增问题" :visible.sync="addAdmin" width="50%">
      <el-form ref="form" :model="form">
        <el-form-item label="问题" :label-width="formLabelWidth">
          <el-input v-model="form.question" autocomplete="off" placeholder="请输入问题"></el-input>
        </el-form-item>
        <el-form-item label="回复" :label-width="formLabelWidth">
          <el-input v-model="form.answer" autocomplete="off" placeholder="请输入对应回复"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addAdmin = false">取 消</el-button>
        <el-button type="primary" @click="addAdmins">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="修改问题及回答" :visible.sync="alterAdmin" width="50%">
      <el-form ref="form" :model="form">
        <el-form-item label="问题" :label-width="formLabelWidth">
          <el-input v-model="form.question" autocomplete="off" placeholder="请输入问题"></el-input>
        </el-form-item>
        <el-form-item label="回复" :label-width="formLabelWidth">
          <el-input v-model="form.answer" autocomplete="off" placeholder="请输入对应回复"></el-input>
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

      <el-table-column label="问题" style="width: 100%" align="center" show-overflow-tooltip="show-overflow-tooltip">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.question }}</span>
        </template>
      </el-table-column>

      <el-table-column label="回复" style="width: 100%" align="center" show-overflow-tooltip="show-overflow-tooltip">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.answer }}</span>
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
  name: "MyProblem",
  data() {
    return {
      tableData: [],
      alterAdmin: false,
      addAdmin: false,
      form: {
        question: "",
        answer: ""
      },
      formLabelWidth: '120px',
    }
  },
  methods: {
    //修改
    handleEdit(index, row) {
      this.alterAdmin = true
      axios.get("/problem/getById?id=" + row.id).then(((res) => {
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
        axios.put("/problem", this.form).then(res => {
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

    },
    //删除
    handleDelete(index, row) {
      this.$confirm("此操作删除不可恢复，是否继续", "警告", {
        type: 'info'
      }).then(() => {
        axios.delete(`/problem?id=` + row.id).then(((res) => {
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
      axios.get("/problem/getAll").then(res => {
        this.tableData = res.data.data
        console.log(res.data.data)
      })
    },
    //新增
    addAdminBefore() {
      this.form = {
        id: '',
        name: '',
        password: '',
        phone: '',
        email: ''
      }
      this.addAdmin = true
    },
    addAdmins() {
      if (!this.checkForm()) {
        this.$message.error('请完善表单相关信息！');
      } else {
        this.loading = true
        axios.post("/problem", this.form).then(res => {
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

.box {
  height: 100%;
  width: 100%;
  overflow-y: scroll;
}

::-webkit-scrollbar {
  display: none;
}
</style>