<template>
  <div class="box">
    <div class="header" style="position: relative">
       <ul>
         <li style="height: 65px; line-height: 65px;padding-left: 20px">
           <img style="width: 35px;height: 35px;margin-right: 20px" src="@/assets/logo.png" alt="">
         </li>
         <li class="username" style="font-weight: 555">{{name}}</li>
         <li class="pagename" style="position:absolute; line-height: 65px; right: 20px; font-weight: 600">后台管理页面</li>
       </ul>
    </div>
    <div style="height: 100%;width: 100%;">
      <div class="left" style="height: 100%;">
        <ul>
          <li>
            <div class="leftitem" v-bind:style="{background:activeColor1}" @click="admin">
              管理员账户管理
            </div>
          </li>
          <li>
            <div class="leftitem" v-bind:style="{background:activeColor2}" @click="lawyer">
              律师账户管理
            </div>
          </li>
          <li>
            <div class="leftitem" v-bind:style="{background:activeColor3}" @click="user">
              用户账户管理
            </div>
          </li>

          <li>
            <div class="leftitem" v-bind:style="{background:activeColor4}" @click="legal">
              资讯管理
            </div>
          </li>

          <li>
            <div class="leftitem" v-bind:style="{background:activeColor5}" @click="problem">
              回复管理
            </div>
          </li>

          <li>
            <div class="leftitem" @click="open">
              退出登录
            </div>
          </li>
        </ul>
      </div>

      <div class="main">
        <router-view></router-view>
      </div>
    </div>

  </div>
</template>

<script>
export default {
  data() {
    return {
      name: JSON.parse(sessionStorage.getItem('userInfo')),
      activeColor1: '',
      activeColor2: '',
      activeColor3: '',
      activeColor4: '',
      activeColor5: '',
    }
  },
  methods: {
    legal() {
      this.$router.push("/legals")
    },
    admin() {
      this.$router.push("/admins")
    },
    lawyer() {
      this.$router.push("/lawyers")
    },
    user() {
      this.$router.push("/users")
    },
    problem(){
      this.$router.push("/problems")
    },
    statu() {
      this.activeColor1 = '#e5e5e5'
      this.activeColor2 = '#e5e5e5'
      this.activeColor3 = '#e5e5e5'
      this.activeColor4 = '#e5e5e5'
      this.activeColor5 = '#e5e5e5'
    },
    open() {
      this.$confirm('即将退出后台管理页面, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$router.push("/")
        this.statu()
        this.activeColor1 = '#fff'
        this.$message.info("退出登录");
      })
    }
  },
  created() {
    this.$router.push("/admins")
    this.$message.success("欢迎," + JSON.parse(sessionStorage.getItem('userInfo')))
    console.log()
  },
  watch: {
    $route: {
      handler(val) {
        console.log(val.name)
        switch (val.name) {
          case 'admins':
            this.statu()
            this.activeColor1 = '#fff'
            break
          case 'lawyers':
            this.statu()
            this.activeColor2 = '#fff'
            break
          case 'users':
            this.statu()
            this.activeColor3 = '#fff'
            break
          case 'legals':
            this.statu()
            this.activeColor4 = '#fff'
                break
          case 'problems':
            this.statu()
            this.activeColor5 = '#fff'
        }
      },
      // 深度观察监听
      deep: true
    }
  }
}
</script>
<style scoped>

.box {
  height: 100%;
  width: 100%;
  background: white;
}

.header {
  position: relative;
  background: #e5e5e5;
  height: 65px;
  box-shadow: 0 1px 10px rgba(0, 0, 0, 0.5)
}

.header li{
  display: inline-block;
}

.left {
  background: #e5e5e5;
  width: 18%;
  height: 100%;
  padding-top: 20px;
  float: left;
}

.main {
  float: left;
  width: 82%;
  height: 100%;
}

.leftitem {
  padding-left: 15%;
  height: 70px;
  line-height: 70px;
  font-weight: 700;
  color: #8b8b8b;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.leftitem:hover {
  background: #fff;
  color: #000000;
  overflow: hidden;
  transform: perspective(1px) scale(1.1, 1.1);
  transition: all 0.3s;
  cursor: pointer;
}

::-webkit-scrollbar {
  display: none;
}





</style>

