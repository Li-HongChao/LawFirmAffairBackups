<template>
  <div>
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

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="alertAdmins( scope.row)">同意申请</el-button>


          <el-button style="margin-left: 15px;" size="mini" type="danger"
                     @click="handleDelete(scope.$index, scope.row)">拒绝通过
          </el-button>
        </template>
      </el-table-column>

    </el-table>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "LawyerDecide",
  data() {
    return {
      tableData: [{
        name: '',
        phone: '',
        area: '',
        feeRate: '',
        rating: ''
      }
      ],
    }
  },
  methods: {
    //获取所有
    getAll() {
      axios.get("/select/lawyerDecideLawyer").then(res => {
        this.tableData = res.data.data
        console.log(res.data.data)
      })
    },
    //删除
    handleDelete(index, row) {
      this.$confirm("此操作不可恢复，是否继续", "警告", {
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
    //通过审核
    alertAdmins(row) {
      row.status=1
      axios.put("/update/lawyer",row ).then(res => {
        if (res.data.code == 1) {
          this.$message.success("已经通过！");
        } else {
          alert("出现错误" + res.data.msg);
        }
      }).finally(()=>{
        this.getAll()
      })
    }
  }
  ,
  created() {
    this.getAll()
  }
}
</script>

<style scoped>

div {
  height: 100%;
  width: 100%;
  overflow-y: scroll;
}

::-webkit-scrollbar {
  display: none;
}
</style>