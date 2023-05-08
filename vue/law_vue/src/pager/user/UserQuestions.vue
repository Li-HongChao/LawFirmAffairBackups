<template>
  <div class="container">

    <el-dialog
        title="咨询付费"
        :visible.sync="dialogVisible"
        :before-close="handleClose">
      <img style="height: 100%;width: 100%; display: block " src="@/assets/pay.png">
      <div>
        <span>您本次的消费为：{{ money }} （￥）请扫码付款</span>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button type="primary" @click="dialogVisible = false" align="center">确 定</el-button>
  </span>
    </el-dialog>

    <div class="content" id="content">
      <div class="item item-center" v-if="!lawyerStatus"><span>请等待律师上线，您现在可以根据提示和智能客服沟通</span>
      </div>

      <div v-for="item in panel" :key="item.msg.key">

        <div class="item item-center"><span>{{ item.time }}</span></div>

        <div class="item item-left" v-if="item.role===0">
          <div class="avatar">
            <div class="el-icon-user-solid" style="font-size: 45px"/>
          </div>
          <div class="bubble bubble-left">{{ item.msg }}</div>
        </div>

        <div class="item item-right" v-if="item.role===1">
          <div class="bubble bubble-right">{{ item.msg }}</div>
          <div class="avatar">
            <div class="el-icon-user-solid" style="font-size: 45px"/>
          </div>
        </div>

      </div>

    </div>
    <div class="input-area">
      <textarea v-model="msg"></textarea>
      <div class="button-area">
        <div
            style="width: 100%; height: 80px; margin: 0 15px 5px 0;overflow-x: scroll; -webkit-overflow-scrolling:touch;display: -webkit-box;white-space: nowrap;">
          <div v-for="item in problem" :key=item.id>
            <div class="question" @click="questions(item.id)"> {{ item.question }}</div>
          </div>
        </div>
        <el-button type="danger" style="text-align: center;padding: 6px;" @click="close">结束咨询</el-button>
        <el-button type="primary" icon="el-icon-picture-outline" style="text-align: center;padding: 6px;"></el-button>
        <el-button type="success" icon="el-icon-check" style="text-align: center;padding: 6px;"
                   @click="send"></el-button>
        <input type="file" name="filename" id="open" style="display:none"/>
      </div>
    </div>
  </div>

</template>

<script>
import axios from "axios";

let socket
let key = 0
export default {
  name: "UserQuestions",
  data() {
    return {
      username: JSON.parse(sessionStorage.getItem("userInfo")),
      id: JSON.parse(sessionStorage.getItem("userId")),
      nowTime: this.getNowDate(),
      //消息面板
      panel: [],
      msg: '',
      //律师是否上线
      lawyerStatus: false,
      //聊天计时
      timeStart: 0,
      timeEnd: 0,

      //在线状态
      online: false,

      //智能回复备选
      problem: [],
      //收费弹窗
      dialogVisible: false,
      //付款二维码src
      pay: "@/assets/pay.png",
      //获取律师资费使用对象
      lawyer: {},
      money: 0,
      //图片地址
      path: document.getElementById('open').files[0].path
    }
  },
  created() {
    this.init()
    console.log(this.nowTime)
  },
  methods: {
    init() {
      //在对方没有上线之前可以使用智能客服
      axios.get("/problem/getAll").then(res => {
        this.problem = res.data.data
        console.log(res.data.data)
      })

      axios.get("/select/lawyer?name=" + this.$route.params.toName).then((res) => {
        console.log(res.data.data)
        console.log(this.id)
        this.lawyer = res.data.data
      })

      //建立连接
      let url = "ws://localhost:8082/websocket/" + this.username
      //如果已经存在，就关闭
      if (socket != null) {
        socket.close();
        socket = null;
      }
      //新建对象
      socket = new WebSocket(url);

      //打开事件
      socket.onopen = () => {
        this.online = true
        console.log("连接已打开")
      };

      //收到消息事件
      socket.onmessage = (message) => {
        if (!this.lawyerStatus) {
          this.$message.success("律师已上线，一寸光阴一寸金！")
          this.lawyerStatus = true
          this.timeStart = Date.now()
        }

        if (message.data == "*") {
          this.$message("对方不在线哦~")
        } else {
          console.log("收到信息" + message.data)
          let msgs = message.data.split(`$`);
          let msg = {}
          msg.key = key++
          msg.msg = msgs[2]
          msg.time = this.getNowDate()
          msg.role = 0
          this.panel.push(msg)
          this.$nextTick(() => {
            let msg = document.getElementById('content') // 获取对象
            msg.scrollTop = msg.scrollHeight // 滚动高度
          })
        }
      }
      //关闭事件
      socket.onclose = () => {
        this.online = false
        this.timeEnd = Date.now()
        let start = this.timeStart
        let end = this.timeEnd
        if (start != 0) {
          console.log(end - start)
          let minutes = ((end - start) % (1000 * 60 * 60) / (1000 * 60)).toFixed(2);

          this.dialogVisible = true
          this.$message.success("本次交谈时间为：" + minutes + "分钟")
          this.money = (this.lawyer.feeRate / 60 * minutes).toFixed(2)

        } else {
          this.$message.info("交谈尚未开始")
        }


      }
      //错误事件
      socket.onerror = () => {
        this.$message.error("连接错误！")
      }
    },
    send() {
      if (this.online) {
        //发送消息以$作为分隔符
        socket.send(`${JSON.parse(sessionStorage.getItem("userInfo"))}$${this.$route.params.toName}$${this.msg}$${1}$${new Date}`)

        let msg = {}
        msg.key = key++
        msg.msg = this.msg
        msg.time = this.getNowDate()
        msg.role = 1
        this.panel.push(msg)

        this.$nextTick(() => {
          let msg = document.getElementById('content') // 获取对象
          msg.scrollTop = msg.scrollHeight // 滚动高度
        })
      } else {
        this.$message.info("咨询已结束或未开始，您可以尝试和客服沟通！")
      }


      //清空面板
      this.msg = ""
      console.log(this.panel)
    },
    questions(id) {
      axios.get("/problem/getById?id=" + id).then(((res) => {
        let msg1 = {}
        msg1.key = key++
        msg1.msg = res.data.data.question
        msg1.time = this.getNowDate() + "(客服)"
        msg1.role = 1
        this.panel.push(msg1)

        let msg2 = {}
        msg2.key = key++
        msg2.msg = res.data.data.answer
        msg2.time = this.getNowDate() + "(客服)"
        msg2.role = 0
        this.panel.push(msg2)
        console.log(this.panel)


        this.$nextTick(() => {
          let msg = document.getElementById('content') // 获取对象
          msg.scrollTop = msg.scrollHeight // 滚动高度
        })
      }))
    },
    close() {
      if (socket != null) {
        socket.close()
      } else {
        this.$message.info("已经结束！")
      }

    }
    ,
    getNowDate() {
      var myDate = new Date;
      var year = myDate.getFullYear(); //获取当前年
      var mon = myDate.getMonth() + 1; //获取当前月
      var date = myDate.getDate(); //获取当前日
      var hours = myDate.getHours(); //获取当前小时
      var minutes = myDate.getMinutes(); //获取当前分钟
      var seconds = myDate.getSeconds(); //获取当前秒
      var now = year + "-" + mon + "-" + date + " " + hours + ":" + minutes + ":" + seconds;
      return now;
    }
  }
}

