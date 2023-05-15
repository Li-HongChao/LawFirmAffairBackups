<template>
  <div>
    <el-card style="position:absolute;height: 80%;width: 80%;left: 50%; top:50%; transform:translate(-50%,-50%);">
      <el-table
          :data="orderList"
          style="width: 100%">

        <!--        1-->
        <el-table-column
            label="咨询人"
            width="180">
          <template slot-scope="scope">
            咨询律师：
            <el-tag size="medium">{{ scope.row.lawyerName }}</el-tag>
          </template>
        </el-table-column>

        <!--        2-->
        <el-table-column label="操作" align="right">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" @click="toMsg(scope.row)">开始咨询</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-button type="text" style="float: right;padding: 10px" @click="historys">聊天记录</el-button>
    </el-card>

    <el-dialog :visible.sync="dialogVisible" width="70%">
      <el-button type="warning" @click="downloadFile">下载到本地</el-button>

      <div style="margin: 50px 0">
        <el-radio-group v-model="select">
          <el-radio :label="1" style="margin: 20px;">全部</el-radio>
          <i v-for="item in nameList" :key="item.toName">
            <el-radio style="margin: 20px;" :label="item.toName" v-if="item.toName!==userName">{{item.toName}}</el-radio>
          </i>
        </el-radio-group>
      </div>

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
            {{ scope.row.msg }}
          </template>
        </el-table-column>

        <el-table-column label="时间" sortable prop="date">
          <template slot-scope="scope">
            {{ scope.row.time }}
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
import router from "@/router";
import {saveAs} from 'file-saver';

export default {
  name: "LawyerBeforeQuestions",
  data() {
    return {
      userName:JSON.parse(sessionStorage.getItem('userInfo')),
      orderList: [],
      chat: [],
      nameList:[],
      dialogVisible: false,
      select:1
    }
  },
  methods: {
    getAll() {
      //查看是否有未咨询的订单
      axios.get("/order/user?username=" + this.userName).then((res) => {
        this.orderList = res.data.data
        console.log("订单" + this.orderList)
      })

      axios.get("/chat?username=" + this.userName).then((res) => {
        this.chat = res.data.data
        console.log("聊天记录" + this.chat)
        const map = new Map()
        this.nameList=this.chat.filter((item) => !map.has(item.toName) && map.set(item.toName, 1))
      })
    },
    toMsg(data) {
      router.push("/questions/" + data.lawyerName)
    },
    historys() {
      this.dialogVisible = true
    },
    downloadFile() {
      if (this.select!==1){
        axios.get("/chat/user?username="+this.userName+"&lawyerName="+this.select).then((res)=>{
          this.chat = res.data.data
          this.save()
        })
      }else if (this.select===1){
        axios.get("/chat?username=" + this.userName).then((res) => {
          this.chat = res.data.data
          console.log("选择了全部："+res.data.data)
          this.save()
        })
      }
    },
    save(){
      console.log(this.chat)
      let data = ""
      for (const chatKey in this.chat) {
        data = data + `\n${this.chat[chatKey].time}\t| 发送人：${this.chat[chatKey].fromName}\t| 接收人：${this.chat[chatKey].toName}\t| 发送消息：”${this.chat[chatKey].msg}“\n`
      }
      let content = new Blob([data], {type: 'text/plain;charset=utf-8'});
      let name = new Date().getTime()
      saveAs(content, `${name}.txt`);
    }


  },
  created() {
    this.getAll()
  }
}
</script>

<style scoped>

</style>