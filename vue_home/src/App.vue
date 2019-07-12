<template>
  <div id="app">
    <router-view v-if="isRouterAlive"/>
  </div>
</template>

<script>
export default {
  name: 'App',
  provide () { // 注册一个方法
    return {
      reload: this.reload
    }
  },
  data () {
    return {
      isRouterAlive: true
    }
  },
  methods: {
    reload () {
      this.isRouterAlive = false
      this.$nextTick(function () {
        this.isRouterAlive = true
        console.log('reload')
      })
    }
  }
}
</script>

<style lang="scss">
  * {
    padding: 0;
    margin: 0;
  }
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    color: #2c3e50;
    position: fixed;
    height: 100%;
    width: 100%;
  }
  .loginTxt {
    .el-form-item__content {
      margin: 0 !important;
    }
  }
  .updatePwd {
    .el-dialog {
      width: 35%;
    }
  }
  .operateBtn {
    background: #409EFF;
    color: #fff;
  }
</style>