</script>

<style scoped>
* {
  padding: 0;
  margin: 0;
}

body {
  height: 100vh;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.container {
  height: 100%;
  width: 100%;
  border-radius: 4px;
  border: 0.5px solid #e0e0e0;
  background-color: #f5f5f5;
  display: flex;
  flex-flow: column;
  overflow: hidden;
}

.content {
  width: calc(100% - 40px);
  padding: 20px;
  overflow-y: scroll;
  flex: 1;
}

.content:hover::-webkit-scrollbar-thumb {
  background: rgba(0, 0, 0, 0.1);
}

.bubble {
  max-width: 400px;
  padding: 10px;
  border-radius: 5px;
  position: relative;
  color: #000;
  word-wrap: break-word;
  word-break: normal;
}

.item-left .bubble {
  margin-left: 15px;
  background-color: #fff;
}

.item-left .bubble:before {
  content: "";
  position: absolute;
  width: 0;
  height: 0;
  border-left: 10px solid transparent;
  border-top: 10px solid transparent;
  border-right: 10px solid #fff;
  border-bottom: 10px solid transparent;
  left: -20px;
}

.item-right .bubble {
  margin-right: 15px;
  background-color: #9eea6a;
}

.item-right .bubble:before {
  content: "";
  position: absolute;
  width: 0;
  height: 0;
  border-left: 10px solid #9eea6a;
  border-top: 10px solid transparent;
  border-right: 10px solid transparent;
  border-bottom: 10px solid transparent;
  right: -20px;
}

.item {
  margin-top: 15px;
  display: flex;
  width: 100%;
}

.item.item-right {
  justify-content: flex-end;
}

.item.item-center {
  justify-content: center;
}

.item.item-center span {
  font-size: 12px;
  padding: 2px 4px;
  color: #fff;
  background-color: #dadada;
  border-radius: 3px;
  -moz-user-select: none; /*火狐*/
  -webkit-user-select: none; /*webkit浏览器*/
  -ms-user-select: none; /*IE10*/
  -khtml-user-select: none; /*早期浏览器*/
  user-select: none;
}

.avatar img {
  width: 42px;
  height: 42px;
  border-radius: 50%;
}

.input-area {
  border-top: 0.5px solid #e0e0e0;
  height: 150px;
  display: flex;
  flex-flow: column;
  background-color: #fff;

}

textarea {
  font-family: "PingFang SC";
  font-size: 15px;
  padding: 20px;
  font-weight: 500;
  flex: 1;
  border: none;
  cursor: pointer;
  overflow-y: auto;
  overflow-x: hidden;
  outline: none;
  resize: none;
}

.button-area {
  display: flex;
  height: 40px;
  margin-right: 10px;
  line-height: 40px;
  padding: 5px;
  justify-content: flex-end;
}

.button-area button {
  width: 80px;
  border: none;
  outline: none;
  border-radius: 4px;
  float: right;
  cursor: pointer;
}

/* 设置滚动条的样式 */

::-webkit-scrollbar {
  display: none;
}

.question {
  display: inline-block;
  border-radius: 9px;
  margin: 0 5px;
  text-align: center;
  color: #656565;
  padding-left: 6px;
  height: 50px;
  font-size: 15px;
}

.question:hover {
  cursor: default;
}


</style>
