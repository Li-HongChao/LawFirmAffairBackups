<template>
  <div>

    <el-dialog title="修改律师信息" :visible.sync="alterAdmin">

      <el-form ref="form" :model="form" :rules="rules" label-position="right"
               label-width="100px">

        <el-row>

          <el-col :span="8">

            <el-form-item label="律师姓名" prop="name">

              <el-input v-model="form.name"/>

            </el-form-item>

          </el-col>

          <el-col :span="8">

            <el-form-item label="律师级别" prop="rating">

              <el-input v-model="form.rating"/>

            </el-form-item>

          </el-col>

          <el-col :span="8">

            <el-form-item label="律师电话" prop="phone">

              <el-input v-model="form.phone"/>

            </el-form-item>

          </el-col>

          <el-col :span="8">

            <el-form-item label="擅长领域" prop="area">

              <el-input v-model="form.area"/>

            </el-form-item>

          </el-col>

          <el-col :span="8">

            <el-form-item label="账户密码" prop="password">

              <el-input v-model="form.password"/>

            </el-form-item>

          </el-col>

          <el-col :span="8">

            <el-form-item label="收费标准" prop="feeRate">

              <el-input v-model="form.feeRate"/>

            </el-form-item>

          </el-col>

          <el-col :span="8">

            <el-form-item label="所在事务所">

              <el-input v-model="form.lawFirm"/>

            </el-form-item>

          </el-col>

          <el-col :span="12">

            <el-form-item label="工作时间">

              <el-input v-model="form.working"/>

            </el-form-item>

          </el-col>

        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="alterAdmin = false">取 消</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
      </div>
    </el-dialog>

    <div class="information">
      <el-card style="margin: 5% 5% 0 5%">
        <el-descriptions title="我的信息" :column="2" class="margin-top">
          <template slot="extra">
            <el-button type="primary" size="small" @click="handleEdit(lawyer.name)">修改</el-button>
          </template>
          <el-descriptions-item label="律师名">{{ lawyer.name }}</el-descriptions-item>
          <el-descriptions-item label="电话">{{ lawyer.phone }}</el-descriptions-item>
          <el-descriptions-item label="擅长领域">{{ lawyer.area }}</el-descriptions-item>
          <el-descriptions-item label="所属事务所">{{ lawyer.lawFirm }}</el-descriptions-item>
          <el-descriptions-item label="咨询费/小时">{{ lawyer.feeRate }}</el-descriptions-item>
          <el-descriptions-item label="星级">{{ lawyer.rating }}</el-descriptions-item>
          <el-descriptions-item label="工作时间">{{ lawyer.working }}</el-descriptions-item>
          <el-descriptions-item label="我的排名">
            <el-tag size="small">{{ lawyerList.map((item) => item.name).indexOf(lawyer.name) + 1 }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="我的预约" v-if="orderList.length!==0">
            <el-tag size="warning">{{ orderList.length }}</el-tag>
          </el-descriptions-item>
        </el-descriptions>
      </el-card>
    </div>

    <div>
      <el-dialog :visible.sync="dialogVisible" width="50%" :before-close="handleClose">
        <div>
          <h2 style="text-align: center">{{ news.title }}</h2>
          <p style="margin-top: 20px;">&emsp;&emsp;{{ "\t\t" + news.content }}</p>
        </div>
      </el-dialog>
    </div>

    <h2 style="text-align: center; margin-top: 40px;  background:#f5f5f5;">法律要闻</h2>

    <div class="msg" style=" background:#f5f5f5;">
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
        <li style="float: right; display: inline-block;width: 60%; height: 100%;"
            v-for="item in newsList"
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

  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "LawyerIndex",
  data() {
    return {
      username: JSON.parse(sessionStorage.getItem('userInfo')),
      id: JSON.parse(sessionStorage.getItem('userId')),
      lawyer: {
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
      rules: {//校验规则
        phone: [{pattern: /^1[3456789]\d{9}$/, required: true, message: '请检查格式', trigger: 'blur'}],
        name: [{max: 8, required: true, message: '需要汉字哦~', trigger: 'blur'}],
        area: [{required: true, message: '擅长领域为必填项', trigger: 'blur'}],
        password: [{min: 6, max: 12, required: true, message: '密码为必填项,最短长度为6，最长为12奥~', trigger: 'blur'}],
        rating: [{pattern: /^[0-9]$/, required: true, message: '必填项，最大为9', trigger: 'blur'}],
        feeRate: [{pattern: /^\d+\.?\d{0,2}$/, required: true, message: '收费标准为必填项', trigger: 'blur'}]
      },
      form: {
        name: '',
        phone: '',
        area: '',
        password: '',
        lawFirm: '',
        feeRate: '',
        working: '',
        rating: ''
      },
      lawyerList: [],
      news: {},
      newsList: [],
      orderList: [],
      fit: 'cover',
      dialogVisible: false,
      alterAdmin: false
    }
  },
  methods: {
    getRanking(row) {
      this.news = row
      this.dialogVisible = true
    },
    view(item) {
      this.news = item
      this.dialogVisible = true
    },
    //修改
    handleEdit(name) {
      this.alterAdmin = true
      axios.get("/select/lawyer?name=" + name).then(((res) => {
        if (res.data.code === 1) {
          //修改第一个调用的先是查询
          this.form = res.data.data
        } else {
          this.$message.error(res.data.msg)
        }
      }))
    },
    submit() {
      if (!this.checkForm()) {
        this.$message.error('请完善表单相关信息！');
      } else {
        axios.put("/update/lawyer", this.form).then(res => {
          if (res.data.code == 1) {
            this.$message.success("修改成功！");
          } else {
            alert("出现错误" + res.data.msg);
          }
        }).finally(() => {
          this.alterAdmin = false
        });
      }
    },
    getAll() {
      axios.get("/select/lawyer?name=" + this.username).then((res) => {
        console.log(res.data.data)
        console.log(this.id)
        this.lawyer = res.data.data
      })
      axios.get("/legal/selectPut").then((res) => {
        console.log(res.data.data)
        this.newsList = res.data.data
      })

      axios.get("/select/lawyerPut").then((res) => {
        console.log(res.data.data)
        this.lawyerList = res.data.data
      })
      //查看是否有为整理的订单
      axios.get("/order?username=" + this.username).then((res) => {
        this.orderList = res.data.data
        if (this.orderList.length !== 0) {
          this.$message.success("您有咨询未处理，请前往“我的查询”处理")
        }
      })
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
.type li {
  display: inline-block;
  margin: 0 2%;
}

.type li:hover {
  transform: perspective(0px) scale(1.2, 1.2);
  transition: all 0.2s;
  cursor: pointer;
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