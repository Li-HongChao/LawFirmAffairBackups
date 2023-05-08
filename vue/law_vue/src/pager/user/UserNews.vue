<template>
  <div style="  background: #f5f5f5;">

    <!--详情展示框-->
    <el-dialog
        :visible.sync="dialogVisible"
        width="50%"
        :before-close="handleClose">
      <div>
        <h2 style="text-align: center">{{ news.title }}</h2>
        <p style="margin-top: 20px;">&emsp;&emsp;{{ "\t\t"+news.content }}</p>
      </div>
    </el-dialog>

    <div class="banner" style="padding: 40px">
      <el-carousel :interval="4000" type="card" height="330px">
        <el-carousel-item v-for="item in imgList" :key="item.url">
          <el-image :fit="fit" style=" width: 100% ; height: 100%;" :src="item.imgSrc"></el-image>
        </el-carousel-item>
      </el-carousel>
    </div>

    <h1 style="margin: 0 auto; text-align: center; padding-bottom: 40px">法律热点</h1>

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
              :data="newsList"
          >
            <el-table-column
                label="最新热文"
                align="center"
                type="index"
                width="80">
            </el-table-column>
            <el-table-column
                align="center"
            >
              <template scope="scope">
                <div @click="getRanking(scope.row)">
                  <ul>
                    <li style="display: inline-block;
                    margin-right: 10%; padding: 11px 0;
                    width: 100%;  overflow: hidden;
                    text-overflow:ellipsis;
                    white-space: nowrap;">{{ scope.row.title }}
                    </li>
                  </ul>
                </div>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </li>

      <!--信息表-->
      <li style="float: right; display: inline-block;width: 60%; height: 100%"
          v-for="item in newsListAll"
          :key="item.id">
        <el-card class="clearfix" style="height: 100%; width: 100%; margin-bottom: 20px;"
                 :body-style="{ padding: '0px' }"
        >
          <div style="text-align: left">
            <el-image :fit="fit" :src="item.imageUrl"
                      style="width: 100%;height: 200px;display: block; margin: 0"
                      @click="view(item)"
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
          </div>
        </el-card>
      </li>
    </ul>

  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "UserNews",
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
      newsList: [],
      newsListAll:[],
      news: {},
      dialogVisible: false,
      fit: "cover"
    }
  },
  methods: {
    getRanking(row) {
      this.news=row
      this.dialogVisible=true
    },
    view(item){
      this.news=item
      this.dialogVisible=true
    },
    getAll() {
      axios.get("/legal/selectPut").then((res) => {
        console.log(res.data.data)
        this.newsList = res.data.data
      })
      axios.get("/legal/selectALL").then((res) => {
        console.log(res.data.data)
        this.newsListAll = res.data.data
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


</style>