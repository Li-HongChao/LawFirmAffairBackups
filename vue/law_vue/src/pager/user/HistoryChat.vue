<template>
  <div class="box">

    <el-table
        :data="chat"
        :default-sort="{prop: 'date', order: 'descending'}">
      <el-table-column>
        <template slot-scope="scope">
          <el-tag size="medium">发送人：</el-tag>
          {{ scope.row.fromName }}
        </template>
      </el-table-column>

      <el-table-column>
        <template slot-scope="scope">
          <el-tag type="success">接收人：</el-tag>
          {{ scope.row.toName }}
        </template>
      </el-table-column>

      <el-table-column label="消息">
        <template slot-scope="scope">
          <el-image :src="scope.row.msg" v-if="scope.row.role>1" :preview-src-list="imgList"></el-image>
          <div v-else> {{ scope.row.msg }}</div>
        </template>
      </el-table-column>

      <el-table-column label="时间" sortable prop="date">
        <template slot-scope="scope">
          {{ scope.row.time }}
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "HistoryChat",
  data() {
    return {
      userName: JSON.parse(sessionStorage.getItem('userInfo')),
      toNAme: this.$route.params.toName,
      chat: [],
      imgList: []
    }
  },
  methods: {
    getAll() {
      if (this.toNAme != 1) {
        axios.get("/chat/user?username=" + this.userName + "&lawyerName=" + this.toNAme).then((res) => {
          this.chat = res.data.data
          this.setImageList()
        })
      } else if (this.toNAme == 1) {
        axios.get("/chat?username=" + this.userName).then((res) => {
          this.chat = res.data.data
          this.setImageList()
          console.log("选择了全部：" + res.data.data)
        })
      }

    },
    setImageList(){
      this.chat.forEach(e=>{
        if (e.role>1){
          this.imgList.push(e.msg)
        }
        console.log(e)
      })
    }
  },
  created() {
    this.getAll()
  }
}
</script>

<style scoped>

.box{
  height: 100%;
  background: #ffffff;
  padding-top: 35px;
}
</style>