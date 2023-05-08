<template>
  <div style="  background: #f5f5f5;">

    <!--详情展示框-->
    <el-dialog
        :visible.sync="dialogVisible"
        width="50%"
        :before-close="handleClose">
      <el-descriptions title="律师信息" :column="2">
        <el-descriptions-item label="律师名">{{lawyer.name}}</el-descriptions-item>
        <el-descriptions-item label="电话">{{lawyer.phone}}</el-descriptions-item>
        <el-descriptions-item label="擅长领域">{{lawyer.area}}</el-descriptions-item>
        <el-descriptions-item label="所属事务所">{{lawyer.lawFirm}}</el-descriptions-item>
        <el-descriptions-item label="咨询费/小时">{{lawyer.feeRate}}</el-descriptions-item>
        <el-descriptions-item label="星级">{{lawyer.rating}}</el-descriptions-item>
        <el-descriptions-item label="工作时间">{{lawyer.working}}</el-descriptions-item>
      </el-descriptions>
      <div style="margin-top: 20%">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="order(lawyer)">预 约</el-button>
      </div>
    </el-dialog>


    <div class="banner" style="padding: 40px">
      <el-carousel :interval="4000" type="card" height="330px">
        <el-carousel-item v-for="item in imgList" :key="item.url">
          <el-image :fit="fit" style=" width: 100% ; height: 100%;" :src="item.imgSrc"
                    @click="imageRouter"></el-image>
        </el-carousel-item>
      </el-carousel>
    </div>

    <h1 style="margin: 0 auto; text-align: center; padding-bottom: 40px">精英团队</h1>

    <!--分类-->
    <ul class="type">
      <li>刑事辩护</li>
      <li>财产分割</li>
      <li>婚姻家庭</li>
      <li>民事纠纷</li>
      <li>产权问题</li>
    </ul>
    <hr>

    <!--  正文  -->
    <ul class="clearfix" style=" text-align: center;width: 100%; height: 100%; padding:50px 5%; position: relative">
      <!--排行榜-->
      <li style="position: sticky; top: 80px; float: left; width: 38%; display: inline-block; vertical-align: top;">
        <el-card>
          <el-table
              :body-style="{ padding: '0px' }"
              :data="lawyerList"
          >
            <el-table-column
                label="排名"
                align="center"
                type="index"
                width="50">
            </el-table-column>
            <el-table-column
                align="center"
            >
              <template scope="scope">
                <div @click="getRanking(scope.row)">
                  <ul>
                    <li style="display: inline-block;margin-right: 10%; padding: 11px 0;">{{ scope.row.name }}</li>
                    <li style="display: inline-block;margin-right: 10%;">{{ "擅长领域：" + scope.row.area }}</li>
                    <li style="display: inline-block">{{ "业内水平：" + scope.row.rating + "星" }}</li>
                  </ul>
                </div>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </li>

      <!--信息表-->
      <li style="float: right; display: inline-block;width: 60%; height: 100%"
          v-for="item in lawyerListAll"
          :key="item.id">
        <el-card class="clearfix" style="height: 300px; width: 100%; margin-bottom: 20px;"
                 :body-style="{ padding: '0px' }"
        >
            <el-image :fit="fit" :src="item.imgUrl"
                      @click="getRanking(item)"
                      style="width: 30%; height:300px;margin: 0; float: right;"
            ></el-image>
          <div style="float: left; height: 300px; text-align: left; padding-left:20px ; padding-top: 30px; font-size: 15px;font-weight: bold;line-height: 35px">
            姓名： {{item.name}}
            <br>
            电话： {{item.phone}}
            <br>
            擅长领域： {{item.area}}
            <br>
            所属事务所： {{item.lawFirm}}
            <br>
           咨询费用/小时： {{item.feeRate}}
            <br>
            工作时间： {{item.working}}
            <br>
           星级： {{"⭐".repeat(item.rating)}}
          </div>
        </el-card>
      </li>
    </ul>

  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "UserLawyers",
  data() {
    return {
      imgList: [
        {
          imgSrc: "https://images.pexels.com/photos/46148/aircraft-jet-landing-cloud-46148.jpeg?auto=compress&cs=tinysrgb&w=600",
          url: "#"
        },
        {
          imgSrc: "https://images.pexels.com/photos/4451501/pexels-photo-4451501.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
          url: "#"
        },
        {
          imgSrc: "https://images.pexels.com/photos/46148/aircraft-jet-landing-cloud-46148.jpeg?auto=compress&cs=tinysrgb&w=600",
          url: "#"
        },
        {
          imgSrc: "https://images.pexels.com/photos/4451501/pexels-photo-4451501.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
          url: "#"
        }
      ],
      lawyerList: [
        {
          area:'',
          feeRate:'',
          id:'',
          imgUrl:'',
          lawFirm:'',
          name:'',
          phone:"",
          rating:'',
          working:''
        }
      ],
      lawyer:{
        area: '',
        feeRate: '',
        id: '',
        imgUrl: '',
        lawFirm: '',
        name: '',
        phone: "",
        rating: '',
        working: ''
      },
      orders:{
        userName:"",
        lawyerName:'',
        status:""
      },
      lawyerListAll:[],
      dialogVisible: false,
      fit: "cover"
    }
  },
  methods: {
    getRanking(row) {
      this.lawyer=row
      this.dialogVisible= true
    },
    order(item){
      this.orders.userName=JSON.parse(sessionStorage.getItem('userInfo'))
      this.orders.lawyerName=item.name
      this.orders.status=1
      console.log(this.orders)
      axios.post("/order",this.orders).then(() => {
        this.$message.success("预约成功，请在“我的咨询”中等待律师上线")
        this.dialogVisible=false
      })
    },
    getAll() {
      axios.get("/select/lawyerPut").then((res) => {
        console.log(res.data.data)
        this.lawyerList = res.data.data
      })
      axios.get("/select/lawyerAll").then((res) => {
        console.log(res.data.data)
        this.lawyerListAll = res.data.data
      })
    },
  },
  created() {
    this.getAll()
  }
}
</script>

<style scoped>
.type li {
  display: inline-block;
  margin: 0 2%;
}

.type li:hover {
  transform: perspective(0px) scale(1.2, 1.2);
  transition: all 0.2s;
  cursor: pointer;
}

.type {
  margin: 0 auto;
  text-align: center;
}

.el-table .cell {
  padding: 0 !important;
  height: 60px;
}



</style>