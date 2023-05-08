<template>
  <el-card style="position:absolute;height: 80%;width: 80%;left: 50%; top:50%; transform:translate(-50%,-50%);">
    <el-table
        :data="orderList"
        style="width: 100%">
      <el-table-column
          label="咨询人"
          width="180">
        <template slot-scope="scope">
          咨询人： <el-tag size="medium">{{ scope.row.userName }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="right">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="toMsg(scope.row)">开始咨询</el-button>
          <el-button size="mini" type="danger" @click="deleteMsg(scope.row)">拒绝预约</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>

</template>

<script>
import axios from "axios";
import router from "@/router";

export default {
  name: "LawyerBeforeQuestions",
  data() {
    return {
      orderList: []
    }
  },
  methods: {
    getAll() {
      //查看是否有为整理的订单
      axios.get("/order?username=" + JSON.parse(sessionStorage.getItem('userInfo'))).then((res) => {
        this.orderList = res.data.data
        console.log("订单" + this.orderList)
      })
    },
    toMsg(data){
      data.status=0
      axios.put("/order",data).then((res) => {
        this.orderList = res.data.data
        console.log("订单" + this.orderList)
      })
      router.push("/lawyerQuestions/"+data.userName)
    },
    deleteMsg(data){
      data.status=0
      axios.put("/order",data).then((res) => {
        this.orderList = res.data.data
        console.log("订单" + this.orderList)
      })
      this.$message("已取消预约！")
      this.getAll()
    }
  },
  created() {
    this.getAll()
  }
}
</script>

<style scoped>

</style>