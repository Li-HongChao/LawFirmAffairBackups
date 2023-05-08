<template>
  <div style="  background: #f5f5f5;">

    <!--轮播图-->
    <div class="banner" style="padding: 40px">
      <el-carousel :interval="4000" type="card" height="330px">
        <el-carousel-item v-for="item in imgList" :key="item.url">
          <el-image :fit="fit" style=" width: 100% ; height: 100%;" :src="item.imgSrc"
                    @click="imageRouter">
          </el-image>
        </el-carousel-item>
      </el-carousel>
    </div>

    <!--推荐律师列表-->
    <div style="margin: 50px;">
      <h2>推荐律师</h2>
      <span style="font-weight: 300;">精英律师，素质过硬！</span>
    </div>
    <div class="legalMsg_list flexStyle">
      <div class="table">
        <el-card class="contentStyle"
                 shadow="hover" :body-style="{ padding: '0px' }"
                 style="width: 23.5%; margin:0.3%; height: 30%; display: inline-block; position: center"
                 v-for="item in lawyerList" :key="item.url">
          <el-image :fit="fit" :src="item.imgUrl"
                    style="width: 100%;height: 300px;display: block; margin: 0"
                    @click="toLawyer"
          ></el-image>

          <div style="padding: 10px">
            <div class="lawyer_name">
              {{ item.name }}
            </div>
            <div class="lawyer_area">
              擅长领域：{{ item.area }}
            </div>
            <div class="lawyer_phone">
              {{ item.phone }}
            </div>
            <div class="lawyer_rating">
              星级：{{ "⭐".repeat(item.rating) }}
            </div>
          </div>
        </el-card>
      </div>
    </div>

    <!--法律新闻列表-->
    <div style="margin: 50px;">
      <h2>法律新闻</h2>
      <span style="font-weight: 300;">法律热点，不容错过！</span>
    </div>
    <div class="legalMsg_list">
      <div class="table">
        <el-card shadow="hover" :body-style="{ padding: '0px' }"
                 style="width: 32.5%; margin:0.3%; height: 30%; display: inline-block; position: center"
                 v-for="item in newList" :key="item.title">
          <el-image :fit="fit" :src="item.imageUrl"
                    style="width: 100%;height: 200px;display: block; margin: 0"
                    @click="toNews"
          ></el-image>
          <div class="legalMsg_title showOverTooltip">
            {{ item.title }}
          </div>
          <div class="legalMsg_author showOverTooltip">
            作者： {{ item.author }}
          </div>
          <div class="legalMsg_content showOverTooltip">
            {{ item.content }}
          </div>

        </el-card>
      </div>
    </div>


    <div class="table">
      <!--排名列表-->
      <ul>
        <!--律师排名-->
        <li style="display: inline-block; width: 46%; margin:2%; height: 30%;">
          <div style="margin: 50px;">
            <h2>律师排名</h2>
            <span style="font-weight: 300;">大神云集，包您满意</span>
          </div>
          <div class="legalMsg_list">
            <div class="table">
              <el-card shadow="always" :body-style="{ padding: '0px' }">
                <el-table

                    :data="lawyerList"
                    style="width: 100%">
                  <el-table-column
                      label="排名"
                      align="center"
                      type="index"
                      width="50">
                  </el-table-column>
                  <el-table-column
                      prop="name"
                      align="center"
                      label="姓名"
                      show-overflow-tooltip="show-overflow-tooltip"
                      width="100%">
                    <template scope="scope">
                      <div @click="getLawyerTable(scope.row)">{{ scope.row.name }}</div>
                    </template>
                  </el-table-column>
                  <el-table-column
                      align="center"
                      show-overflow-tooltip="show-overflow-tooltip"
                      prop="rating"
                      label="律师星级">
                  </el-table-column>
                  <el-table-column
                      align="center"
                      show-overflow-tooltip="show-overflow-tooltip"
                      prop="area"
                      label="擅长领域">
                  </el-table-column>
                </el-table>


              </el-card>
            </div>
          </div>
        </li>
        <!--新闻列表-->
        <li style="display: inline-block; vertical-align: top; width: 46%; margin:2%; height: 30%;">
          <div style="margin: 50px;">
            <h2>新闻列表</h2>
            <span style="font-weight: 300;">简短信息，洞彻世界</span>
          </div>
          <div class="legalMsg_list">
            <div class="table">
              <el-card shadow="always" :body-style="{ padding: '0px' }">
                <el-table
                    :data="newList"
                    style="width: 100%">
                  <el-table-column
                      label=""
                      align="center"
                      type="index"
                      width="50">
                  </el-table-column>
                  <el-table-column
                      prop="title"
                      label="事件"
                      show-overflow-tooltip="show-overflow-tooltip"
                      width="100%">
                    <template scope="scope">
                      <div @click="getNewsTable(scope.row)">{{ scope.row.title }}</div>
                    </template>
                  </el-table-column>
                  <el-table-column
                      show-overflow-tooltip="show-overflow-tooltip"
                      prop="content"
                      label="详情">
                  </el-table-column>
                </el-table>

              </el-card>
            </div>
          </div>
        </li>
      </ul>
    </div>

  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "UserIndex",
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
      newList: [],
      lawyerList: [],
      fit: 'cover'
    }
  },
  methods: {
    getAll(){
      axios.get("/select/lawyerPut").then((res)=>{
        console.log(res.data.data)
        this.lawyerList=res.data.data
      })
      axios.get("/legal/selectPut").then((res)=>{
        console.log(res.data.data)
        this.newList=res.data.data
      })
    },
    getLawyerTable(){
      this.$router.push("/lawyerTeams")
    },
    getNewsTable(){
      this.$router.push("/news")
    },
    imageRouter() {
      this.$router.push("/news")
    },
    toLawyer(){
      this.$router.push("/lawyerTeams")
    },
    toNews(){
      this.$router.push("/news")
    }

  },
  created() {
    this.getAll()
  }
}
</script>

<style scoped>
.showOverTooltip {
  overflow: hidden;
  text-overflow:ellipsis;
  white-space: nowrap;
}

.legalMsg_list {
  width: 85%;
  margin: 0 auto;
}

.table {
  margin: 0 auto;
}

.legalMsg_title {
  font-weight: 550;
  font-size: 20px;
  padding: 10px;
}

.legalMsg_content {
  padding: 10px;
  Letter-spacing: 1px;
  line-height: 140%;
}

.legalMsg_author {
  font-weight: 300;
  font-size: 15px;
  padding-left: 10px;
}

.lawyer_name {
  font-weight: 550;
  font-size: 20px;
}

.lawyer_area {
  font-size: 13px;
  padding: 10px 0;
}

.lawyer_phone {
  padding: 10px 0;
  font-size: 18px;
  color: black;
}

.lawyer_rating {
  padding: 10px 0;
  font-size: 16px;
}

.flexStyle {
  justify-content: space-between;
  /* 设置超出滚动 */
  overflow-x: auto;
}

.contentStyle {
  display: inline-block;
  margin-right: 5px;
  /* 超出滚动的关键，没有它元素会自动缩小，不会滚动 */
  flex-shrink: 0;
}

</style>