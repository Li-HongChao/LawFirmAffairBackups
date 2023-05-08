<template>
  <div style="width: 100%;height: 100%;background:white;">
    <el-tabs v-model="activeName" @tab-click="handleClick" style="padding: 20px;">
      <el-tab-pane label="律师管理" name="content">
        <router-view></router-view>
      </el-tab-pane>
      <el-tab-pane label="律师排名" name="sort">
        <router-view></router-view>
      </el-tab-pane>
      <el-tab-pane label="申请审核" name="decide">
        <router-view></router-view>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>

export default {
  data() {
    return {
      activeName: 'content'
    };
  },
  methods: {
    handleClick(tab) {
      switch (tab.name){
        case "content":
          this.$router.replace("/content")
          break
        case "decide":
          this.$router.replace("/decide")
          break
        case "sort":
          this.$router.replace("/sort")
          break
      }
    }
  },
  created() {
    this.$router.push("/content")
  }
};
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